package com.thelionking.datafetch.model.fetcher;

import java.util.HashMap;
import java.util.Map;

public final class FetchUtil {
	
	//内涵段子
	public final static String NeiHanDuanZi = "NeiHanDuanZi";
	//糗事百科
	public final static String QiuShiBaiKe = "QiuShiBaiKe";
	//内涵吧
	public final static String NeiHanBa = "NeiHanBa";
	//百思不得姐
	public final static String BaiSiBuDeJie = "BaiSiBuDeJie";
	//暴走漫画
	public final static String BaoZouManHua = "BaoZouManHua";
	//内涵社区
	public final static String NeiHanSheQu = "NeiHanSheQu";
	
	public static final String CLOSED_STATE = "已关闭";
	public static final String OPENED_STATE = "已开启";
	
	public final static String[] FETCH_SOURCE_POOL = {
		NeiHanDuanZi, 
		QiuShiBaiKe,
		NeiHanBa,
		BaiSiBuDeJie,
		BaoZouManHua,
		NeiHanSheQu
	};
	
	public static final Map<String, String> threadState;
	
	static{
		threadState = new HashMap<String, String>();
		for(String name : FetchUtil.FETCH_SOURCE_POOL){
			threadState.put(name, CLOSED_STATE);
		}
	}
	
	

	public static boolean isValidName(String name) {
		for(String n : FETCH_SOURCE_POOL) {
			if(name.equals(n))
				return true;
		}
		return false;
	}
	
	/**
	 * 默认只有超级用户登录，因此暂时不提供线程安全
	 * @param name
	 * @param state
	 */
	public static void setState(String name, String state){
		threadState.put(name, state);
	}
}
