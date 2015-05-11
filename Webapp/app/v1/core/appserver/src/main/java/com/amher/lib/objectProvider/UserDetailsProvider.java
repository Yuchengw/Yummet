package com.amher.lib.objectProvider;

import com.amher.service.authentication.UserDetailsImpl;

public interface UserDetailsProvider {
	public UserProvider getUserProvider();
	public void setUserProvider(UserProvider userProvider);
	public UserDetailsImpl findByUserName(String username);
}
