package com.thelionking.datafetch.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.thelionking.datafetch.model.fetcher.TextContainer;

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
	public final static int FETCH_INTERVAL;

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
		
		// �����û����ȳ���
		if (LOCAL) {
			USER = "root";
			PASSWORD = "woshi721";
			PORT = "3306";
			HOST = "localhost";
			DB_NAME = "history";
		} else {
			DB_NAME = "app_jenson";
			USER = "oy11l13mln";
			PASSWORD = "5lzm31m1ii0jkx3i1hyxw5x311m5j2111342mi20";
			PORT = "3307";
			HOST = "w.rdc.sae.sina.com.cn";
		}

		// set fetch interval
		int interval = Integer.parseInt(p.getProperty("fetch_interval").trim());
		if (interval < 5 || interval > 60) {
			FETCH_INTERVAL = 5 * 1000;
		} else {
			FETCH_INTERVAL = interval * 1000;
		}

	}
	
	/**
	 * ����propertiesDir�������ļ�����ȡ��ֵ��
	 * @param propertiesDir
	 * @return
	 */
	public static Properties read(String propertiesDir) {
		String basePath = TextContainer.class.getResource("/").getPath();
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
	 * ����propertiesDir�������ļ�����Properties������¼�ֵ��
	 * @param propertiesDir
	 * @param properties
	 */
	public static void write(String propertiesDir, Properties properties) {
		String basePath = TextContainer.class.getResource("/").getPath();
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
