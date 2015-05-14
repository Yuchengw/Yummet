package com.iamhere.mongodb.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.iamhere.entities.PostComment;
import com.iamhere.entities.PostObject;
import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlValidationHandler;

/**
 * Mongodb representation for the PostComment
 * @author jassica
 *
 */
@Document(collection = "PostComments")
public class DBPostComment extends DBEntityObject {
	private PostObject parentPost;
	// TODO: to keep it simple now and not consider the rendering
	private String commentBody;
	private String childComment; // TODO: is this only for child or sibling as
									// well
	private UserObject createdBy;

	public DBPostComment() {
	}

	public DBPostComment(String id) {
		setId(id);
	}

	public PostObject getParentPost() {
		return parentPost;
	}

	public void setParentPost(PostObject parentPost) {
		this.parentPost = parentPost;
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
	}

	public String getChildComment() {
		return childComment;
	}

	public void setChildComment(String childComment) {
		this.childComment = childComment;
	}

	public UserObject getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserObject createdBy) {
		this.createdBy = createdBy;
	}
	
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
	}

	@Override
	public String getDbTableName() {
		return "PostComments";
	}

	public void setChildCommentWithEntity(PostComment childComment2) {
		this.childComment = childComment2 == null ? null : childComment2.getId();
	}

	@Override
	public String toString() {
		return "PostComments [ Body =  " + commentBody + "]";
	}

	

}