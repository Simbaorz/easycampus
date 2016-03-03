package com.thelionking.campus.model;

import java.io.Serializable;
import java.util.List;

public class Discuss implements Serializable{
	//求学霸
	public static final int DISCUSS_TYPE_PA = 0;
	//闲贴
	public static final int DISCUSS_TYPE_HANG = 1;
	//交易帖
	public static final int DISCUSS_DEAL = 2;
	private int discussId;
	private int roomId;
	private int discussType;
	private String discussTitle;
	private String discussContent;
	private String discussUrl;
	private String discussUsername;
	private int discussUserId;
	private long discussTime;
	private List<Comment> comments;

	public int getDiscussId() {
		return discussId;
	}

	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getDiscussType() {
		return discussType;
	}

	public void setDiscussType(int discussType) {
		this.discussType = discussType;
	}

	public String getDiscussTitle() {
		return discussTitle;
	}

	public void setDiscussTitle(String discussTitle) {
		this.discussTitle = discussTitle;
	}

	public String getDiscussContent() {
		return discussContent;
	}

	public void setDiscussContent(String discussContent) {
		this.discussContent = discussContent;
	}

	public String getDiscussUrl() {
		return discussUrl;
	}

	public void setDiscussUrl(String discussUrl) {
		this.discussUrl = discussUrl;
	}

	public String getDiscussUsername() {
		return discussUsername;
	}

	public void setDiscussUsername(String discussUsername) {
		this.discussUsername = discussUsername;
	}

	public int getDiscussUserId() {
		return discussUserId;
	}

	public void setDiscussUserId(int discussUserId) {
		this.discussUserId = discussUserId;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public long getDiscussTime() {
		return discussTime;
	}

	public void setDiscussTime(long discussTime) {
		this.discussTime = discussTime;
	}

	@Override
	public String toString() {
		return "Discuss [discussId=" + discussId + ", roomId=" + roomId
				+ ", discussType=" + discussType + ", discussTitle="
				+ discussTitle + ", discussContent=" + discussContent
				+ ", discussUrl=" + discussUrl + ", discussUsername="
				+ discussUsername + ", discussUserId=" + discussUserId
				+ ", comments=" + comments + "]";
	}
	
	

}
