package com.yummet.business.bean;


/**
 * Login bean for login control
 * 
 * @author yucheng
 * @version 1
 * */
public class Login {
	private String userName;
	private String password;
	
	public Login(){}

	
	public String getPassword() {
		return password;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


}
