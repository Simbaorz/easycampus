package com.thelionking.campus.service;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import com.thelionking.campus.dao.BuildingDao;
import com.thelionking.campus.dao.CommentDao;
import com.thelionking.campus.dao.DiscussDao;
import com.thelionking.campus.dao.ProvinceDao;
import com.thelionking.campus.dao.RoomDao;
import com.thelionking.campus.dao.UserDao;
import com.thelionking.campus.model.Building;
import com.thelionking.campus.model.Comment;
import com.thelionking.campus.model.Discuss;
import com.thelionking.campus.model.Province;
import com.thelionking.campus.model.Room;
import com.thelionking.campus.model.User;
import com.thoughtworks.xstream.XStream;

public class UserService {
	//获取所有省份
	public String getAllProvinces() {
		Result<Province> result = new Result<Province>();
		XStream xStream = getAliasXStream();
		String sql = "select * from provinces";
		String[] args = null;
		ProvinceDao pd = new ProvinceDao();
		List<Province> p = pd.getBySQL(sql, args);
		result.content = p;
		xStream.aliasType("province", Province.class);
		return xStream.toXML(result);
	}
	//通过学校Id获取自习楼
	public String getBuildingsBySchoolId(int schoolId) {
		Result<Building> result = new Result<Building>();
		XStream xStream = getAliasXStream();
		String sql = "select * from buildings where school_id=?";
		String[] args = {schoolId + ""};
		BuildingDao bd = new BuildingDao();
		List<Building> b = bd.getBySQL(sql, args);
		result.content = b;
		xStream.aliasType("building", Building.class);
		return xStream.toXML(result);
	}
	//通过自习楼Id获取自习室
	public String getRoomsByBuildingId(int buildingId) {
		Result<Room> result = new Result<Room>();
		XStream xStream = getAliasXStream();
		String sql = "select * from rooms where building_id=?";
		String[] args = {buildingId + ""};
		RoomDao rd = new RoomDao();
		List<Room> rr = rd.getBySQL(sql, args);
		result.content = rr;
		xStream.aliasType("room", Room.class);
		return xStream.toXML(result);
	}
	//通过自习室Id获取讨论
	public String getDiscussesByRoomId(int roomId) {
		Result<Discuss> result = new Result<Discuss>();
		XStream xStream = getAliasXStream();
		String sql = "select * from discusses where room_id=?";
		String[] args = {roomId + ""};
		DiscussDao dd = new DiscussDao();
		List<Discuss> rr = dd.getBySQL(sql, args);
		result.content = rr;
		xStream.aliasType("discuss", Discuss.class);
		return xStream.toXML(result);
	}
	//通过用户Id获取讨论
	public String getDiscussesByUserId(int userId) {
		Result<Discuss> result = new Result<Discuss>();
		XStream xStream = getAliasXStream();
		String sql = "select * from discusses where discuss_user_id=?";
		String[] args = {userId + ""};
		DiscussDao dd = new DiscussDao();
		List<Discuss> rr = dd.getBySQL(sql, args);
		result.content = rr;
		xStream.aliasType("discuss", Discuss.class);
		return xStream.toXML(result);
	}
	//插入讨论
//	public String insertDiscuss(Discuss discuss) {
//		Result<Void> result = new Result<Void>();
//		XStream xStream = getAliasXStream();
//		DiscussDao dd = new DiscussDao();
//		if(!dd.insert(discuss)){
//			result.status = Result.ERROR_CODE;
//		}
//		return xStream.toXML(result);
//	}
	//插入评论
//	public String insertComment(Comment comment){
//		Result<Void> result = new Result<Void>();
//		XStream xStream = getAliasXStream();
//		CommentDao cd = new CommentDao();
//		if(!cd.insert(comment)){
//			result.status = Result.ERROR_CODE;
//		}
//		return xStream.toXML(result);
//	}
	//通过讨论Id获得评论
	public String getCommentsByDiscussId(int discussId) {
		Result<Comment> result = new Result<Comment>();
		XStream xStream = getAliasXStream();
		String sql = "select * from comments where discuss_id=?";
		String[] args = {discussId + ""};
		CommentDao cd = new CommentDao();
		List<Comment> rr = cd.getBySQL(sql, args);
		result.content = rr;
		xStream.aliasType("comment", Comment.class);
		return xStream.toXML(result);
	}
	//通过用户Id获取评论
	public String getCommentsByUserId(int userId) {
		Result<Comment> result = new Result<Comment>();
		XStream xStream = getAliasXStream();
		String sql = "select * from comments where comment_user_id=?";
		String[] args = {userId + ""};
		CommentDao cd = new CommentDao();
		List<Comment> rr = cd.getBySQL(sql, args);
		result.content = rr;
		xStream.aliasType("comment", Comment.class);
		return xStream.toXML(result);
	}
	
	//登录
	public String login(String userName, String userPassword) {
		Result<User> result = new Result<User>();
		XStream xStream = getAliasXStream();
		//去空字符
//		userName = userName.trim();
//		userPassword = userPassword.trim();
		//如果账号密码有问题返回
		if(userName.equals("") || userName == null || userPassword == null || userPassword.equals("")){
			result.status = Result.ERROR_CODE_ACCOUNT_NOT_EXISTS;
			return xStream.toXML(result);
		}
		String sql = "select * from users where user_name=?";
		String[] args = {userName + ""};
		UserDao ud = new UserDao();
		List<User> users = ud.getBySQL(sql, args);
		//返回数据为空
		if(users == null || users.size() == 0){
			result.status = Result.ERROR_CODE_ACCOUNT_NOT_EXISTS;
			return xStream.toXML(result);
		}
		User user = users.get(0);
		if(!user.getUserPassword().equals(userPassword)){
			result.status = Result.ERROR_CODE_PASSWORD_ERROR;
			return xStream.toXML(result);
		}
		xStream.aliasType("user", User.class);
		result.content = users;
		return xStream.toXML(result);
	}
	
	/*
	 * 签到加分
	 */
	public String addSignInScore(int userId) {
		Result<Void> result = new Result<Void>();
		XStream xStream = getAliasXStream();
//		if(!isPostMethod()){
//			result.status = Result.ERROR_CODE;
//			return xStream.toXML(result);
//		}else{
			UserDao ud = new UserDao();
			if(!ud.updateScore(userId, 5)){
				result.status = Result.ERROR_CODE;
			}
			return xStream.toXML(result);
//		}
	}
	
	//判断是否是Post方法
	private final boolean isPostMethod() {
		String method = ServletActionContext.getRequest().getMethod();
		return method.toUpperCase().equals("POST");
	}
	//返回有过修改的流
	private final XStream getAliasXStream(){
		XStream xStream = new XStream();
		xStream.aliasType("result", Result.class);
		return xStream;
	}
	
//	public static void main(String[] args) {
//		UserService us = new UserService();
//		System.out.println(us.addSignInScore(1, 100));
//	}
	
	
	/**
	 * 返回的数据格式
	 * <result>
	 * 	<status></status>
	 * 	<content></content>
	 * </result>
	 * @param <T>
	 */
	public static final class Result<T>{
		public static final int ERROR_CODE_ACCOUNT_NOT_EXISTS = 2;
		public static final int ERROR_CODE_PASSWORD_ERROR = 3;
		public static final int ERROR_CODE_ADD_SCORE_ERROR = 4;
		
		public static final int ERROR_CODE_REPEAT_USERNAME = 5;
		public static final int ERROR_CODE = 1;
		public static final int RIGHT_CODE = 0;
		int status = RIGHT_CODE;
		List<T> content;	
		
		public Result(){
			this.content = null;
			this.status = RIGHT_CODE;
		}
	}

	public String publishDiscuss(Discuss discuss) {
		Result<Void> result = new Result<Void>();
		XStream xStream = getAliasXStream();
//		if(!isPostMethod()){
//			result.status = Result.ERROR_CODE;
//			return xStream.toXML(result);
//		}else{
			DiscussDao dd = new DiscussDao();
			if(!dd.insert(discuss)){
				result.status = Result.ERROR_CODE;
				return xStream.toXML(result);
			}
			UserDao ud = new UserDao();
			ud.updateScore(discuss.getDiscussUserId(), 10);
			return xStream.toXML(result);
//		}
	}
	public String publishComment(Comment comment) {
		Result<Void> result = new Result<Void>();
		XStream xStream = getAliasXStream();
//		if(!isPostMethod()){
//			result.status = Result.ERROR_CODE;
//			return xStream.toXML(result);
//		}else{
			CommentDao cd = new CommentDao();
			if(!cd.insert(comment)){
				result.status = Result.ERROR_CODE;
				return xStream.toXML(result);
			}
			UserDao ud = new UserDao();
			ud.updateScore(comment.getCommentUserId(), 5);
			return xStream.toXML(result);
//		}
	}
	public String register(User user) {
		Result<User> result = new Result<User>();
		XStream xStream = getAliasXStream();
		xStream.aliasType("user", User.class);
		UserDao ud = new UserDao();
		String sql = "select * from users where user_name=?";
		String[] args = {user.getUserName() + ""};
		List<User> users = ud.getBySQL(sql, args);
		if(users!= null){
			if(users.size() > 0){
				result.status = Result.ERROR_CODE_REPEAT_USERNAME;
				return xStream.toXML(result);	
			}
		}
		
		
		if(!ud.insert(user)){
			result.status = Result.ERROR_CODE;
		}else{
			List<User> newUsers = ud.getBySQL(sql, args);
			result.content = newUsers;
		}
		return xStream.toXML(result);	
	}
	
	public String estimateSelfStudyRoomUseRate(int userId, int roomId, int rate) {
		Result<Void> result = new Result<Void>();
		XStream xStream = getAliasXStream();
		if(rate > 100 || rate < 0){
			result.status = Result.ERROR_CODE;
		}
		return xStream.toXML(result);
	}
	
}
