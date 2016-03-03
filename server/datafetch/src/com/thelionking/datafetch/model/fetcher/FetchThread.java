package com.thelionking.datafetch.model.fetcher;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

public class FetchThread implements FetchThreadConfig.FetchStateListener, Runnable, IFetcher{
	
	public final static String TAG = "FetchThreadBase";
	
	/**
	 * 该线程的版本号码，可以用来标识该线程第几次启动
	 */
	private final int version;
	
	/**
	 * 是否允许运行线程
	 *  true允许
	 *  false不允许
	 */
	private boolean runPermission = false;
	
	/**
	 * 抓取器容器
	 */
	private FetcherContainer fetchers;
	
	/**
	 * 身份识别,通过构造器初始化，不允许改变
	 */
	private final String name;
	

	/**
	 * 线程是否关闭
	 */
	private volatile boolean isClosed = false;
	
	/**
	 * 构造方法
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
	 * run方法
	 */
	@Override
	public void run(){
		int count = 0;
		
		CommonUtil.print(TAG + ": NAME==" + this.name + "==Version为==" + version + "线程开始工作");
		
		while(!isClosed) {
		
			CommonUtil.print("===========时间： " + IFetcher.FORMAT.format(new java.util.Date()) + "===============");
			CommonUtil.print("===========本次是第 " + (++count) + " 次抓取" + "===============");
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
		
		CommonUtil.print(TAG + ": NAME==" + this.name + "==Version为==" + version + "线程停止工作");
		
	}
	
	/**
	 * 获取name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 在线程打开之前调用
	 */
	@Override
	public void onOpen(int code) {
		if(code == FetchThreadConfig.FetchStateListener.SUCCESSED) {
			runPermission = true;
			CommonUtil.print(TAG + "NAME为 : " + name + "==Version为==" + version + "的线程开启啦！");
			
		}else if(code == FetchThreadConfig.FetchStateListener.FAILED){
			runPermission = false;
			CommonUtil.print(TAG + "NAME为 : " + name + "==Version为==" + version + "的线程开启失败了！");
		}else{
			CommonUtil.print(TAG + "onOpen失败code码出现错误");
		}
	}
	
	/**
	 *	获取线程是否可以运行
	 * @return
	 */
	public boolean getRunPermission() {
		return this.runPermission;
	}
	
	/**
	 * 线程结束之前调用
	 */
	@Override
	public void onStop() {
		CommonUtil.print(TAG + "NAME为 : " + name + "==Version为==" + version + "onStop方法执行");
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
