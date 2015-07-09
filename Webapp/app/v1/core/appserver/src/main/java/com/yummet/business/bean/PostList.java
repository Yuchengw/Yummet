package com.yummet.business.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author yucheng
 * @version 1
 * */
public class PostList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 77091525724809582L;

	private int count;
	private List<Post> Posts;

	public PostList() {
		Posts = new ArrayList<Post>();
	}

	public PostList(List<Post> Posts) {
		this.Posts = Posts;
		this.count = Posts.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonSerialize
	public List<Post> getPosts() {
		return Posts;
	}

	public void setPosts(List<Post> Posts) {
		this.Posts = Posts;
	}

	// extension
	public void add(Post p1) {
		getPosts().add(p1);
	}

	public Post get(int index) {
		return getPosts().get(index);
	}

	public void remove(int index) {
		getPosts().remove(index);
	}
}
