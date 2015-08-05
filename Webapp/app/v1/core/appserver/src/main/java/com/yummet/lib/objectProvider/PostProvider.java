package com.yummet.lib.objectProvider;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yummet.business.bean.Post;
import com.yummet.business.bean.User;
import com.yummet.lib.platformService.PlatformPostService;
import com.yummet.lib.platformService.PlatformPostServiceImpl;

/**
 * @author yucheng
 * @since 1
 * */
@Service
public class PostProvider {

	private PlatformPostServiceImpl platformPostService;
	
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
	
	/**
	 * 
	 * */
	public Post get(String postId) {
		return this.platformPostService.getPostById(postId);
	}

	/**
	 * 
	 * */
	public List<Post> get(String userEmail, int startIndex, int number) {
		return this.platformPostService.getPostByNumber(userEmail, startIndex, number);
        }

	public Boolean remove(String postId) {
		return (Boolean) this.platformPostService.removeById(postId);
	} 

	/**
	 * update is expensive, think before do it
	 * */
	public Post update(User user, Post updatePost) {
		updatePost.setCreator(user);
		return this.platformPostService.updatePost(updatePost);
	}

}
