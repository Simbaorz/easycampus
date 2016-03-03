package com.thelionking.campus.action;

import com.opensymphony.xwork2.ActionSupport;
import com.thelionking.campus.model.Comment;
import com.thelionking.campus.model.Discuss;
import com.thelionking.campus.model.User;
import com.thelionking.campus.service.UserService;

public class UserAction extends ActionSupport {
	public String result;
	public Discuss discuss;
	public Comment comment;
	public User user;

	private int discussId;
	private int userId;
	private int score;
	private int commentId;
	private int id;
	private int roomId;
	
	private int rate;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void setDiscuss(Discuss discuss) {
		this.discuss = discuss;
	}

	public Discuss getDiscuss() {
		return discuss;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
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

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getDiscussId() {
		return discussId;
	}

	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String getAllProvinces() {
		UserService ps = new UserService();
		result = ps.getAllProvinces();
		return SUCCESS;
	}

	public String getBuildingsBySchoolId() {
		UserService ps = new UserService();
		result = ps.getBuildingsBySchoolId(id);
		return SUCCESS;
	}

	public String getRoomsByBuildingId() {
		UserService ps = new UserService();
		result = ps.getRoomsByBuildingId(id);
		return SUCCESS;
	}

//	public String insertDiscuss() {
//		UserService us = new UserService();
//		result = us.insertDiscuss(discuss);
//		return SUCCESS;
//	}

	public String getCommentsByDiscussId() {
		UserService ps = new UserService();
		result = ps.getCommentsByDiscussId(id);
		return SUCCESS;
	}

	public String getCommentsByUserId() {
		UserService ps = new UserService();
		result = ps.getCommentsByUserId(id);
		return SUCCESS;
	}

	public String getDiscussesByUserId() {
		UserService ps = new UserService();
		result = ps.getDiscussesByUserId(id);
		return SUCCESS;
	}

	public String getDiscussesByRoomId() {
		UserService ps = new UserService();
		result = ps.getDiscussesByRoomId(id);
		return SUCCESS;
	}

//	public String insertComment() {
//		UserService us = new UserService();
//		result = us.insertComment(comment);
//		return SUCCESS;
//	}

	public String login() {
		UserService us = new UserService();
		result = us.login(user.getUserName(), user.getUserPassword());
		return SUCCESS;
	}
	
	//签到
	public String addSignInScore(){
		UserService us = new UserService();
		result = us.addSignInScore(userId);
		return SUCCESS;
	}
	//发讨论
	public String publishDiscuss(){
		UserService us = new UserService();
		result = us.publishDiscuss(discuss);
		return SUCCESS;
	}
	//发评论
	public String publishComment(){
		UserService us = new UserService();
		result = us.publishComment(comment);
		return SUCCESS;
	}
	//评估自习室占用率
	public String estimateSelfStudyRoomUseRate(){
		UserService us = new UserService();
		result = us.estimateSelfStudyRoomUseRate(userId, roomId, rate);
		return SUCCESS;
	}
	//创建用户
	public String register() {
		UserService us = new UserService();
		result = us.register(user);
		return SUCCESS;
	}

}
