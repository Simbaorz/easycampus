package com.thelionking.datafetch.model.fetcher;

import java.util.HashMap;
import java.util.Map;

public final class FetchUtil {
	
	//�ں�����
	public final static String NeiHanDuanZi = "NeiHanDuanZi";
	//���°ٿ�
	public final static String QiuShiBaiKe = "QiuShiBaiKe";
	//�ں���
	public final static String NeiHanBa = "NeiHanBa";
	//��˼���ý�
	public final static String BaiSiBuDeJie = "BaiSiBuDeJie";
	//��������
	public final static String BaoZouManHua = "BaoZouManHua";
	//�ں�����
	public final static String NeiHanSheQu = "NeiHanSheQu";
	
	public static final String CLOSED_STATE = "�ѹر�";
	public static final String OPENED_STATE = "�ѿ���";
	
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
	 * Ĭ��ֻ�г����û���¼�������ʱ���ṩ�̰߳�ȫ
	 * @param name
	 * @param state
	 */
	public static void setState(String name, String state){
		threadState.put(name, state);
	}
}
