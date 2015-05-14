package com.amher.lib.objectProvider;

import com.amher.business.bean.User;
import com.amher.service.authentication.UserDetailsImpl;

/**
 * This is for User object authentication
 * */
public class UserDetailsProviderImpl implements UserDetailsProvider{

	private UserProvider userProvider;
	
	public UserDetailsProviderImpl() {
		this.userProvider = new UserProvider();
	}
		
	public UserDetailsProviderImpl(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	public UserProvider getUserProvider() {
		return this.userProvider;
	}
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	public UserDetailsImpl findByUserName(String username) {
		User user = this.userProvider.findByUserName(username);
		if (user == null) {
			return null;
		}
		return new UserDetailsImpl(user);
	}
}
