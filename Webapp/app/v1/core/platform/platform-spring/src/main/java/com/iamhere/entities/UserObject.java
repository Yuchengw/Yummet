package com.iamhere.entities;

import com.iamhere.cache.CacheManager;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBUserObject;
import com.iamhere.platform.adapters.SystemContext;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/***
 * 
 * Plastform entity for User
 * @author jassica
 *
 */
public class UserObject  extends EntityObject {
	private static final long serialVersionUID = -6011241820070393953L;  
	// private fields
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

	
	public UserObject(String firstName, String lastName, String email,
			String password) {
		// TODO: Assume caller will give no-null-empty values
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		this.setRole("normal");
	}
	
	public UserObject(DBEntityObject dbUser) {
		reloadAllFieldInformationFromDbObject(dbUser);
	}

	public UserObject(String email) {
		setEmail(email);
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		// override cache key to be the email address 
		setCacheKey(email);
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

	/**
	 * Verify if the current user already exists in the db
	 * @return
	 */
	public boolean isAlreadyExist() {
		// Check if the user email is already used under cache
		CacheManager cache = SystemContext.getCacheContext();
		boolean userIsAlreadyUsed = false;
		try {
			userIsAlreadyUsed = cache.exists(this);
		} catch (Exception e) {
		}
		// If the cache match the result, the user should be exist already
		// However if the cache did not find the result, we need double check for the db
		if (!userIsAlreadyUsed) {
			DBUserObject dbUser;
			dbUser = new DBUserObject();
			dbUser.setEmail(email);
			return dbUser.isEmailUsed();
		} 
		return true;
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		// Do the object validations
		if ( TextUtil.isNullOrEmpty(getLastName())) {
			dml.addError("The last name cannot be empty!");
		}
		if (TextUtil.isNullOrEmpty(getEmail())) {
			dml.addError("Email cannot be empty!");
		}
		if (TextUtil.isNullOrEmpty(getPassword())) {
			dml.addError("Password cannot be empty!");
		}
		if (TextUtil.isNullOrEmpty(getAlias())) {
			dml.addError("Alias cannot be empty!");
		}
		
		// The default role information is normal user
		if (TextUtil.isNullOrEmpty(getRole())) {
			setRole("normal");
		}
		super.saveHook_Validate(dml);
	}

	@Override
	public DBEntityObject getDbObject() {
		DBUserObject dbUser = new DBUserObject();
		dbUser.setEmail(email);
		dbUser.setFirstName(firstName);
		dbUser.setLastName(lastName);
		dbUser.setCreditInfo(creditInfo);
		dbUser.setPhone(phone);
		dbUser.setActiveScore(activeScore);
		dbUser.setPassword(password);
		dbUser.setAlias(alias);
		dbUser.setRole(role);
		dbUser.setCreatedDate(getCreatedDate());
		dbUser.setLastModifiedDate(getLastModifiedDate());
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbUser.setId(getId());
		}
		return dbUser;
	}
	
	@Override
	public UserObject load() throws Exception {
		return (UserObject) super.load();
	}
	
	@Override
	public void reloadAllFieldInformationFromDbObject(DBEntityObject dbObject) {
		DBUserObject dbUser = (DBUserObject) dbObject;
		setActiveScore(dbUser.getActiveScore());
		setAlias(dbUser.getAlias());
		setCreatedDate(dbUser.getCreatedDate());
		setCreditInfo(dbUser.getCreditInfo());
		setEmail(dbUser.getEmail());
		setFirstName(getFirstName());
		setId(dbUser.getId());
		setLastModifiedDate(dbUser.getLastModifiedDate());
		setLastName(dbUser.getLastName());
		setPassword(dbUser.getPassword());
		setPhone(dbUser.getPhone());
		setRole(dbUser.getRole());
	}

	@Override
	public Class<?> getDbClass() {
		return DBUserObject.class;
	}

	@Override
	public String getDbTableName() {
		return new DBUserObject().getDbTableName();
	}

	
}
