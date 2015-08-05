package com.yummet.lib.platformService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;

/**
 * @author yucheng
 * @since 1
 * */
public class PlatformPostServiceImpl extends PlatformPostService implements PlatformPostServiceConstants{

	private static final Logger logger = LoggerFactory
			.getLogger(PlatformPostServiceImpl.class);

	public PlatformPostServiceImpl() {
	}

	/**
	 * This function is used for create a new post in mongodb
	 * 
	 * @throws Exception
	 * */
	public Post createPost(Post post) {
		Post createdPost = null;
		try {
			createdPost = (Post)getPostRestClient().createPost(post);
		} catch (Exception e) {
			logger.debug("there is something wrong when inserting post object"
					+ e.getStackTrace());
		}
		return createdPost;
	}

	/**
	 * This function is used for update a post in mongodb
	 * 
	 * @throws Exception
	 * */
	public Post updatePost(Post post) {
		Post updatedPost = null;
		try {
			updatedPost = (Post) getPostRestClient().updatePost(post);
		} catch (Exception e) {
			logger.error("there is something wrong when updating post object"
					+ e.getStackTrace());
		}
		return updatedPost;
	}

	/**
	 * This function is used from get appserver version post object from
	 * platform
	 * */
	public Post getPostById(String postId) {
		Post post = null;
		try {
			post = (Post) getPostRestClient().getPostById(postId);
		} catch (Exception e) {
			logger.error("Error happens when retriving post object"
					+ e.getStackTrace());
		}
		return post;
	}

	public List<Post> getPostByNumber(String username, int startIndex, int number) {
		List<Post> posts = null;
		try {
			posts = (List<Post>) getPostRestClient().getPostByNumber(username, startIndex, number);
		} catch (Exception e) {
			logger.debug("Error happens when retriving post object"
					+ e.getStackTrace());
		}
		return posts;
	}

	public boolean removeById(String postId) {
		try {
			return getPostRestClient().removeById(postId);
		} catch (Exception e) {
			logger.error("there is something wrong when deleting post object"
					+ e.getStackTrace());
			return false;
		}
	}
}
