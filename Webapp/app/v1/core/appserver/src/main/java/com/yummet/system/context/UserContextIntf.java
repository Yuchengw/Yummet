package com.yummet.system.context;

import com.yummet.business.bean.User;

/**
 * @author yucheng
 * @since 1
 * */
public interface UserContextIntf extends ContextIntf{
	
	String getUserId();
	
	String getUserId(boolean nullOk);
	
	/**
	 * This method should not be used in general
	 * */
	String getUserId(boolean nullOk, boolean notAuthenticatedOk);
	
	User getUser();
	
	User getUser(boolean nullOk);

	/**
	 * This method should not be used in general.
	 * */
	User getUser(boolean nullOk, boolean notAuthenticatedOk);
	
}
