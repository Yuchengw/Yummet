package com.iamhere.main_platform;

import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlOperationWrapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Platform test for UserObject
 * 
 * @author jassica
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
//		 UserObject user = new UserObject("George", "admin",
//		 "test@hotmail.com", "test1234");
//		 user.setActiveScore(10000);
//		 user.setAlias("gadmin");
//		 user.setCreditInfo(10000);
//		 user.setRole("admin");
//		 user.setEmailAuthorized(true);
//		 DmlOperationWrapper dmlOperationState = user.save();
//		 assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
//		 assertNotNull("User should be saved successfully", user.getId());
//		 System.out.println("Newly created user id is: " + user.getId());
	}

	public void testRemoveExistingUser() {
//		 UserObject user = new UserObject( "test@hotmail.com");
//		 assertTrue(user.remove());
	}

	public void testUpdateExistingUser() throws Exception {
		UserObject user = new UserObject("test@hotmail.com");
		user = user.load();
		if (user != null) {
			user.setActiveScore(80000);
			DmlOperationWrapper dmlOperationState = user.save();
			assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
			assertEquals("User should be saved successfully",
					"test@hotmail.com", user.getId());
		}
	}

	public void testIfUserEmailExist() throws Exception {
//		UserObject user = new UserObject("George", "admin", "test@hotmail.com",
//				"test1234");
//		assertTrue(user.isAlreadyExist());
	}
}
