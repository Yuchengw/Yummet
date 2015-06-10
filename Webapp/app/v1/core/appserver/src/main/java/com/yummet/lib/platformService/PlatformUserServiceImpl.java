package com.yummet.lib.platformService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yummet.bridge.PlatformServiceProvider;
import com.yummet.bridge.PlatformUserServiceProviderImpl;
import com.yummet.entities.UserObject;
import com.yummet.business.bean.User;
import com.yummet.util.monitoring.UserControllerPerformanceMonitorInterceptor;

/**
 * @author yucheng
 * @since 1
 * */
public class PlatformUserServiceImpl extends PlatformUserService {
	PlatformServiceProvider platformServiceProvider;
	private static final Logger logger = LoggerFactory.getLogger(PlatformUserServiceImpl.class);	
	
	public PlatformUserServiceImpl() {
		platformServiceProvider = new PlatformUserServiceProviderImpl(); // we should change it to API in the future, for 																	   // now, I accept the jar import file 
	}
	
	/**
	 * this function is used for get user info from platform without password authentication,
	 * use this with carefulness.
	 * @param userEmail
	 * @return User 
	 * */
	public User getUserByEmail(String userEmail) {
		UserObject platformUserObject = null;
		User user = null;
		try {
			platformUserObject = (UserObject) ((PlatformUserServiceProviderImpl) platformServiceProvider).getObject(userEmail);
			if (platformUserObject != null) {
				user = new User();
				copyUser(platformUserObject, user);
			} else {
				
			}
		} catch (Exception e) {
			logger.debug("Error happens when retriving User object" + e.getStackTrace());
		}
		return user;
	}
	
	/**
	 * This function is used for get appserver user object from Platform by email and password
	 * */
	public User getUserByEmailAndPassword(String userEmail, String userPassword) {
		UserObject platformUserObject = null;
		User user = null;
		try {
			platformUserObject = (UserObject) ((PlatformUserServiceProviderImpl) platformServiceProvider).getObject(userEmail, userPassword);
			if (platformUserObject != null) {
				user = new User();
				copyUser(platformUserObject, user);
			} else {
				
			}
		} catch (Exception e) {
			logger.debug("Error happens when retriving User object" + e.getStackTrace());
		}
		return user;
	}
	/**
	 * This function is used for create user in mongodb
	 * @throws Exception 
	 * */
	public User createUser(User user) {
		UserObject newUserObject = null;
		try {
			newUserObject = new UserObject(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
			// 
			((PlatformUserServiceProviderImpl) platformServiceProvider).insertObject(newUserObject);
		} catch (Exception e) {
			logger.debug("there is something wrong when inserting user object" + e.getStackTrace());
		}
		user.setPassword(newUserObject.getPassword());
		return user;
	}
	
	/**
	 * This function is used for update user in mongodb
	 * @throws Exception 
	 * */
	public User updateUser(User user) {
		try {
		//  TODO: should define the new constructor, prefer another copy helper function
		UserObject updateUserObject = new UserObject(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword());
		((PlatformUserServiceProviderImpl) platformServiceProvider).updateObject(updateUserObject);
		} catch (Exception e) {
			logger.debug("there is something wrong when inserting user object" + e.getStackTrace());
		}
		return user;
	}
	/**
	 * This is function is used for copy platformUser to a appserver user
	 * Copy attribute one by one is ok
	 * @param UserObject platform User object
	 * @param User appserver User object
	 * @throws Exception // should be more specific in the future
	 * */
	private User copyUser(UserObject platformUser, User appUser) throws Exception{
		if (platformUser == null || appUser == null) {
			throw new Exception("Can't copy"); // should be more specific
		}
		appUser.setEmail(platformUser.getEmail());
		appUser.setFirstName(platformUser.getFirstName());
		appUser.setLastName(platformUser.getLastName());
		appUser.setCreatedDate(platformUser.getCreatedDate());
		appUser.setLastModifiedDate(platformUser.getLastModifiedDate());
		appUser.setAlias(platformUser.getAlias());
		appUser.setId(platformUser.getId());
		appUser.setCreditInfo(platformUser.getCreditInfo());
		appUser.setActiveScore(platformUser.getActiveScore());
		appUser.setEmailAuthorized(true);
		appUser.setPassword(platformUser.getPassword());
		appUser.setPhone(platformUser.getPhone());
		appUser.setRole(platformUser.getRole());
		return appUser;
	}


	@Override
	public PlatformServiceProvider getPlatformServiceProvider() {
		return platformServiceProvider;
	}

}
