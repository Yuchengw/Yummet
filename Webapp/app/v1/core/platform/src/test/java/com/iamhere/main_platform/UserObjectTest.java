package com.iamhere.main_platform;

import com.iamhere.cache.CacheManager;
import com.iamhere.entities.UserObject;
import com.iamhere.platform.adapters.SystemContext;
import com.iamhere.platform.func.DmlOperationWrapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Platform test for UserObject
 * 
 * @author Jessica
 * @version 1
 *
 */
public class UserObjectTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public UserObjectTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(UserObjectTest.class);
	}

	/**
	 * Create a new user and save to db
	 * 
	 * @throws Exception
	 */
	public void testCreateNewUser() {
		 UserObject user = new UserObject("George", "admin",
		 "test2@hotmail.com", "test1234");
		 user.setActiveScore(10000);
		 user.setAlias("gadmin");
		 user.setCreditInfo(10000);
		 user.setRole("admin");
		 user.setEmailAuthorized(true);
		 DmlOperationWrapper dmlOperationState = user.save();
		 assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
		 assertNotNull("User should be saved successfully", user.getId());
		 System.out.println("Newly created user id is: " + user.getId());
	}

	public void testRemoveExistingUser() throws Exception {
		 UserObject user = new UserObject( "test2@hotmail.com");
		 user = user.load();
		 assertTrue(user.remove());
		 CacheManager manager = SystemContext.getCacheContext();
		 assertTrue(!manager.exists(user));
	}
	
	public void testLoadExistingUser() throws Exception {
		UserObject user = new UserObject("test@hotmail.com");
		user = user.load();
		assertNotNull(user.getId());
	}

	public void testUpdateExistingUser() throws Exception {
		UserObject user = new UserObject("test@hotmail.com");
		user = user.load();
		user.setActiveScore(90000);
		DmlOperationWrapper dmlOperationState = user.save();
		assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
		assertEquals("User should be saved successfully", "552c8f59e221fd568dbf3d58", user.getId());
	}

	public void testIfUserEmailExist() throws Exception {
		UserObject user = new UserObject("George", "admin", "test@hotmail.com",
				"test1234");
		assertTrue(user.isAlreadyExist());
	}
}