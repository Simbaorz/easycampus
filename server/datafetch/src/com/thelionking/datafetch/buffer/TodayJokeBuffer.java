package com.thelionking.datafetch.buffer;

import java.util.List;

import com.thelionking.datafetch.buffer.JokeBufferManager.JokeData;
import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.service.UserService;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;
/**
 * 今日的缓存，按时间升序排列
 * @author the lion king
 *
 */
public final class TodayJokeBuffer extends JokeBuffer {
	
	public TodayJokeBuffer() {
		sort(new JokeBufferManager.FromTopCustomComparator());
	}
	
	/**
	 *@version test
	 * 此处有bug
	 */
	@Override
	protected JokeData get(List<Joke> jokes, int start) {
		System.out.println(jokes.size());
		JokeData jokeData = new JokeData();
		//当前时间戳
		long current = System.currentTimeMillis();
		//初始时是end为-1(代表一个无效值)
		//拿到的数据集合应该是从start到end的一个闭区间
		int end = -1;
		//是否执行过循环，若true表示执行过， false表示未执行过
		boolean flag = false;
		for(int i=start; i<jokes.size(); ++i){
			if(jokes.get(i).getDate() > current){
				//闭区间
				//客户端应该发jokes.size() + 1;
				//List<Joke>的subList方法返回的是自身的映射偏移量，这里不适用
				//暂时使用复制的
				end = i-1;
				break;
			}
			flag = true;
		}
		CommonUtil.print("end" + end);
		//1. 当人为的给出的start值得到的时间戳比当前的时间戳靠前，进入不了循环，此时end = -1
		//2. 当前时间戳已经超过今日最大时间戳循环会全部执行完毕，则end = -1
		//注：当前没找到好办法区别这二者，暂时先设置一个flag变量
		if(flag){
			if(end == -1){
				jokeData.list = CommonUtil.subList(jokes, start, jokes.size() - 1);
				jokeData.nextStart = INVALID_START;
			}else{
				jokeData.list = CommonUtil.subList(jokes, start, end);
				jokeData.nextStart = ++end;
			}
		}else{
			jokeData.nextStart = start;
		}
		return jokeData;
	}
	
	@Override
	protected synchronized void load() {
		UserService service = new UserService();
		long start = CommonUtil.getMillis(0);
		long end = start + Constant.DAY;
		text.addAll(service.getBuffer(start, end, Joke.TEXT, Joke.LASTEST_MODE));
		picture.addAll(service.getBuffer(start, end, Joke.PICTURE, Joke.LASTEST_MODE));
		vedio.addAll(service.getBuffer(start, end, Joke.VEDIO, Joke.LASTEST_MODE));
		updateSize();
	}

	@Override
	protected synchronized void update(List<Joke> text, List<Joke> picture, List<Joke> vedio) {
		text.clear();
		picture.clear();
		vedio.clear();
		load();
	}
	
}
