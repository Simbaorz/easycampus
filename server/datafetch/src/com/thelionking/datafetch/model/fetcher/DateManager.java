package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.model.Joke;

public final class DateManager {

	private JokeDateGenerator textGenerator;
	private JokeDateGenerator pictureGenerator;
	private JokeDateGenerator vedioGenerator;
	
	private DateManager() {
		textGenerator = new JokeDateGenerator(Joke.TEXT, "text.properties");
		pictureGenerator = new JokeDateGenerator(Joke.PICTURE, "text.properties");
		vedioGenerator = new JokeDateGenerator(Joke.VEDIO, "text.properties");
	}
	
	public static DateManager getInstance() {
		return SingletonHelper.INSTANCE;
	}
	
	private static class SingletonHelper{
		static DateManager INSTANCE = new DateManager();
	}
	
	public JokeDateGenerator getGenerator(int id){
		if(id == Joke.TEXT){
			return textGenerator;
		}else if(id == Joke.PICTURE){
			return pictureGenerator;
		}else if(id == Joke.VEDIO){
			return vedioGenerator;
		}else{
			return null;
		}
	}
}
