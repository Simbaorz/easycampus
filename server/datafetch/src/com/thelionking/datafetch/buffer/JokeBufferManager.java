package com.thelionking.datafetch.buffer;

import java.util.Comparator;
import java.util.List;

import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 * 此类作为笑话数据在内存中的缓存 笑话分为： 文本、图片、视频。每种分类又分为 最新模式与推荐模式
 * 
 * 暂时不考虑并发问题
 * 
 * 因为考虑到该缓存需要随机访问特性，而不会频繁的做插入跟删除操作，因此选用ArrayList实现
 * 
 * 在创建JokeBuffer对象时会自动load数据一次，因此此处不用显示的调用JokeBuffer的load的方法
 * 
 * Singleton
 * 
 * @author the lion king
 * 
 */
public final class JokeBufferManager {

	private TodayJokeBuffer today = new TodayJokeBuffer();
	private YesterdayJokeBuffer yesterday = new YesterdayJokeBuffer();
	private RecommendJokeBuffer recommend = new RecommendJokeBuffer();
	
	private JokeBufferManager() {
		//开启缓存线程
		new Thread(new BufferThread()).start();
	}

	/**
	 * 获得实例
	 */
	public static JokeBufferManager getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * 
	 * 单例帮助类
	 */
	private static class SingletonHelper {
		static JokeBufferManager INSTANCE = new JokeBufferManager();
	}

	/**
	 * 单例下的init方法，可能会去读一些配置
	 */
	public void init() {

	}
	
	private final void updateBuffer(){
		yesterday.update(today.getJokeList(Joke.TEXT), today.getJokeList(Joke.PICTURE), today.getJokeList(Joke.VEDIO));
		today.update(null, null, null);
		recommend.update(null, null, null);
	}
	
	public final JokeData getTodayBuffer(int type, int start) {
		return today.getJokes(type, start);
	}
	
	public final JokeData getYesterdayBuffer(int type, int start) {
		return yesterday.getJokes(type, start);
	}
	
	public final JokeData getRecommendBuffer(int type, int start){
		return null;
	}
	
	public JokeData getRemedy(int type, int remedy) {
		JokeData jokeData = today.getJokes(type, 0);
		List<Joke> jokes = yesterday.remedy(type, remedy);
		jokeData.list.addAll(jokes);
		return jokeData;
	}
	
	/**
	 * 按日期降序
	 *
	 */
	public static class FromBottomCustomComparator implements Comparator<Joke>{
		@Override
		public int compare(Joke joke1, Joke joke2) {
			if(joke1.getDate() == joke2.getDate()) {
				return 0;
			}else if(joke1.getDate() > joke2.getDate()) {
				return -1;
			}else{
				return 1;
			}
		}
	}
	
	/**
	 * 按日期升序
	 */
	public static class FromTopCustomComparator implements Comparator<Joke>{
		@Override
		public int compare(Joke joke1, Joke joke2) {
			if(joke1.getDate() == joke2.getDate()) {
				return 0;
			}else if(joke1.getDate() > joke2.getDate()) {
				return 1;
			}else{
				return -1;
			}
		}
	}
	
	/**
	 * 作为返回数据使用
	 * 
	 *
	 */
	public static class JokeData{
		public int nextStart;
		public List<Joke> list;

		public JokeData(){
			
		}
		
		public JokeData(List<Joke> jokes, int nextStart) {
			this.list = jokes;
			this.nextStart = nextStart;
		}
	}
	
	/**
	 * @version test ,代码未测试
	 * 缓存更新数据线程,开启时更新一次，每天凌晨更新一次，为了保证顺利进入下一天再刷新，故意增加了50毫秒的误差
	 *
	 */
	private class BufferThread implements Runnable{
	
		@Override
		public void run() {
			updateBuffer();
		}
		
		public void updateBuffer() {
			while(true){
				//此行语句放在这里，提前获得时间戳，updateBuffer函数占用了一定的时间，因此interval的时间会稍长，这样保证了下次刷新的时间过了下一天的0点
				long currentUpdated = System.currentTimeMillis();
				//加50毫秒的目的是保证进入下一天(明天0点+50毫秒)
				long nextUpdated = CommonUtil.getMillis(0) + Constant.DAY + 50;
				long interval = nextUpdated - currentUpdated;
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				JokeBufferManager.this.updateBuffer();
			}
		}
		
	}

}
