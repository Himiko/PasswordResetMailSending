package com.pilot.pswrest.action;

import com.pilot.pswreset.dao.UserDao;
import com.pilot.pswrest.config.PBKDF2;

public class RegistrationAction {
	private String userName;
	private String password;
	private String saltValue;
	private int userId;
	private String emailAddress;
	private String pageCheck = "";
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSaltValue() {
		return saltValue;
	}

	public void setSaltValue(String saltValue) {
		this.saltValue = saltValue;
	}
	
	public String getPageCheck() {
		return pageCheck;
	}

	public void setPageCheck(String pageCheck) {
		this.pageCheck = pageCheck;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	private PBKDF2 pbkdf2;

	public PBKDF2 getPBKDF2() {
		return pbkdf2;
	}

	public void setPBKDF2(PBKDF2 value) {
		pbkdf2 = value;
	}

	public String execute(){
		String hashPassowrd = PBKDF2.hashPassword(getPassword()).toString();
		setPassword(hashPassowrd);
		setSaltValue(PBKDF2.getRandomSaltValue());
		
		int i=UserDao.register(this);
		if(i>0){
			// to check whether login or register page
			setPageCheck("registerPage");
			return "success";
		}
		return "error";
	}
}
