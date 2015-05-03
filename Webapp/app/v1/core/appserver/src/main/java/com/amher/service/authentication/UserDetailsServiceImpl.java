package com.amher.service.authentication;

import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.amher.lib.objectProvider.UserDetailsProvider;

/**
 * @author yucheng
 * @version 1
 * */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserDetailsProvider userDetailsProvider;

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	
	/**this class is used by spring controller to authenticate and authorize
	 *user
	 */
	@Override
	public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserDetailsImpl userDetailsImpl = userDetailsProvider.findByUserName(username);
		if (userDetailsImpl == null) {
			throw new UsernameNotFoundException("user not found");
		}
		detailsChecker.check(userDetailsImpl);
		return userDetailsImpl;
	}
}
