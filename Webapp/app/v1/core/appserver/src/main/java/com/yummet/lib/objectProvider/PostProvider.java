package com.yummet.lib.objectProvider;

import java.util.List;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.PostList;
import com.yummet.business.bean.User;
import com.yummet.lib.platformService.PlatformPostService;
import com.yummet.lib.platformService.PlatformPostServiceImpl;

/**
 * @author yucheng
 * @since 1
 * */
public class PostProvider {

	private PlatformPostService platformPostService;
	
	public PostProvider() {
		this.platformPostService = new PlatformPostServiceImpl();
	}
	
	public PlatformPostService getPostService() {
		return this.platformPostService;
	}
	
	public Post add(User user, Post post) {
		post.setCreator(user);
		return this.platformPostService.createPost(post);
	}

	public Post get(String postId) {
		return this.platformPostService.getPostById(postId);
	}

	/**
	 * 
	 * */
	public List<Post> get(User user, int number, int cursor) {
		return null;
	}

	public Boolean remove(String postId) {
		return (Boolean) this.platformPostService.removeById(postId);
	} 

	/**
	 * update is expensive, think before do it
	 * */
	public void update(Post updatePost) {
		//TODO:
	}

}
