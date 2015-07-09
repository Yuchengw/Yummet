package com.yummet.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.cache.CacheManager;
import com.yummet.enums.DMLEvents;
import com.yummet.platform.adapters.DatabaseProvider;
import com.yummet.platform.adapters.SystemContext;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.platform.func.DmlValidationHandler;
import com.yummet.utilities.TextUtil;

/***
 * 
 * Platform entity for User
 * 
 * @author Jessica
 * @since 1
 *
 */
@Document(collection = "Users")
public class UserObject extends EntityObject {
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
	private DateTime sessionExpire;
	
	// Related Object information which is relationship information in the
	// Relational DB
	private Set<String> createdPosts;
	private Set<String> commentedPosts;
	private boolean relatedInforIsUpdated = false;

	public UserObject() {
	}
	
	public UserObject(String firstName, String lastName, String email,
			String password) {
		// TODO: Assume caller will give no-null-empty values
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setEmail(email);
		this.setPassword(password);
		// Autoset field
		this.setRole("normal");
		this.setAlias(firstName + " " + lastName);
	}

	public UserObject(String id, String email) {
		setId(id);
		setEmail(email);
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
	
	public void setSessionExpire(DateTime sessionExpire) {
		this.sessionExpire = sessionExpire;
	}
	
	public DateTime getSessionExpire() {
		return sessionExpire;
	}

	public void setCreatedPosts(Set<String> posts) {
		createdPosts = posts;
	}
	
	public Set<String> getCreatedPosts() {
		return createdPosts;
	}
	
	public void setCommentedPosts(Set<String> posts) {
		commentedPosts = posts;
	}
	
	public Set<String> getCommentedPosts() {
		return commentedPosts;
	}
	
	 /**
	 * When a user create a post, we add the postId to the User
	 * @param postId
	 */
	 public void addCreatedPosts(String postId) {
	 if (createdPosts == null) {
	 createdPosts = new HashSet<String>();
	 }
	 createdPosts.add(postId);
	 relatedInforIsUpdated = true;
	 }
	
	 /**
	 * When a user remove a post, we remove the postId from the User
	 * @param postId
	 */
	 public void removeCreatedPosts(String postId) {
	 if (createdPosts != null) {
	 createdPosts.remove(postId);
	 relatedInforIsUpdated = true;
	 }
	 }
	
	 /**
	 * When a user comment a post, we add the postId to the User
	 * @param postId
	 */
	 public void addCommentedPosts(String postId) {
	 if (commentedPosts == null) {
	 commentedPosts = new HashSet<String>();
	 }
	 commentedPosts.add(postId);
	 relatedInforIsUpdated = true;
	 }
	
	 /**
	 * When a user comment a post, we remove the postId from the User
	 * @param postId
	 */
	 public void removeCommentedPosts(String postId) {
	 if (commentedPosts != null) {
	 commentedPosts.remove(postId);
	 relatedInforIsUpdated = true;
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
			isExist = dbContext.exists("Users", maps, 
					this);
		} catch( Exception e) {
			isExist = false;
		} 
		// Todo update cache
		return isExist;
	}
	
	/**
	 * Verify if the current user already exists in the db
	 * 
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
		// However if the cache did not find the result, we need double check
		// for the db
		if (!userIsAlreadyUsed) {
			return isEmailUsed();
		}
		return true;
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		// Do the object validations
		if (TextUtil.isNullOrEmpty(getLastName())) {
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
	public UserObject load() throws Exception {
		return (UserObject) super.load();
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		return null;
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		return relatedInforIsUpdated;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
		return null;
	}

	@Override
	public String toString() {
		return "User [email=" + email + ", firstname=" + firstName
				+ ", lastname=" + lastName + ", phone=" + phone + ", password="
				+ password + ", alias=" + alias + ", role=" + role + "]";
	}
	
//	TODO:
//	@Override
//	public void saveHook_Validate(DmlValidationHandler dml) {
//		boolean exists = isEmailUsed();
//		if (exists && dml.getDmlType() == DMLEvents.CREATE) {
//			dml.addError("The email is already used!");
//		}
//	}
	

	
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
}