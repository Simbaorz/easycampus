package com.thelionking.datafetch.model.fetcher;

import java.text.SimpleDateFormat;

/**
 * fetch的根接口
 * @author the lion king
 *
 */
public interface IFetcher {
	
	public static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

	public void fetch();
	
	public void close();
	
}

