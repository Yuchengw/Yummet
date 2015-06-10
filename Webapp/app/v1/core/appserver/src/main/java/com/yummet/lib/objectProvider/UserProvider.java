package com.yummet.lib.objectProvider;

import com.yummet.business.bean.User;
import com.yummet.lib.platformService.PlatformUserService;
import com.yummet.lib.platformService.PlatformUserServiceImpl;

/**
 * @author yucheng
 * @since 1
 * */
public class UserProvider {

	private PlatformUserServiceImpl platformUserServiceImpl;
	
	public UserProvider() {
		this.platformUserServiceImpl = new PlatformUserServiceImpl();
	}
	// we should hide this, directly use provider's api to get the data for controller
	public PlatformUserServiceImpl getUserServiceImpl() {
		return (PlatformUserServiceImpl) this.platformUserServiceImpl;
	}
	
	public User add(User user) {
		return this.platformUserServiceImpl.createUser(user);
	}

	public User get(String email, String password) {
		return this.platformUserServiceImpl.getUserByEmailAndPassword(email, password);
	}
	
	public User get(String email) {
		return this.platformUserServiceImpl.getUserByEmail(email);
	}

	public Boolean remove(int id) {
		return false;
	}
	
	/**
	 * update is expensive, think before do it
	 * */
	public void update(User updateUser) {
		this.platformUserServiceImpl.updateUser(updateUser);
	}

	/**
	 * find user by user's name, used for spring security
	 * */
	public User findByUserNameAndPassword(String userEmail, String password) {
		return this.platformUserServiceImpl.getUserByEmailAndPassword(userEmail, password);
	}
}
