package com.yummet.business.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author yucheng
 * @version 1
 * */
public class PostCommentList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7165192163461913556L;
	
	private int count;
	private List<PostComment> postcomments;

	public PostCommentList() {
		postcomments = new ArrayList<PostComment>();
	}

	public PostCommentList(List<PostComment> postcomments) {
		this.postcomments = postcomments;
		this.count = postcomments.size();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@JsonSerialize
	public List<PostComment> getPostComments() {
		return postcomments;
	}

	public void setUsers(List<PostComment> postcomments) {
		this.postcomments = postcomments;
	}

	// extension
	public void add(PostComment pm1) {
		getPostComments().add(pm1);
	}

	public PostComment get(int index) {
		return getPostComments().get(index);
	}

	public void remove(int index) {
		getPostComments().remove(index);
	}
}
