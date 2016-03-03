package com.thelionking.datafetch.buffer;

import java.util.List;

import com.thelionking.datafetch.buffer.JokeBufferManager.JokeData;
import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.service.UserService;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 *Buffer中的List不可能为null，之可能size为0
 */
public final class YesterdayJokeBuffer extends JokeBuffer{
	public YesterdayJokeBuffer() {
		sort(new JokeBufferManager.FromBottomCustomComparator());
	}
	
	@Override
	protected JokeData get(List<Joke> jokes, int start) {
		JokeData jokeData = new JokeData();
		System.out.println("Start : " + start);
		int end = start + 4;
		if(end < jokes.size() -1) {
			jokeData.list = CommonUtil.subList(jokes, start, end);
			jokeData.nextStart = end + 1;
		}else if(end >= jokes.size() -1){
			jokeData.list = CommonUtil.subList(jokes, start, jokes.size() -1);
			jokeData.nextStart = -1;
		}
		return jokeData;
	}

	@Override
	protected synchronized void load() {
		UserService service = new UserService();
		long end = CommonUtil.getMillis(0);
		long start = end - Constant.DAY;
		text.addAll(service.getBuffer(start, end, Joke.TEXT, Joke.LASTEST_MODE));
		picture.addAll(service.getBuffer(start, end, Joke.PICTURE, Joke.LASTEST_MODE));
		vedio.addAll(service.getBuffer(start, end, Joke.VEDIO, Joke.LASTEST_MODE));
		updateSize();
	}

	@Override
	protected synchronized void update(List<Joke> text, List<Joke> picture, List<Joke> vedio) {
		UserService service = new UserService();
		long end = CommonUtil.getMillis(0);
		long start = end - Constant.DAY;
		this.text.clear();
		this.picture.clear();
		this.vedio.clear();
		if(text.size() != 0) {
			this.text.addAll(text);
		}else{
			text.addAll(service.getBuffer(start, end, Joke.TEXT, Joke.LASTEST_MODE));
		}
		if(picture.size() != 0) {
			this.picture.addAll(picture);
		}else{
			picture.addAll(service.getBuffer(start, end, Joke.PICTURE, Joke.LASTEST_MODE));
		}
		if(vedio.size() != 0) {
			this.vedio.addAll(vedio);
		}else{
			vedio.addAll(service.getBuffer(start, end, Joke.VEDIO, Joke.LASTEST_MODE));
		}
		updateSize();
	}
	
	/**
	 * 补足客户端昨日不足的joke（暂时不使用）
	 * @param remedy
	 * @param type
	 * @return
	 */
	public List<Joke> remedy(int type, int remedy){
		List<Joke> jokes = null;
		if(type == Joke.TEXT) {
			jokes = CommonUtil.subList(text, text.size() - remedy, text.size() - 1);
		}
		else if(type == Joke.PICTURE){
			jokes = CommonUtil.subList(picture, picture.size() - remedy, picture.size() - 1);
		}
		else if(type == Joke.VEDIO) {
			jokes = CommonUtil.subList(vedio, vedio.size() - remedy, vedio.size() - 1);
		}
		return jokes;
	}
	
}
