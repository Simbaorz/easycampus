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
	 * 在非DEBUG模式下输出
	 * @param msg
	 */
	public static void print(String msg) {
		if(Constant.DEBUG){
			System.out.println(msg);
		}
	}
	
	/**
	 * 拿到今天的日期（精确到天）
	 * @return
	 */
	public static java.sql.Date getTodayDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}
	
	/**
	 * 拿到昨天的日期(精确到天)
	 * @return
	 */
	public static java.sql.Date getYesterdayDate() {
		return new java.sql.Date(new java.util.Date().getTime() - Constant.DAY);
	}
	
	/**
	 * 获取数据库连接
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
	 * 粘结url地址
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
	 * 根据给出的hour获得起始毫秒数
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
	 * 根据给定的year, month, day , hour 获得指定的毫秒数
	 * 严重注意： month是从0开始的
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
	 * 根据给定的毫秒数返回时间的字符串形式
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
	 * 注意month是从0开始的，0是一月份 1-12月份对应0-11数字
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
