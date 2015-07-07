package com.yummet.business.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yucheng
 * @version 1
 * */
public class PostComment extends BeanObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1371607600111979397L;

	private String commentBody;
	private String Id;
	private Post parentPost;
	private List<PostComment> childComments; 
	
	private User createdBy;

	public PostComment(String id) {
		this.Id = id;
		super.setId(id);
	}
	
	public PostComment(Post parent, String comment) {
		setParentPost(parent);
		setCommentBody(comment);
		childComments = new ArrayList<PostComment>();
	}

	public PostComment(String id, Post parent, String comment) {
		setId(id);
		setParentPost(parent);
		setCommentBody(comment);
	}
	
	
	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}
	
	public Post getParentPost() {
		return parentPost;
	}

	public void setParentPost(Post parentPost) {
		this.parentPost = parentPost;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public List<PostComment> getChildComments() {
		return childComments;
	}

	public void addChildComment(PostComment childComment) {
		this.childComments.add(childComment);
	}

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
}
