package com.thelionking.campus.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public final class CommonUtil {
	/**
	 * �ڷ�DEBUGģʽ�����
	 * @param msg
	 */
	public static void print(String msg) {
		if(Constant.DEBUG){
			System.out.println(msg);
		}
	}
	
	/**
	 * �õ���������ڣ���ȷ���죩
	 * @return
	 */
	public static java.sql.Date getTodayDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}
	
	/**
	 * �õ����������(��ȷ����)
	 * @return
	 */
	public static java.sql.Date getYesterdayDate() {
		return new java.sql.Date(new java.util.Date().getTime() - Constant.DAY);
	}
	
	/**
	 * ��ȡ���ݿ�����
	 * @return
	 */
	public static Connection connect(){
		Connection conn = null;
		try{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(splice());
			return conn;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * ճ��url��ַ
	 * @return
	 */
	public static String  splice(){
		StringBuilder sb = new StringBuilder();
		sb.append("jdbc:mysql://");
		sb.append(Constant.HOST + ":" + Constant.PORT);
		sb.append("/" + Constant.DB_NAME);
		sb.append("?user=" + Constant.USER);
		sb.append("&password=" + Constant.PASSWORD);
		return sb.toString();
	}
	
	/**
	 * ���ݸ�����hour�����ʼ������
	 * @param hour
	 * @return
	 */
	public static long getMillis(int hour){
		Calendar c = Calendar.getInstance(); 
		c.set(Calendar.HOUR_OF_DAY, hour); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
	
	/**
	 * ���ݸ�����year, month, day , hour ���ָ���ĺ�����
	 * ����ע�⣺ month�Ǵ�0��ʼ��
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @return
	 */
	public static long getTime(int year, int month, int day, int hour){
		Calendar c = Calendar.getInstance(); 
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour); 
		c.set(Calendar.SECOND, 0); 
		c.set(Calendar.MINUTE, 0); 
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}
	
	/**
	 * ���ݸ����ĺ���������ʱ����ַ�����ʽ
	 * @param millis
	 * @return
	 */
	public static String getDate(long millis) {
		java.util.Date d = new java.util.Date();
		d.setTime(millis);
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return f.format(d);
	}
	
	/**
	 * ע��month�Ǵ�0��ʼ�ģ�0��һ�·� 1-12�·ݶ�Ӧ0-11����
	 * @param year
	 * @param month
	 * @return
	 */
	public static int calculateDaysOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();   
		calendar.set(year, month, 1);
		int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
		return maxDay;
	}
	
}
