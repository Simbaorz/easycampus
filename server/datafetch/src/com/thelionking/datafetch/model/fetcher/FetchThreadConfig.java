package com.thelionking.datafetch.model.fetcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 * 服务器的配置信息 此项目适用一单个JVM环境下，不适用集群 这是一个有状态的单例
 * 
 * @author thelionking(狮子王)
 * 
 */
public final class FetchThreadConfig {
	/**
	 * TAG
	 */
	public static final String TAG = "FetchThreadConfig";

	/**
	 * 线程阀(控制开关),valve值能由管理员操作
	 */
	private Map<String, FetchStateListener> valve;
	
	/**
	 * 闭合的构造方法
	 * 
	 */
	private FetchThreadConfig() {
		valve = new HashMap<String, FetchStateListener>();
	}

	/**
	 * 初始化方法,可能去读一些配置
	 */
	public void init() {

	}

	/**
	 * 单例帮助类
	 * 
	 */
	private static final class InstanceHelper {
		static FetchThreadConfig INSTANCE = new FetchThreadConfig();
	}

	/**
	 * 实例的获得
	 * 
	 * @return instance
	 */
	public static FetchThreadConfig getInstance() {
		return InstanceHelper.INSTANCE;
	}
	
	/**
	 * 根据name关闭线程
	 * 
	 * @param name
	 * @return
	 */
	public void close(final String name) {
		synchronized(valve) {
			if (valve.containsKey(name)) {
				valve.get(name).onStop();
				//将日期生成器存储到本地
				valve.remove(name);
			} else {
				if(Constant.DEBUG){
					CommonUtil.print("Valve中不存在NAME为 ： " + name + "线程");
				}
			}
		}
		

	}

	/**
	 * 关闭所有线程
	 */
	public void closeAllThreads() {
		synchronized(valve){
			for (String name : valve.keySet()) {
				valve.get(name).onStop();				
			}
			//将日期生成器存储到本地
			valve.clear();
		}
		
	}

	/**
	 * 抓取线程状态监听器
	 * 
	 */
	public static interface FetchStateListener {
		public static final int FAILED = 0;
		public static final int SUCCESSED = 1;

		public void onOpen(int code);

		public void onStop();

	}
	
	/**
	 * 添加FetchStateListener到valve中
	 * @param name
	 * @param listener
	 */
	public void addListener(final String name, final FetchStateListener listener) {
		synchronized(valve) {
			if (valve.containsKey(name)) {
				listener.onOpen(FetchStateListener.FAILED);
				CommonUtil.print("NAME为 ： " + name + "的抓取线程已经存在，请稍后尝试！");
			} else {
				valve.put(name, listener);
				listener.onOpen(FetchStateListener.SUCCESSED);
				CommonUtil.print("NAME为 ： " + name + "的抓取线程添加成功！");
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
