package com.yummet.api.rest;

import java.util.ArrayList;
import java.util.List;

import com.yummet.bridge.PlatformPostServiceProviderImpl;
import com.yummet.bridge.PlatformServiceProvider;
import com.yummet.bridge.PlatformUserServiceProviderImpl;
import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;
import com.yummet.entities.EntityObject;
import com.yummet.entities.PostObject;
import com.yummet.entities.UserObject;

@SuppressWarnings("rawtypes")
public class AppRestPostClientImpl extends RestClient {

	
	private static final String REST_POST_PREFIX = "/posts";

	@SuppressWarnings("unchecked")
	public AppRestPostClientImpl(Class expectedType) {
		super(expectedType);
	}

	/**
	 * This function is used for create a new post in mongodb
	 * 
	 * @throws Exception
	 * */
	@SuppressWarnings("unchecked")
	public Post createPost(Post p) {
		Post post = (Post) doPost(REST_POST_PREFIX + "/providePostUpsert", p);
		return post;
	}

	/**
	 * This function is used for update a post in mongodb
	 * 
	 * @throws Exception
	 * */
	public Post updatePost(Post p) {
		Post post = (Post) doPost(REST_POST_PREFIX + "/providePostUpsert", p);
		return post;
	}

	/**
	 * This function is used from get appserver version post object from
	 * platform
	 * */
	public Post getPostById(String postId) {
		Post post = (Post) doGet(REST_POST_PREFIX + "/providePostQueryx/" + postId);
		return post;
	}

	@SuppressWarnings("unchecked")
	public List<Post> getPostByNumber(String userEmail, int startIndex, int number) {
		List<Post> post = (List<Post>) doGet(REST_POST_PREFIX + "/providePostLimitQuery/" + userEmail + "/" + startIndex + "/" + number);
		return post;
	}

	// TODO: need to figure out a way to do this better.
	@SuppressWarnings("unchecked")
	public boolean removeById(String postId) {
		String retMsg = (String) doPost(REST_POST_PREFIX + "/providePostDelete/" + postId, null);
		if (retMsg.contains("success")) {
			return true;
		} else {
			return false;
		}
	}
}
