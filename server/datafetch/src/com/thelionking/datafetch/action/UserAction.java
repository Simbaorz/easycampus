package com.thelionking.datafetch.action;

import com.opensymphony.xwork2.ActionSupport;
import com.thelionking.datafetch.service.UserService;
import com.thelionking.datafetch.util.CommonUtil;
import com.thelionking.datafetch.util.Constant;

public class UserAction extends ActionSupport{
	/**
	 * generate serial version uid;
	 */
	private static final long serialVersionUID = 187816161550447801L;
	
	/**
	 * TAG
	 */
	public static final String TAG = "UserAction";
	
	/**
	 * 返回的数据
	 */
	private String result = null;
	
	/**
	 * 日期(用毫秒数表示)
	 */
	private long date = 0L;
	

	/**
	 * 刷新类型(段子，或图片)
	 */
	private int type = 0;
	
	/**
	 * 是否是推荐模式
	 */
	private int mode = 0;
	
	/**
	 * start
	 * @return
	 */
	private int start = 0;
	
	/**
	 * remedy
	 * @return
	 */
	private int remedy = 0;
	
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	public String welcome() {
		return SUCCESS;
	}
	
	public int getStart(){
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public void setRemedy(int remedy){
		this.remedy = remedy;
	}
	
	public int getRemedy(){
		return remedy;
	}

	/**
	 * 当下拉刷新的时候
	 * @return
	 */
	public String getJokesFromTop() {
		UserService userService = new UserService();
		if(Constant.DEBUG) {
			CommonUtil.print("date : " + date);
			CommonUtil.print("type : " + type);
			CommonUtil.print("mode : " + mode);
			CommonUtil.print("topStart : " + start);
		}
		result = userService.getJokesFromTop(date, type, mode, start);
		return SUCCESS;
	}
	
	/**
	 * 当从底部刷新时调用
	 * @return
	 */
	public String getJokesFromBottom() {
		UserService userService = new UserService();
		if(Constant.DEBUG) {
			CommonUtil.print("date : " + date);
			CommonUtil.print("type : " + type);
			CommonUtil.print("mode : " + mode);
			CommonUtil.print("bottomStart : " + start);
		}
		result = userService.getJokesFromBottom(date, type, mode, start);
		return SUCCESS;
	}
	
	public String getRemedyAction() {
		UserService service = new UserService();
		result = service.getRemedy(type, remedy);
		return SUCCESS;
	}
	
	/**
	 * 当从底部刷新时调用
	 * @return
	 */
	public String getJokes() {
		UserService userService = new UserService();
		result = userService.getJokes(id, type, mode);
		return SUCCESS;
	}
}
