package com.amher.business.bean;


import org.springframework.security.core.GrantedAuthority;

import com.amher.service.authentication.UserDetailsImpl;

/**
 * @author yucheng
 * @version 1
 * */
public class UserAuthority implements GrantedAuthority{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7972684886271571320L;

	private UserDetailsImpl userDetailsImpl;

	private String authority;

	public UserDetailsImpl getUserDetailsImpl() {
		return userDetailsImpl;
	}

	public void setUserDetailsImpl(UserDetailsImpl userDetailsImpl) {
		this.userDetailsImpl = userDetailsImpl;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof UserAuthority))
			return false;

		UserAuthority ua = (UserAuthority) obj;
		return ua.getAuthority() == this.getAuthority() || ua.getAuthority().equals(this.getAuthority());
	}

	@Override
	public int hashCode() {
		return getAuthority() == null ? 0 : getAuthority().hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getAuthority();
	}

}
