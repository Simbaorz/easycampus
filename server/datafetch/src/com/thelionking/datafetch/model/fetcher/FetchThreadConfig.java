package com.thelionking.datafetch.model.fetcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 * ��������������Ϣ ����Ŀ����һ����JVM�����£������ü�Ⱥ ����һ����״̬�ĵ���
 * 
 * @author thelionking(ʨ����)
 * 
 */
public final class FetchThreadConfig {
	/**
	 * TAG
	 */
	public static final String TAG = "FetchThreadConfig";

	/**
	 * �̷߳�(���ƿ���),valveֵ���ɹ���Ա����
	 */
	private Map<String, FetchStateListener> valve;
	
	/**
	 * �պϵĹ��췽��
	 * 
	 */
	private FetchThreadConfig() {
		valve = new HashMap<String, FetchStateListener>();
	}

	/**
	 * ��ʼ������,����ȥ��һЩ����
	 */
	public void init() {

	}

	/**
	 * ����������
	 * 
	 */
	private static final class InstanceHelper {
		static FetchThreadConfig INSTANCE = new FetchThreadConfig();
	}

	/**
	 * ʵ���Ļ��
	 * 
	 * @return instance
	 */
	public static FetchThreadConfig getInstance() {
		return InstanceHelper.INSTANCE;
	}
	
	/**
	 * ����name�ر��߳�
	 * 
	 * @param name
	 * @return
	 */
	public void close(final String name) {
		synchronized(valve) {
			if (valve.containsKey(name)) {
				valve.get(name).onStop();
				//�������������洢������
				valve.remove(name);
			} else {
				if(Constant.DEBUG){
					CommonUtil.print("Valve�в�����NAMEΪ �� " + name + "�߳�");
				}
			}
		}
		

	}

	/**
	 * �ر������߳�
	 */
	public void closeAllThreads() {
		synchronized(valve){
			for (String name : valve.keySet()) {
				valve.get(name).onStop();				
			}
			//�������������洢������
			valve.clear();
		}
		
	}

	/**
	 * ץȡ�߳�״̬������
	 * 
	 */
	public static interface FetchStateListener {
		public static final int FAILED = 0;
		public static final int SUCCESSED = 1;

		public void onOpen(int code);

		public void onStop();

	}
	
	/**
	 * ���FetchStateListener��valve��
	 * @param name
	 * @param listener
	 */
	public void addListener(final String name, final FetchStateListener listener) {
		synchronized(valve) {
			if (valve.containsKey(name)) {
				listener.onOpen(FetchStateListener.FAILED);
				CommonUtil.print("NAMEΪ �� " + name + "��ץȡ�߳��Ѿ����ڣ����Ժ��ԣ�");
			} else {
				valve.put(name, listener);
				listener.onOpen(FetchStateListener.SUCCESSED);
				CommonUtil.print("NAMEΪ �� " + name + "��ץȡ�߳���ӳɹ���");
			}
		}
	}
	
	public boolean containsThread(String name) {
		synchronized (name) {
			if(valve.containsKey(name))
				return true;
			else
				return false;
		}
	}
	
	public Set<String> getState() {
		synchronized (valve) {
			return valve.keySet();
		}
	}
	
}
