package com.yummet.api.rest;

import com.yummet.business.bean.User;

/**
 * @author yucheng
 * @since 1
 * */
@SuppressWarnings("rawtypes")
public class AppRestUserClientImpl extends RestClient {

	private static final String REST_USER_PREFIX = "/users";
	
	@SuppressWarnings("unchecked")
	public AppRestUserClientImpl(Class expectedType) {
		super(expectedType);
	}

	public User getUserByEmail(String email) {
		User user = (User) doGet(REST_USER_PREFIX + "/userQueryx/" + email);
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public User createUser(User u) {
		User user = (User) doPost(REST_USER_PREFIX + "/userInsert", u);
		return user;
	}
		
	@SuppressWarnings("unchecked")
	public User updateUser(User u) {
		User user = (User) doPost(REST_USER_PREFIX + "/userUpsert", u);
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public User deleteUser(User deleteUser) {
		User returnUser = (User) doPost(REST_USER_PREFIX + "/userDelete", deleteUser);
		return returnUser;
	}
}
