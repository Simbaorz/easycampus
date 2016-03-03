package com.thelionking.campus.model;

/**
 * @author the lion king
 */
public class User {
	
    private int userId;
    
    private String userName;
    
    private int userScore;
    
    private int userGrade;
    
    private String userImg;
    
    private String userSign;
    
    private String userNickname;
    
    private String userPassword;
    
    public User(){
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getUserScore() {
		return userScore;
	}

	public void setUserScore(int userScore) {
		this.userScore = userScore;
	}

	public int getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(int userGrade) {
		this.userGrade = userGrade;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserSign() {
		return userSign;
	}

	public void setUserSign(String userSign) {
		this.userSign = userSign;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName
				+ ", userScore=" + userScore + ", userGrade=" + userGrade
				+ ", userImg=" + userImg + ", userSign=" + userSign
				+ ", userNickname=" + userNickname + ", userPassword="
				+ userPassword + "]";
	}

}
