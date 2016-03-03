package com.thelionking.datafetch.test;

import java.util.ArrayList;
import java.util.List;

import com.thelionking.datafetch.dao.JokeDao;
import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.model.Source;

public class JokeDaoTest {
	public static void main(String[] args) {
		List<Joke> jokes = new ArrayList<Joke>();
		Joke joke = new Joke();
		joke.setAuthor("³Â½ðÅô");
		joke.setContent("chjp");
//		joke.setDate("1991-10-19 23:24");
		joke.setImgurl("www.baidu.com");
		joke.setReadingCount(177);
		joke.setSource(Source.BSBDJ);
//		joke.setType(1);
		
		Joke joke2 = new Joke();
		joke2.setAuthor("³Â½ðÅô");
		joke2.setContent("chjp");
//		joke2.setDate("1991-10-19 23:24");
		joke2.setImgurl("www.baidu.com");
		joke2.setReadingCount(177);
		joke2.setSource(Source.BSBDJ);
//		joke2.setType(1);
		
		jokes.add(joke);
		jokes.add(joke2);

		JokeDao jd = new JokeDao();
		jd.insert(jokes, JokeDao.JOKES_TEXT);
//		jd.deleteAll();
	}
}
