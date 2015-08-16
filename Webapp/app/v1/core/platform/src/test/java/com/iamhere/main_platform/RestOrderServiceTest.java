package com.iamhere.main_platform;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class RestOrderServiceTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public RestOrderServiceTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(RestOrderServiceTest.class);
	}

	public void testUpsertAOrder() {
//		String url = "http://localhost:8080/api/orders/orderUpsert";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		String input = "{ \"commentBody\": \"Test Food Post\","
//				+ "\"seller\":{\"id\":\"1\",\"email\": \"ycwmike@gmai.com\" }, "
//				+  "\"buyer\":{\"id\":\"1\",\"email\": \"ycwmike@gmai.com\" }, "
//				+ "\"parentPost\":\"55a5e14ee221fd37a55413cc\", "
//				+ "\"parentPostType\":\"P\", "
//				+ "\"actualCost\":\"4.33\", \"quantity\": \"10\", \"transactionDateTime\" : \"2015-07-24T21:20:47.668+0000\", \"status\":\"close\"}";
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
	
	public void testGetOrderById() {
		String url = "http://localhost:8080/api/orders/orderQueryx/55d00db8e221fd0cbbbe26da";

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
	
	public void testRemoveAOrder() {
//		String url = "http://localhost:8080/api/orders/orderDelete";
//
//		Client restClient = Client.create();
//		WebResource webResource = restClient.resource(url);
//
//		String input = "{\"id\": \"55cfdc51e221fd5033ab2ba0\"}";
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
