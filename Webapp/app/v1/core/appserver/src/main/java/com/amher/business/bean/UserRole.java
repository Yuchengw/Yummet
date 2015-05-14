package com.amher.business.bean;

import com.amher.service.authentication.UserDetailsImpl;

/***
 * @author yucheng
 * @version 1
 * */
public enum UserRole {
	ANONYMOUS, USER, ADMIN;
	
	public UserAuthority asAuthorityFor(final UserDetailsImpl userDetailsImpl) {
		final UserAuthority authority = new UserAuthority();
		authority.setAuthority("ROLE_" + toString());
		authority.setUserDetailsImpl(userDetailsImpl);
		return authority;
	}

	public static UserRole valueOf(final UserAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_ANONYMOUS":
			return ANONYMOUS;
		case "ROLE_USER":
			return USER;
		case "ROLE_ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: "
				+ authority.getAuthority());
	}
}
