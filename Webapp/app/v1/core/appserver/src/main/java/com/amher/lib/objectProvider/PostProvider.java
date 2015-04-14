package com.amher.lib.objectProvider;

import java.util.List;

import com.amher.business.bean.Post;
import com.amher.business.bean.PostList;
import com.amher.business.bean.User;

/**
 * @author yucheng
 * @version 1
 * */
public class PostProvider {

	private static PostList allPosts;
	/**
	 * mocking
	 * */
	static {
		allPosts = new PostList();
		Post p1 = new Post("1", new User("1", "Yucheng", "Wang",
				"ycwmike@gmail.com"), "Post1", "China", 1);
		Post p2 = new Post("2", new User("2", "George", "Lin",
				"gglin@gmail.com"), "Post2", "USA", 2);
		allPosts.add(p1);
		allPosts.add(p2);
	}

	public void add(Post user) {
		allPosts.add(user);
	}

	public Post get(int index) {
		return allPosts.get(index);
	}

	public List<Post> getAll() {
		return allPosts.getPosts();
	}

	public void remove(int id) {
		allPosts.remove(id);
	}

	/**
	 * update is expensive, think before do it
	 * */
	public void update(Post updatePost) {
		update(updatePost.getId(), updatePost);
	}
	
	public void update(String index, Post newPost) {
		List<Post> allPostList = allPosts.getPosts();
		allPostList.set(Integer.parseInt(index), newPost);
	}
}
