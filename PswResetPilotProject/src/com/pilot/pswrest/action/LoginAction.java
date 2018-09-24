package com.pilot.pswrest.action;

import java.security.spec.InvalidKeySpecException;

import com.opensymphony.xwork2.ActionSupport;
import com.pilot.pswreset.dao.UserDao;
import com.pilot.pswrest.config.PBKDF2;
import com.pilot.pswrest.config.PBKDF2.InvalidHashException;

public class LoginAction extends ActionSupport{
	private String userName;
	private String password;
	private String saltValue;
	private int userId;
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

	public String execute() throws InvalidKeySpecException, InvalidHashException {
		boolean matchPassword = false;
		// check whether record is exist or not with inputed username. 
		if(UserDao.rowCount(getUserName()) != 0){
			LoginAction userRecord = UserDao.userRecord(getUserName());
			matchPassword = PBKDF2.verifyPassword(userRecord.getPassword(), userRecord.getSaltValue(), getPassword().toCharArray());
		}
		
		// if login password is correct.
		if (matchPassword) {
			pageCheck = "loginPage";
			return "success";
		} else {
			addActionError("Invalid Username/Password.");
			return "error";
		}
	}
}
