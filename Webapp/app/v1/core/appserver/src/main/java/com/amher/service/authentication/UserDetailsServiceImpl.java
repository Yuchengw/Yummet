package com.amher.service.authentication;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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
public class UserDetailsServiceImpl implements UserDetailsService, InitializingBean, DisposableBean {

	@Autowired
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

	@Override
	public void destroy() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
