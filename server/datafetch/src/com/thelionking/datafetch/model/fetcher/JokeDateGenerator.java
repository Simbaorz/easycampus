package com.thelionking.datafetch.model.fetcher;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

/**
 * 笑话时间的生成，只供DateManager使用
 * 
 * @author the lion king
 * 
 */
public final class JokeDateGenerator {
	//自己所属的id
	public int id;
	// 年份
	private int year;
	// 月份
	private int month;
	// 日
	private int day;
	// 小时
	private int hour;
	// 计数值，在1-CYCLE_VALUE之间循环，每循环一次，hour加1
	private int count;
	
	//当月的最大值
	private int maxDays;
	
	//抓取的循环间隔
	private final int cycleInterval;
	
	//propertiesDir的路径
	private String propertiesDir;
	
	public JokeDateGenerator(int id, String propertiesDir) {
		this.id = id;
		this.propertiesDir = propertiesDir;
		Properties p = Constant.read(this.propertiesDir);
		cycleInterval = Integer.parseInt(p.getProperty("generator_cycle_value"));
		year = Integer.parseInt(p.getProperty("generator_year"));
		month = Integer.parseInt(p.getProperty("generator_month"));
		day = Integer.parseInt(p.getProperty("generator_day"));
		hour = Integer.parseInt(p.getProperty("generator_hour"));
		count = Integer.parseInt(p.getProperty("generator_count"));
		maxDays = CommonUtil.calculateDaysOfMonth(year, month);
	}

	public long generateMillils() {
		long start = CommonUtil.getTime(year, month, day, hour);
		long time = start + (long)(Math.random() * Constant.HOUR);
		check();
		return time;
	}

	/**
	 * 随抓取次数增加时间
	 */
	private void check() {
		
		if(count > cycleInterval) {
			++hour;
			count = 1;
		}
		if(hour == 24) {
			++day;
			hour = 0;
		}
		if(day > maxDays){
			++month;
			day = 1;
			if(month == 12) {
				++year;
				month = 0;
			}
			maxDays = CommonUtil.calculateDaysOfMonth(year, month);
		}
		if(Constant.DEBUG){
			CommonUtil.print("ID : " + id + " Year ： " + year + "  Month : " + (month+1) + "  Day : " + day + " Hour : " + hour +"  Count : " + count + "  maxDays : " + maxDays);
		}
		++count;
	}
	
	/**
	 * 回滚
	 */
	public void rollback(int number) {
		
	}
	
	/**
	 * 更新
	 */
	public void write() {
		Properties p = new Properties();
		p.setProperty("generator_year", year + "");
		p.setProperty("generator_month", month + "");
		p.setProperty("generator_day", day + "");
		p.setProperty("generator_hour", hour + "");
		p.setProperty("generator_count", count + "");
		Constant.write(this.propertiesDir, p);
	}
	
}
