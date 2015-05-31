package com.yummet.business.bean;

/**
 * @author yucheng
 * @version 1
 * */
public class User extends BeanObject {
	
    private static final long serialVersionUID = -7788619177798333712L;
    
    private String firstName;
	private String lastName;
	private String phone;
	private double creditInfo; 
	private double activeScore; 
								
	private String password;
	private String alias;
	private String role;
	private String id;
	private boolean isEmailAuthorized;
	private String email;
	
	public User() {}

	public User(String id, String firstname, String lastname, String email, String password) {
		// Be careful of lower and upper case 
		super.setId(id);
		this.firstName = firstname;
		this.lastName = lastname;
		this.email = email;
		this.password = password;
	}
	
	// Getter and Setter
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
		super.setId(id);
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public double getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(double creditInfo) {
		this.creditInfo = creditInfo;
	}

	public double getActiveScore() {
		return activeScore;
	}

	public void setActiveScore(double activeScore) {
		this.activeScore = activeScore;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEmailAuthorized() {
		return isEmailAuthorized;
	}

	public void setEmailAuthorized(boolean isEmailAuthorized) {
		this.isEmailAuthorized = isEmailAuthorized;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
