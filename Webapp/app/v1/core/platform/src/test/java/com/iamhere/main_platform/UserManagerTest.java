package com.iamhere.main_platform;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.yummet.entities.UserObject;
import com.yummet.platform.func.DmlOperationWrapper;

public class UserManagerTest extends TestCase
{
/**
 * Create the test case
 *
 * @param testName name of the test case
 */
public UserManagerTest( String testName )
{
    super( testName );
}

/**
 * @return the suite of tests being tested
 */
public static Test suite()
{
    return new TestSuite( PostObjectTest.class );
}

/**
 * Create a new user and save to db
 * @throws Exception 
 */
public void testCreateNewUser()
{
	UserObject user = new UserObject("Jassica", "admin", "jassica_jiafei@163.com", "test1234");
	user.setActiveScore(10000);
	user.setAlias("jadmin");
	user.setCreditInfo(10000);
	user.setRole("admin");
	DmlOperationWrapper dmlOperationState = user.save();
	assertTrue("Save should succeed", dmlOperationState.isBulkSuccess());
	assertNotNull("User should be saved successfully", user.getId());
	System.out.println("Newly created user id is: " + user.getId());
}

//	public static void main(String[] args) {
//		// UserObject user1 = new UserObject("User1", "LastName",
//		// "test@test.com",
//		// "123456");
//		// // UserManager um = new UserManager(new MongoDbProvider());
//		// try {
//		// user1.save();
//		// System.out.println("Save is done");
//		// user1.setEmail("testUpdate@test.com");
//		// user1.save();
//		// // um.createNewUsers(new UserObject[] { user1 });
//		// // UserObject[] allUsers = um.getAllUsers();
//		// // for (UserObject user: allUsers) {
//		// // System.out.println(user.getEmail());
//		// // }
//		// // um.removeUsers(new UserObject[] { user1 });
//		// } catch (Exception e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//
//		UserObject user = new UserObject("54f28f36e221fd10f166f952");
//		try {
//			user = user.load();
//			user.setActiveScore(100);
//			user.save();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}