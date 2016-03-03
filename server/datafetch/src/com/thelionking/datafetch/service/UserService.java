package com.thelionking.datafetch.service;

import java.util.List;

import com.thelionking.datafetch.buffer.JokeBuffer;
import com.thelionking.datafetch.buffer.JokeBufferManager;
import com.thelionking.datafetch.buffer.JokeBufferManager.JokeData;
import com.thelionking.datafetch.dao.JokeDao;
import com.thelionking.datafetch.model.Joke;
import com.thoughtworks.xstream.XStream;

public class UserService {
	
	
	/**
	 * 将对象转换成xml输出
	 * @param o
	 * @return
	 */
	private String toXml(JokeData data) {
		XStream xStream = new XStream();
		xStream.aliasType("jokes", JokeData.class);
		xStream.addImplicitCollection(JokeData.class, "list");
		xStream.aliasType("joke", Joke.class);
		return xStream.toXML(data);
	}
	
	/**
	 * 将对象转换成xml输出
	 * @param o
	 * @return
	 */
	private String toXml(List<Joke> jokes) {
		ListBean<Joke> bean = new ListBean<Joke>(jokes);
		XStream xStream = new XStream();
		xStream.aliasType("jokes", ListBean.class);
		xStream.addImplicitCollection(JokeData.class, "list");
		xStream.aliasType("joke", Joke.class);
		return xStream.toXML(jokes);
	}
	
	public static class ListBean<T> {
		private List<T> list = null;
		public ListBean(List<T> list) {
			this.list = list;
		}
	}
	

	/**
	 * 
	 * @param start
	 * @param end
	 * @param type
	 * @param mode
	 * @return
	 */
	public List<Joke> getBuffer(long start, long end, int type, int mode) {
		List<Joke> jokes = null;
		JokeDao jokeDao = new JokeDao();
		if(mode == Joke.LASTEST_MODE) {
			if(type == Joke.TEXT) {
				String sql = "select * from jokes_text where date >= " + start + " and date < " + end;
				jokes = jokeDao.getBySQL(sql, null);
			}
			else if(type == Joke.PICTURE) {
				String sql = "select * from jokes_picture where date >= " + start + " and date < " + end;
				jokes = jokeDao.getBySQL(sql, null);
			}
			else if(type == Joke.VEDIO) {
				String sql = "select * from jokes_vedio where date >= " + start + " and date < " + end;
				jokes = jokeDao.getBySQL(sql, null);
			}
		}else if(mode == Joke.RECOMMEND_MODE){
			
		}else{
			
		}
		return jokes;
	}
	
	/**
	 * 对应从顶部刷新的逻辑
	 * 拿到从上次刷新时间点到目前时间点的数据
	 * 每天初始刷新点是0点
	 * @param date
	 * @param type
	 * @param mode
	 * @param index
	 * @return
	 */
	public String getJokesFromTop(long date, int type, int mode, int start) {
		JokeData data = null;
		if(mode == Joke.LASTEST_MODE) {
			if(type == Joke.TEXT){
				data = JokeBufferManager.getInstance().getTodayBuffer(Joke.TEXT, start);
			}else if(type == Joke.PICTURE) {
				data = JokeBufferManager.getInstance().getTodayBuffer(Joke.PICTURE, start);
			}else if(type == Joke.VEDIO) {
				data = JokeBufferManager.getInstance().getTodayBuffer(Joke.VEDIO, start);
			}else{
				//error
			}
		}else if(mode == Joke.RECOMMEND_MODE) {
			
		}
		
		
		return toXml(data);
	}
	
	/**
	 * 对应从底部刷新的逻辑
	 * @param date
	 * @param type
	 * @param mode
	 * @return
	 */
	public String getJokesFromBottom(long date, int type, int mode, int start) {
		JokeData data = null;
		
		if(mode == Joke.LASTEST_MODE) {
			if(type == Joke.TEXT){
				data = JokeBufferManager.getInstance().getYesterdayBuffer(Joke.TEXT, start);
			}else if(type == Joke.PICTURE) {
				data = JokeBufferManager.getInstance().getYesterdayBuffer(Joke.PICTURE, start);
			}else if(type == Joke.VEDIO) {
				data = JokeBufferManager.getInstance().getYesterdayBuffer(Joke.VEDIO, start);
			}else{
				//error
			}
		}else if(mode == Joke.RECOMMEND_MODE) {
			
		}
		
		
		return toXml(data);
	}

	public String getRemedy(int type, int remedy) {
		
		return toXml(JokeBufferManager.getInstance().getRemedy(type, remedy));
	}

	public String getJokes(int id, int type, int mode) {
		List<Joke> jokes = null;
		JokeDao jokeDao = new JokeDao();
		if(mode == Joke.LASTEST_MODE) {
			if(type == Joke.TEXT) {
				String sql = "select * from jokes_text where id >= " + id;
				jokes = jokeDao.getBySQL(sql, null);
			}
			else if(type == Joke.PICTURE) {
				String sql = "select * from jokes_picture where id >= " + id;
				jokes = jokeDao.getBySQL(sql, null);
			}
			else if(type == Joke.VEDIO) {
				String sql = "select * from jokes_vedio where id >= " + id;
				jokes = jokeDao.getBySQL(sql, null);
			}
		}else if(mode == Joke.RECOMMEND_MODE){
			
		}else{
			
		}
		return toXml(jokes);
	}
		
}
