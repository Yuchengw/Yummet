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
	public List<Post> get(String username, String password, int number) {
		return this.platformPostService.getPostByNumber(username, password, number);
        }

	public List<Post> get(User user, int size, int cursor) {
		return this.platformPostService.get(user, size, cursor);
	}

	public Boolean remove(String postId) {
		return (Boolean) this.platformPostService.removeById(postId);
	} 

	/**
	 * update is expensive, think before do it
	 * */
	public Post update(Post updatePost) {
		return this.platformPostService.updatePost(updatePost);
	}

}
