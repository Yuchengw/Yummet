package com.yummet.bridge;

import com.yummet.entities.EntityObject;
import com.yummet.entities.UserObject;
import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.util.security.EncryptionUtil;

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
			//TODO: we need to implement our own exception chain
		}
		return new UserObject(userEmail).load();
	}
	
	/**
	 * This is function is used for communicating with app server
	 * put the password check logic here since we want to platform do more security stuff, make 
	 * appserver stateless
	 * 
	 * @param String userEmail
	 * @param String userpassword
	 * @return Platform User Object if the password is correct
	 * @throws Exception
	 * */
	public EntityObject getObject(String userEmail, String userPassword) throws Exception {
		UserObject user = (UserObject) getObject(userEmail);
		String hashedPassword = hashPassword(userPassword);
		if (hashedPassword != null && hashedPassword.length() != 0 && hashedPassword.equals(user.getPassword())) {
			return user;
		} else {
			return null;
		}
	}
	
	/**
	 * @throws Exception 
	 * 
	 * */
	public EntityObject updateObject(UserObject userInfo) throws Exception {
		userInfo.setPassword(hashPassword(userInfo.getPassword())); // encrypted password, 
		DmlOperationWrapper dmlOperationState = userInfo.save();
		if (!dmlOperationState.isBulkSuccess()) {
		}
		return (EntityObject) dmlOperationState.getEntityObjectsWithoutError();
	}
	
	/**
	 * 
	 * */
	public EntityObject insertObject(UserObject userInfo) throws Exception {
		userInfo.setPassword(hashPassword(userInfo.getPassword())); // encrypted password, 
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
	
	/**
	 * Helper function for encryption password
	 * @param password
	 * @return hashedPassword
	 * @throws Exception 
	 * */
	public String hashPassword(String password) throws Exception {
		String encryptedPassword = null;
		if (password != null || password.length() != 0) {
			encryptedPassword = EncryptionUtil.byteArrayToHexString(EncryptionUtil.computeHash(password));
		} 
		return encryptedPassword;
	}
}
