package com.thelionking.datafetch.buffer;

import java.util.Comparator;
import java.util.List;

import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 * ������ΪЦ���������ڴ��еĻ��� Ц����Ϊ�� �ı���ͼƬ����Ƶ��ÿ�ַ����ַ�Ϊ ����ģʽ���Ƽ�ģʽ
 * 
 * ��ʱ�����ǲ�������
 * 
 * ��Ϊ���ǵ��û�����Ҫ����������ԣ�������Ƶ�����������ɾ�����������ѡ��ArrayListʵ��
 * 
 * �ڴ���JokeBuffer����ʱ���Զ�load����һ�Σ���˴˴�������ʾ�ĵ���JokeBuffer��load�ķ���
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
		//���������߳�
		new Thread(new BufferThread()).start();
	}

	/**
	 * ���ʵ��
	 */
	public static JokeBufferManager getInstance() {
		return SingletonHelper.INSTANCE;
	}

	/**
	 * 
	 * ����������
	 */
	private static class SingletonHelper {
		static JokeBufferManager INSTANCE = new JokeBufferManager();
	}

	/**
	 * �����µ�init���������ܻ�ȥ��һЩ����
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
	 * �����ڽ���
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
	 * ����������
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
	 * ��Ϊ��������ʹ��
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
	 * @version test ,����δ����
	 * ������������߳�,����ʱ����һ�Σ�ÿ���賿����һ�Σ�Ϊ�˱�֤˳��������һ����ˢ�£�����������50��������
	 *
	 */
	private class BufferThread implements Runnable{
	
		@Override
		public void run() {
			updateBuffer();
		}
		
		public void updateBuffer() {
			while(true){
				//���������������ǰ���ʱ�����updateBuffer����ռ����һ����ʱ�䣬���interval��ʱ����Գ���������֤���´�ˢ�µ�ʱ�������һ���0��
				long currentUpdated = System.currentTimeMillis();
				//��50�����Ŀ���Ǳ�֤������һ��(����0��+50����)
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
