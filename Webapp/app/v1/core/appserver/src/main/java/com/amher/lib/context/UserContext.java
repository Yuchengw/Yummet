package com.amher.lib.context;

import com.amher.lib.request.*;

/**
 * UserContext
 * 
 * A thread local that contains information about the context user
 * 
 * @author AmherLib
 * @since 1
 * */


public interface UserContext extends Context{
	/**
	 * Get a UserContext for the calling thread
	 * 
	 * @return never will be null; but maybe an error
	 * */
	 UserInfo getUserInfo();
	 
	 String getUserId();
	 
	 boolean isAccessibleMode();
	 
	 String getRealuserId();
	 
	 public interface Provider<T extends UserContext> extends StackableContextProvider<T> {
		 /**
		  * Establish the context as the given user
		  * @param userId
		  * @param mruDisabled
		  * */
		 void establish(String userId);
		 void establish(String userId, boolean mruDisabled);
		 void establish(boolean checkHttps, String userId);
		 void establish(String userId, boolean mruDisabled, boolean checkHttps);
	 }
}
