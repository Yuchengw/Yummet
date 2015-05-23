package com.yummet.bridge;

import com.yummet.entities.EntityObject;
import com.yummet.entities.UserObject;
import com.yummet.platform.func.DmlOperationWrapper;

/**
 * @author yucheng
 * @since 1
 * */
public class PlatformUserServiceProviderImpl implements PlatformServiceProvider{
	
	public PlatformUserServiceProviderImpl() {
	}
	
	/**
	 * This function is used for communicating with app server 
	 * to pass the user info from DB to appserver
	 * @param String userEmail
	 * @return Platform User object
	 * @throws Exception 
	 * */
	public EntityObject getObject(String userEmail) throws Exception {
		if (userEmail == null) {
		}
		return new UserObject(userEmail).load();
	}
	
	/**
	 * 
	 * */
	public EntityObject updateObject(UserObject userInfo) {
		DmlOperationWrapper dmlOperationState = userInfo.save();
		if (!dmlOperationState.isBulkSuccess()) {
		}
		return (EntityObject) dmlOperationState.getEntityObjectsWithoutError();
	}
	
	/**
	 * 
	 * */
	public EntityObject insertObject(UserObject userInfo) throws Exception {
		DmlOperationWrapper dmlOperationState = userInfo.save();
		if (!dmlOperationState.isBulkSuccess()) {
			throw new Exception("Saving user " + userInfo.getEmail() + " failed");
		}
		return userInfo;
	}
	
	/**
	 * 
	 * */
	public Boolean deleteObject(UserObject userInfo) {
		return userInfo.remove();
	}
	
}
