package com.iamhere.main_platform;

import com.yummet.entities.PostComment;
import com.yummet.entities.PostObject;
import com.yummet.entities.UserObject;
import com.yummet.enums.PostStatus;
import com.yummet.platform.func.DmlOperationWrapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PostObjectTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public PostObjectTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(PostObjectTest.class);
	}

	/**
	 * Create a new post and save to db?
	 */
	public void testCreateNewPost() {
//		UserObject user = new UserObject("test@hotmail.com");
//		PostObject post = new PostObject(user, "Sample Post", "Around SSF", 1);
//		post.setCost(2.0); // it should be required and we need verify
//		post.setStatus(PostStatus.OPEN); // it should be a required field too
//		post.setNumberOfOrders(0); // should not allow to set the value
//		post.setLastModifiedBy(user);
//		post.setType(PostObject.REQUEST);
//		DmlOperationWrapper dmlState = post.save();
//		assertTrue(dmlState.isBulkSuccess());
//		assertNotNull(
//				"The post should be created succesfully and assign a objectId",
//				post.getId());
//		System.out.println("The post id is: " + post.getId());
//		//
//		// DateTime current = DateTime.now();
//		// RecurEventInfo recur = new RecurEventInfo(current);
//		// recur.setRecur(true); // when recur is true the interval information
//		// has to be set
//		// recur.setRecurOffset(1);
//		// recur.setRecurUnit(TimeUnit.DAYS);
//		//
//		// post.setPeriod(recur);
	}

	public void testUpdateExistingPost() throws Exception {
//		UserObject user = new UserObject("test@hotmail.com");
//		PostObject post = new PostObject("552c9595e221fd5c2173c682");
//		post = post.load();
//		post.setCost(2.0); // it should be required and we need verify
//		post.setStatus(PostStatus.CLOSE); // it should be a required field too
//		post.setLastModifiedBy(user);
//		DmlOperationWrapper dmlState = post.save();
//		assertTrue(dmlState.isBulkSuccess());
//		assertEquals("The post id should not be changed",
//				"552c9595e221fd5c2173c682", post.getId());
	}
	
	public void testCreateNewPostComment() {
//		UserObject user = new UserObject("test@hotmail.com");
//		PostObject post = new PostObject("552c9595e221fd5c2173c682");
//		PostComment comment = new PostComment(post, "I add comment hahshsha");
//		comment.setCreatedBy(user);
//		DmlOperationWrapper state = comment.save();
//		assertTrue(state.isBulkSuccess());
//		assertNotNull("The comment id should not be null", comment.getId());
//		System.out.println("The comment id is : "  + comment.getId());
	}
	
	public void testUpdateExistingPostComment() throws Exception {
//		PostComment comment = new PostComment("552c971be221fd5da1bd6968");
//		comment = comment.load();
//		comment.setCommentBody("I want to update my comment");
//		DmlOperationWrapper state = comment.save();
//		assertTrue(state.isBulkSuccess());
	}
	
	public void testRemoveExistingPostComment() throws Exception {
//		PostComment comment = new PostComment("552c971be221fd5da1bd6968");
//		boolean removed = comment.remove();
//		assertTrue("removed should successful", removed);
	}
}