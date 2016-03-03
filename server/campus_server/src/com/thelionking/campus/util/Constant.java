package com.thelionking.campus.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Constant {

	public static final long SECOND = 1000L;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	
	public static final int BATCH_COUNT;
	
	public static final String DB_NAME;
	public static final String USER;
	public static final String PASSWORD;
	public static final String PORT;
	public static final String HOST;

	public final static boolean DEBUG;
	public final static boolean LOCAL;

	static {
		Properties p = read("launch.properties");
		//set batch count;
		int batchCount = Integer.parseInt(p.getProperty("batch_count").trim());
		if( batchCount < 5 || batchCount > 30) {
			BATCH_COUNT = batchCount;
		}else{
			BATCH_COUNT = 20;
		}
		
		if (p.getProperty("debug").trim().toLowerCase().equals("false")) {
			DEBUG = false;
		} else {
			DEBUG = true;
		}

		// set local
		if (p.getProperty("local").trim().toLowerCase().equals("false")) {
			LOCAL = false;
		} else {
			LOCAL = true;
		}
		
		// 设置用户名等常量
		if (LOCAL) {
			USER = "root";
			PASSWORD = "woshi721";
			PORT = "3306";
			HOST = "localhost";
			DB_NAME = "campus_server";
		} else {
			DB_NAME = "app_servercampus";
			USER = "1ynl0y05lx";
			PASSWORD = "1mimm2110x130hw22jil0531xxhk2hl2l3kwiwjx";
			PORT = "3307";
			HOST = "w.rdc.sae.sina.com.cn";
		}

	
	}
	
	/**
	 * 根据propertiesDir给出的文件名读取键值对
	 * @param propertiesDir
	 * @return
	 */
	public static Properties read(String propertiesDir) {
		String basePath = Constant.class.getResource("/").getPath();
		InputStream in = null;
		try {
			Properties p = new Properties();
			in = new BufferedInputStream(new FileInputStream(basePath
					+ "/" + propertiesDir));
			p.load(in);
			return p;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			// try{
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// }
		}

	}
	
	/**
	 * 根据propertiesDir给出的文件名和Properties对象更新键值对
	 * @param propertiesDir
	 * @param properties
	 */
	public static void write(String propertiesDir, Properties properties) {
		String basePath = Constant.class.getResource("/").getPath();
		InputStream in = null;
		OutputStream out = null;
		try {
			Properties p = new Properties();
			in = new BufferedInputStream(new FileInputStream(basePath + "/" + propertiesDir));
			p.load(in);
			out = new BufferedOutputStream(new FileOutputStream(basePath + "/" + propertiesDir));
			
			for(Object o : properties.keySet()){
				p.setProperty(((String)o), properties.getProperty(((String)o)));
			}
			p.store(out, "");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// try{
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// }
		}
		
	}
	
}
