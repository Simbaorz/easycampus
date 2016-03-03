package com.thelionking.campus.model;

public class Comment {
	private int commentId;
	private int discussId;
	private String commentUrl;
	private String commentContent;
	private String commentUsername;
	private int commentUserId;

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getDiscussId() {
		return discussId;
	}

	public void setDiscussId(int discussId) {
		this.discussId = discussId;
	}

	public String getCommentUrl() {
		return commentUrl;
	}

	public void setCommentUrl(String commentUrl) {
		this.commentUrl = commentUrl;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentUsername() {
		return commentUsername;
	}

	public void setCommentUsername(String commentUsername) {
		this.commentUsername = commentUsername;
	}

	public int getCommentUserId() {
		return commentUserId;
	}

	public void setCommentUserId(int commentUserId) {
		this.commentUserId = commentUserId;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", discussId=" + discussId
				+ ", commentUrl=" + commentUrl + ", commentContent="
				+ commentContent + ", commentUsername=" + commentUsername
				+ ", commentUserId=" + commentUserId + "]";
	}
	
	

}
