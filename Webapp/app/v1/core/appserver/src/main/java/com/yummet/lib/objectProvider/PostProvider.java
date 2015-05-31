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

	private PlatformPostServiceImpl platformPostServiceImpl;
	
	public PostProvider() {
		this.platformPostServiceImpl = new PlatformPostServiceImpl();
	}
	
	public PlatformPostServiceImpl getUserServiceImpl() {
		return (PlatformPostServiceImpl) this.platformPostServiceImpl;
	}
	
	public Post add(User user, Post post) {
		post.setCreator(user);
		return this.platformPostServiceImpl.createPost(post);
	}

	public Post get(String postId) {
		return this.platformPostServiceImpl.getPostById(postId);
	}

	/**
	 * 
	 * */
	public List<Post> get(String username, String password, int number) {
		return this.platformPostServiceImpl.getPostByNumber(username, password, number);
	}

	public Boolean remove(int postId) {
		return (Boolean) this.platformPostServiceImpl.removeById(postId);
	} 

	/**
	 * update is expensive, think before do it
	 * */
	public void update(Post updatePost) {
		//TODO:
	}

}
