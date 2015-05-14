package com.amher.web.security;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.amher.service.authentication.UserDetailsImpl;

/**
 * @author yucheng
 * @version 1
 * */
public class UserAuthentication implements Authentication {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7050486829442504148L;
	
	private final UserDetailsImpl userDetailsImpl;
	private boolean authenticated = true;

	public UserAuthentication(UserDetailsImpl user) {
		this.userDetailsImpl = user;
	}

	@Override
	public String getName() {
		return userDetailsImpl.getUsername();
	}

	public UserDetailsImpl getUserDetailsImpl() {
		return this.userDetailsImpl;
	} 
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserDetailsImpl().getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return getUserDetailsImpl().getPassword();
	}

	@Override
	public UserDetailsImpl getDetails() {
		return getUserDetailsImpl();
	}

	@Override
	public Object getPrincipal() {
		return getUserDetailsImpl().getUsername();
	}

	@Override
	public boolean isAuthenticated() {
		return authenticated;
	}

	@Override
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
}

