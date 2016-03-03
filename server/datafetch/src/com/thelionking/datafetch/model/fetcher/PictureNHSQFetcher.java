package com.thelionking.datafetch.model.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thelionking.datafetch.dao.JokeDao;
import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.model.Source;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

public class PictureNHSQFetcher extends Fetcher{
	private int page;
	
	public PictureNHSQFetcher(String baseUrl) {
		super(baseUrl);
	}

	@Override
	public void fetch() {
		List<Joke> jokes = new ArrayList<Joke>();
		String source = HTMLPageDownloader.download(generateUrl(), false, "utf-8");
		Pattern p = Pattern.compile("<div class=\"pin\">(\\n|.)*?</div>");
		Matcher m = p.matcher(source);
		
		while (m.find()) {
			Joke joke = new Joke();
			joke.setSource(Source.NHSQ);
			joke.setDate(this.generator.generateMillils());
			if (!"".equals(m.group())) {
				Pattern authorPattern = Pattern.compile("<span rel=\"nofollow\" class=\"name\">(\\n|.)*?</span>");
				Matcher authorMatcher = authorPattern.matcher(m.group());
				while (authorMatcher.find()) {
					String author = authorMatcher.group().trim().replaceAll("<span rel=\"nofollow\" class=\"name\">", "")
							.replaceAll("</span>", "").replaceAll("<a(\n|.)*?>", "").replaceAll("</a>", "");
					joke.setAuthor(author);
					CommonUtil.print("作者 : " + author);
				}
				Pattern contentPattern = Pattern
						.compile("<a\\s*target=\"_blank\"(\\n|.)*?<p>(\\n|.)*?(?=</p>)");
				Matcher contentMatcher = contentPattern.matcher(m.group());
				while (contentMatcher.find()) {
					//过滤一下HTML的格式
					String content = contentMatcher.group().trim().replaceAll("<a\\s*target=\"_blank\"(\\n|.)*?<p>", "");
					joke.setContent(content);
					CommonUtil.print("内容 : " + content);
				}

				joke.setReadingCount(generateReadingCount(2000));
				
				Pattern imgurlPattern = Pattern
						.compile("<img class=\"lazy\" data-original=\"(\\n|.)*?(?=\")");
				Matcher imgurlMatcher = imgurlPattern.matcher(m.group());
				while (imgurlMatcher.find()) {
					//过滤一下HTML的格式
					String imgurl = imgurlMatcher.group().trim().replaceAll("<img class=\"lazy\" data-original=\"", "");
					joke.setImgurl(imgurl);
					CommonUtil.print("图像地址 : " + imgurl);
				}
			}
			jokes.add(joke);
		}
		
		JokeDao jokeDao = new JokeDao();
		jokeDao.insert(jokes, JokeDao.JOKES_PICTURE);
		page += 20;
	}

	@Override
	public void close() {
		Properties p = new Properties();
		p.setProperty("nsq_page", page + "");
		Constant.write(this.propertiesDir, p);
	}

	@Override
	protected String generateUrl() {
		return this.baseUrl + page;
	}

	@Override
	protected void rollback() {
		this.generator.rollback(0);
	}
	
	protected void setPropertiesDir(String propertiesDir) {
		this.propertiesDir = propertiesDir;
		Properties p = Constant.read(propertiesDir);
		this.page = Integer.parseInt(p.getProperty("nhsq_page"));
	}
}
