package com.thelionking.datafetch.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.thelionking.datafetch.dao.JokeDao;
import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.model.Source;
import com.thelionking.datafetch.model.fetcher.HTMLPageDownloader;
import com.thelionking.datafetch.util.CommonUtil;

public final class FetchTest {
	public static void main(String[] args) {
		int page = 0;
		for(int i=0;i <20; ++i) {
			fetch(page);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			page += 20;
		}
	}
	public static void fetch(int page) {
		List<Joke> jokes = new ArrayList<Joke>();
		String source = HTMLPageDownloader.download("http://www.neihanshequ.com/pic/new/?page=" + page, false, "utf-8");
		Pattern p = Pattern.compile("<div class=\"pin\">(\\n|.)*?</div>");
		Matcher m = p.matcher(source);
		
		while (m.find()) {
			Joke joke = new Joke();
			joke.setSource(Source.NHSQ);
			joke.setDate(1);
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
					String content = contentMatcher.group().trim().replaceAll("<a target=\"_blank\"(\\n|.)*?<p>", "");
					joke.setContent(content);
					CommonUtil.print("内容 : " + content);
				}

				joke.setReadingCount(200);
				
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
	}
	
}


