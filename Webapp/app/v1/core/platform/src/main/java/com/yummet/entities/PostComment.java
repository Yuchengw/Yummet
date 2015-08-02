package com.yummet.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.platform.func.DmlValidationHandler;

/**
 * Platform entity for the PostComment
 * 
 * @author Jassica
 * @version 1
 */
@Document(collection = "PostComments")
public class PostComment extends EntityObject {
	private static final long serialVersionUID = -6011241820070393956L;
	private String parentPost;
	// TODO: to keep it simple now and not consider the rendering
	private String commentBody;
	private PostComment childComment; // TODO: is this only for child or sibling
										// as well
	private UserObject createdBy;

	/* ===================== Constructors ============================= */
	public PostComment(String parent, String comment) {
		setParentPost(parent);
		setCommentBody(comment);
	}

	public PostComment(String id) {
		setId(id);
	}

	public PostComment() {
	}

	/* ===================== Setters and Getters ============================= */
	public String getParentPost() {
		return parentPost;
	}

	public void setParentPost(String parentPost) {
		this.parentPost = parentPost;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public PostComment getChildComment() {
		return childComment;
	}

	public void setChildComment(PostComment childComment) {
		this.childComment = childComment;
	}

	public UserObject getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserObject createdBy) {
		this.createdBy = createdBy;
	}

	/* ===================== Override super method ============================= */
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		if (getCreatedBy() == null) {
			dml.addError("Post creator is not set!");
		}
		if (getParentPost() == null) {
			dml.addError("Parent post is forget to set!");
		}

		super.saveHook_Validate(dml);
	}

	@Override
	public void setId(String id) {
		super.setId(id);
		setCacheKey(id);
	}

	public PostComment load() throws Exception {
		return (PostComment) super.load();
	}

	@Override
	public String toString() {
		return "PostComments [id=" + getId() + ", comment=" + getCommentBody() + "]";
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDbTableName() {
		return "PostComments";
	}
}