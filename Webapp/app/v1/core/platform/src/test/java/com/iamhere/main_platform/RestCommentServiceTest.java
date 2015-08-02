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

	public void testUpsertAPostComment() {
//		String url = "http://localhost:8080/api/postcomments/commentUpsert";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		String input = "{ \"commentBody\": \"Test Food Post\","
//				+ "\"createdBy\":{\"id\":\"1\",\"email\": \"ycwmike@gmai.com\" }, \"parentPost\":\"55a5e14ee221fd37a55413cc\"}";
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
	
	public void testGetProvidePostById() {
		String url = "http://localhost:8080/api/postcomments/commentQueryx/55af15b0e221fd03bdfe143c";

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		ClientResponse resp = webResource.type("application/json").get(
				ClientResponse.class);
		if (resp.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ resp.getStatus());
		}
		System.out.println("Output from Server .... \n");
		String output = resp.getEntity(String.class);
		System.out.println(output);
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
		String url = "http://localhost:8080/api/postcomments/commentDelete";

		Client restClient = Client.create();
		WebResource webResource = restClient.resource(url);

		String input = "{\"id\": \"55af15b0e221fd03bdfe143c\"}";
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

}
