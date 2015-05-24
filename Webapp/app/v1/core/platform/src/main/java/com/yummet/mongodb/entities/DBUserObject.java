package com.yummet.mongodb.entities;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.entities.UserObject;
import com.yummet.enums.DMLEvents;
import com.yummet.platform.adapters.DatabaseProvider;
import com.yummet.platform.adapters.SystemContext;
import com.yummet.platform.func.DmlValidationHandler;

/**
 * MongoDB representation of User object
 * 
 * @author jassica
 *
 */
@Document(collection = "Users")
public class DBUserObject extends DBEntityObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5761815599608865412L;
	
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private double creditInfo; // the number of likes fro all the posts
	private double activeScore; // the sum of provide posts, request posts and
								// post comments
	private String password;
	private String alias;
	private String role;
	private boolean isEmailAuthorized;

	public DBUserObject() {
	}

	// Getters and setters
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getCreditInfo() {
		return creditInfo;
	}

	public void setCreditInfo(double creditInfo) {
		this.creditInfo = creditInfo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		boolean exists = isEmailUsed();
		if (exists && dml.getDmlType() == DMLEvents.CREATE) {
			dml.addError("The email is already used!");
		}
	}

	/**
	 * Check if the same email address has been used by others
	 * 
	 * @return
	 */
	public boolean isEmailUsed() {
		// verify if the email address is already used
		DatabaseProvider dbContext = SystemContext.getContext();
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("Email", getEmail());
		boolean isExist = false;
		try {
			isExist = dbContext.exists(getDbTableName(), maps, new UserObject(
					this));
		} catch( Exception e) {
			isExist = false;
		} 
		return isExist;
	}

	@Override
	public Map<String, Object> getFieldsAndValues() {
		Map<String, Object> values = new HashMap<String, Object>();;
		values.put("email", email);
		return values;
	}

	@Override
	public String getDbTableName() {
		return "Users";
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstname=" + firstName
				+ ", lastname=" + lastName + ", phone=" + phone + ", password="
				+ password + ", alias=" + alias + ", role=" + role + "]";
	}

}
