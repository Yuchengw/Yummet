package com.amher.lib.objectProvider;

import com.amher.business.bean.User;
import com.amher.service.authentication.UserDetailsImpl;


public class UserDetailsProvider {

	private UserProvider userProvider;
	
	public UserDetailsProvider() {
	}
		
	public UserDetailsProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	public UserProvider getUserProvider() {
		return this.userProvider;
	}
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	public UserDetailsImpl findByUserName(String username) {
		User user = getUserProvider().findByUserName(username);
		if (user == null) {
			return null;
		}
		return new UserDetailsImpl(user);
	}
}
