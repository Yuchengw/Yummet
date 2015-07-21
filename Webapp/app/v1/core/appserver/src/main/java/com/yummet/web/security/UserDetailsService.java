package com.yummet.web.security;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.yummet.business.bean.User;
import com.yummet.lib.objectProvider.UserProvider;

/**
 * @author yucheng
 * @since 1
 * */
//@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	private UserProvider userProvider;
	
	public void setUserProvider(UserProvider userProvider) {
		this.userProvider = userProvider;
	}
	
	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

	@Override
	public final User loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		final User user = userProvider.get(userEmail);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		// for debug usage: TODO: add Testing context if neccessary
		user.setAccountNonExpired(true);
		user.setAccountEnabled(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setCredentialsNonExpired(true);
//		detailsChecker.check(user); // TODO: we needs a custom detailsChecker
		return user;
	}
	
	/**
	 * Hide confidational info
	 * */
	private void cleanUser (User user) {}
}
