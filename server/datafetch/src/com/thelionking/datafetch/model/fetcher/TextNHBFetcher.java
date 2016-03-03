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

public final class TextNHBFetcher extends Fetcher{
	private int page;
	
	//此处有一点bug，page不能在这里初始化，因为此时propertiesDir还未被设置
	public TextNHBFetcher(String baseUrl) {
		super(baseUrl);
		
	}
	
	@Override
	public void fetch() {
		List<Joke> jokes = new ArrayList<Joke>();
		String source = HTMLPageDownloader.download(generateUrl(), false, "GB2312");
		Pattern p = Pattern
				.compile("<li\\s*class=\"piclist\\d*\">(\n|.)*?(?=</li>)</li>");
		Matcher m = p.matcher(source);
		while (m.find()) {
			Joke joke = new Joke();
			joke.setSource(Source.NHB);
			joke.setDate(this.generator.generateMillils());
			joke.setImgurl("NULL");
			if (!"".equals(m.group())) {
				Pattern authorPattern = Pattern
						.compile("<a href=\"/article/\\d+.html\">(\n|.)*?(?=</a>)");
				Matcher authorMatcher = authorPattern.matcher(m.group());
				while (authorMatcher.find()) {
					String author = authorMatcher.group()
							.replaceAll("<a href=\"/article/\\d+.html\">", "")
							.replaceAll("<b>", "").replaceAll("</b>", "");
					joke.setAuthor(author);
					CommonUtil.print("作者 : " + author);
				}
				Pattern contentPattern = Pattern
						.compile("(?<=<div class=\"f18 mb20\">)(\n|.)*?(?=</div>)");
				Matcher contentMatcher = contentPattern.matcher(m.group());
				while (contentMatcher.find()) {
					//过滤一下HTML的格式
					String content = doHtmlFilter(contentMatcher.group(), HTML_FILTERS);
					joke.setContent(content);
					CommonUtil.print("内容 : " + content);
				}

				Pattern readingCountPattern = Pattern
						.compile("(?<=<em>)\\s*\\d+\\s*(?=</em>)");
				Matcher readingCountMatcher = readingCountPattern.matcher(m
						.group());
				while (readingCountMatcher.find()) {
					int readingCount = Integer.parseInt(readingCountMatcher
							.group().trim());
					joke.setReadingCount(readingCount);
					CommonUtil.print("浏览次数 : " + readingCount);
				}
			}
			jokes.add(joke);
		}
		JokeDao jokeDao = new JokeDao();
		jokeDao.insert(jokes, JokeDao.JOKES_TEXT);
		++page;
	}

	@Override
	public void close() {
		Properties p = new Properties();
		p.setProperty("nhb_page", page + "");
		Constant.write(this.propertiesDir, p);
	}

	@Override
	protected String generateUrl() {
		return baseUrl + page + ".html";
	}

	@Override
	protected void rollback() {
		this.generator.rollback(0);
	}
	
	protected void setPropertiesDir(String propertiesDir){
		this.propertiesDir = propertiesDir;
		Properties p  = Constant.read(this.propertiesDir);
		page = Integer.parseInt(p.getProperty("nhb_page"));
	}
	
	
}
