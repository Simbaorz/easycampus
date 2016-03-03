package com.thelionking.datafetch.action;

import com.opensymphony.xwork2.ActionSupport;
import com.thelionking.datafetch.model.fetcher.FetchUtil;
import com.thelionking.datafetch.service.SuperAdminService;

public class SuperAdminAction extends ActionSupport{
	public static final String TAG = "SuperAdminAction";
	
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
		
	public String open() {
		if(this.name != null && FetchUtil.isValidName(name)){
			SuperAdminService service = new SuperAdminService();
			boolean opened = service.open(this.name);
			if(opened){
				FetchUtil.setState(name, FetchUtil.OPENED_STATE);
				return SUCCESS;
			}
		}
		return ERROR;
	}
	
	public String close() {
		if(this.name != null && FetchUtil.isValidName(name)){
			SuperAdminService service = new SuperAdminService();
			boolean closed = service.close(this.name);
			if(closed){
				FetchUtil.setState(name, FetchUtil.CLOSED_STATE);
				return SUCCESS;
			}
		}
		return ERROR;
	}
	
	public String closeAll() {
		SuperAdminService service = new SuperAdminService();
		boolean closed = service.closeAll();
		if(closed) {
			for(String name : FetchUtil.FETCH_SOURCE_POOL) {
				FetchUtil.setState(name, FetchUtil.CLOSED_STATE);
			}
			return SUCCESS;
		}
		return ERROR;
	}
	
	public String openAll(){
		SuperAdminService service = new SuperAdminService();
		boolean open = service.openAll();
		if(open){
			for(String name : FetchUtil.FETCH_SOURCE_POOL) {
				FetchUtil.setState(name, FetchUtil.OPENED_STATE);
			}
			return SUCCESS;
		}
		return ERROR;
	}
	
	
	 
}
