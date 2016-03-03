package com.thelionking.datafetch.service;

import com.thelionking.datafetch.model.fetcher.FetchManager;
import com.thelionking.datafetch.model.fetcher.FetchThread;
import com.thelionking.datafetch.model.fetcher.FetchThreadConfig;
import com.thelionking.datafetch.model.fetcher.FetchUtil;
import com.thelionking.datafetch.util.CommonUtil;


public class SuperAdminService {
	public static final String TAG = "SuperAdminService";
	
	public boolean open(String name) {
		if(name.equals(FetchUtil.NeiHanBa)){
			FetchManager.getInstance().createTextThread();
			return true;
		}
		else if(name.equals(FetchUtil.NeiHanSheQu)){
			FetchManager.getInstance().createPictureThread();
			return true;
		}
		return true;
	}
	
	public boolean close(String name) {
		FetchThreadConfig.getInstance().close(name);
		return true;
	}
	
	public boolean closeAll() {
		FetchThreadConfig.getInstance().closeAllThreads();
		return true;
	}
	
	public boolean openAll() {
		CommonUtil.print("暂时不提供该服务");
		return true;
	}
}
