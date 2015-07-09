package com.iamhere.main_platform;

//import com.iamhere.cache.CacheManager;
//import com.iamhere.entities.UserObject;
//import com.iamhere.platform.adapters.SystemContext;
//import com.iamhere.platform.func.DmlOperationWrapper;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Platform rest test for UserObject
 * 
 * @author Jessica
 * @version 1
 *
 */
public class RestUserServiceTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public RestUserServiceTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(RestUserServiceTest.class);
	}
	
	public void testUpsertAUser() {
			String url = "http://localhost:8080/api/users/userUpsert";

			Client restClient = Client.create();
			WebResource webResource = restClient.resource(url);

			String input = "{\"id\": \"1\", \"firstName\": \"Tom\",\"lastName\": \"Cruise\",\"email\": \"ycwmike@gmai.com\", \"photo\": \"Object or null (paramter decide)\", \"phone\": \"1234567\",\"creditInfo\": \"1231231\",\"activeScore\": \"123112321\",\"alias\": \"alias\",\"role\": \"admin\", \"sessionExpire\": \"2012-12-24T21:20:47.668+0000\",\"password\": \"afadsfdafa(hash value)\",\"newpassword\": \"adfafdafadfdf\"}";
			ClientResponse resp = webResource.type("application/json").post(
					ClientResponse.class, input);
			if (resp.getStatus() != 201) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ resp.getStatus());
			}
			System.out.println("Output from Server .... \n");
			String output = resp.getEntity(String.class);
			System.out.println(output);
	}
	
	public void testRemoveAUser() {
//		String url = "http://localhost:8080/api/users/userDelete";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		String input = "{\"id\": \"1\", \"email\": \"ycwmike@gmai.com\"}";
//		ClientResponse resp = webResource.type("application/json").post(
//				ClientResponse.class, input);
//		if (resp.getStatus() != 201) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ resp.getStatus());
//		}
//		System.out.println("Output from Server .... \n");
//		String output = resp.getEntity(String.class);
//		System.out.println(output);
}
}