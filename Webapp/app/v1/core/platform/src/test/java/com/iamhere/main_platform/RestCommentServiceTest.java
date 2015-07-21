package com.iamhere.main_platform;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestCommentServiceTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public RestCommentServiceTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(RestCommentServiceTest.class);
	}

	public void testUpsertAProvidePost() {
		String url = "http://localhost:8080/api/posts/providePostUpsert";

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		String input = "{ \"subject\": \"Test Food Post\","
				+ "\"creator\":{\"id\":\"1\",\"email\": \"ycwmike@gmai.com\" }, \"lastModifiedBy\":{\"id\":\"1\",\"email\": \"ycwmike@gmai.com\" },  "
				+ "\"location\": \"San Francisco\",\"quantity\": \"7\",\"commentsOrDescription\": \"Test Food\", "
				+ "\"cost\": \"10.09\", \"postCategory\": \"Food\",\"status\": \"open\",\"visibility\": \"public\", "
				+ "\"expireDate\": \"2012-12-24T21:20:47.668+0000\"}";
		ClientResponse resp = webResource.type("application/json").post(
				ClientResponse.class, input);
		if (resp.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ resp.getStatus());
		}
		System.out.println("Output from Server .... \n");
		String output = resp.getEntity(String.class);
		System.out.println(output);
	}
	
	public void testGetProvidePostById() {
//		String url = "http://localhost:8080/api/posts/providePostQueryx/559cab35e221fd52ee2670fd";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		ClientResponse resp = webResource.type("application/json").get(
//				ClientResponse.class);
//		if (resp.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ resp.getStatus());
//		}
//		System.out.println("Output from Server .... \n");
//		String output = resp.getEntity(String.class);
//		System.out.println(output);
	}
	
	public void testGetProvidePostsByUsers() {
//		String url = "http://localhost:8080/api/posts/providePostLimitQuery/";
//		url += "ycwmike@gmai.com/0/3";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		ClientResponse resp = webResource.type("application/json").get(
//				ClientResponse.class);
//		if (resp.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ resp.getStatus());
//		}
//		System.out.println("Output from Server .... \n");
//		String output = resp.getEntity(String.class);
//		System.out.println(output);
	}

	public void testRemoveAProvidePost() {
//		String url = "http://localhost:8080/api/posts/providePostDelete";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		String input = "{\"id\": \"559ca4ece221fd52ee8f36da\"}";
//		ClientResponse resp = webResource.type("application/json").post(
//				ClientResponse.class, input);
//		if (resp.getStatus() != 200) {
//			throw new RuntimeException("Failed : HTTP error code : "
//					+ resp.getStatus());
//		}
//		System.out.println("Output from Server .... \n");
//		String output = resp.getEntity(String.class);
//		System.out.println(output);
	}

}
