package com.thelionking.datafetch.buffer;

import java.util.List;

import com.thelionking.datafetch.buffer.JokeBufferManager.JokeData;
import com.thelionking.datafetch.model.Joke;
import com.thelionking.datafetch.service.UserService;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;
/**
 * ���յĻ��棬��ʱ����������
 * @author the lion king
 *
 */
public final class TodayJokeBuffer extends JokeBuffer {
	
	public TodayJokeBuffer() {
		sort(new JokeBufferManager.FromTopCustomComparator());
	}
	
	/**
	 *@version test
	 * �˴���bug
	 */
	@Override
	protected JokeData get(List<Joke> jokes, int start) {
		System.out.println(jokes.size());
		JokeData jokeData = new JokeData();
		//��ǰʱ���
		long current = System.currentTimeMillis();
		//��ʼʱ��endΪ-1(����һ����Чֵ)
		//�õ������ݼ���Ӧ���Ǵ�start��end��һ��������
		int end = -1;
		//�Ƿ�ִ�й�ѭ������true��ʾִ�й��� false��ʾδִ�й�
		boolean flag = false;
		for(int i=start; i<jokes.size(); ++i){
			if(jokes.get(i).getDate() > current){
				//������
				//�ͻ���Ӧ�÷�jokes.size() + 1;
				//List<Joke>��subList�������ص��������ӳ��ƫ���������ﲻ����
				//��ʱʹ�ø��Ƶ�
				end = i-1;
				break;
			}
			flag = true;
		}
		CommonUtil.print("end" + end);
		//1. ����Ϊ�ĸ�����startֵ�õ���ʱ����ȵ�ǰ��ʱ�����ǰ�����벻��ѭ������ʱend = -1
		//2. ��ǰʱ����Ѿ������������ʱ���ѭ����ȫ��ִ����ϣ���end = -1
		//ע����ǰû�ҵ��ð취��������ߣ���ʱ������һ��flag����
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
