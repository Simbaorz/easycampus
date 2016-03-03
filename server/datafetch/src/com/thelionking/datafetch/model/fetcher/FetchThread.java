package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

public class FetchThread implements FetchThreadConfig.FetchStateListener, Runnable, IFetcher{
	
	public final static String TAG = "FetchThreadBase";
	
	/**
	 * ���̵߳İ汾���룬����������ʶ���̵߳ڼ�������
	 */
	private final int version;
	
	/**
	 * �Ƿ����������߳�
	 *  true����
	 *  false������
	 */
	private boolean runPermission = false;
	
	/**
	 * ץȡ������
	 */
	private FetcherContainer fetchers;
	
	/**
	 * ���ʶ��,ͨ����������ʼ����������ı�
	 */
	private final String name;
	

	/**
	 * �߳��Ƿ�ر�
	 */
	private volatile boolean isClosed = false;
	
	/**
	 * ���췽��
	 * @param name
	 */
	public FetchThread(String name, int version, FetcherContainer fetchers) {
		this.name = name;
		this.fetchers = fetchers;
		this.runPermission = false;
		this.version = version;
		if(FetchUtil.isValidName(this.name)) {
			FetchThreadConfig.getInstance().addListener(this.name, this);
		}
		
	}
	
	/**
	 * run����
	 */
	@Override
	public void run(){
		int count = 0;
		
		CommonUtil.print(TAG + ": NAME==" + this.name + "==VersionΪ==" + version + "�߳̿�ʼ����");
		
		while(!isClosed) {
		
			CommonUtil.print("===========ʱ�䣺 " + IFetcher.FORMAT.format(new java.util.Date()) + "===============");
			CommonUtil.print("===========�����ǵ� " + (++count) + " ��ץȡ" + "===============");
			CommonUtil.print("");
			CommonUtil.print("");
			CommonUtil.print("");
			
			
			fetch();
			try {
				Thread.sleep(Constant.FETCH_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			CommonUtil.print("");
			CommonUtil.print("");
			CommonUtil.print("");
		}
		
		CommonUtil.print(TAG + ": NAME==" + this.name + "==VersionΪ==" + version + "�߳�ֹͣ����");
		
	}
	
	/**
	 * ��ȡname
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * ���̴߳�֮ǰ����
	 */
	@Override
	public void onOpen(int code) {
		if(code == FetchThreadConfig.FetchStateListener.SUCCESSED) {
			runPermission = true;
			CommonUtil.print(TAG + "NAMEΪ : " + name + "==VersionΪ==" + version + "���߳̿�������");
			
		}else if(code == FetchThreadConfig.FetchStateListener.FAILED){
			runPermission = false;
			CommonUtil.print(TAG + "NAMEΪ : " + name + "==VersionΪ==" + version + "���߳̿���ʧ���ˣ�");
		}else{
			CommonUtil.print(TAG + "onOpenʧ��code����ִ���");
		}
	}
	
	/**
	 *	��ȡ�߳��Ƿ��������
	 * @return
	 */
	public boolean getRunPermission() {
		return this.runPermission;
	}
	
	/**
	 * �߳̽���֮ǰ����
	 */
	@Override
	public void onStop() {
		CommonUtil.print(TAG + "NAMEΪ : " + name + "==VersionΪ==" + version + "onStop����ִ��");
		this.isClosed = true;
		close();
	}
	
	@Override
	public void close() {
		this.fetchers.close();
	}

	@Override
	public void fetch() {
		this.fetchers.fetch();
	}
}
