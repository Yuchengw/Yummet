package com.iamhere.entities;

import org.joda.time.DateTime;

import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBPostComment;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/**
 * Platform entity for the PostComment
 * @author jassica
 *
 */
public class PostComment extends EntityObject {
	private PostObject parentPost;
	// TODO: to keep it simple now and not consider the rendering
	private String commentBody;
	private PostComment childComment; // TODO: is this only for child or sibling as well
	private UserObject createdBy;
	
	private DBPostComment dbComment;
	
	/*===================== Constructors =============================*/
	public PostComment(PostObject parent, String comment) {
		dbComment = new DBPostComment();
		setParentPost(parent);
		setCommentBody(comment);
	}
	
	public PostComment(DBPostComment db) {
		this.dbComment = db;
		reloadAllFieldInformationFromDbObject();
	}

	public PostComment(String id) {
		dbComment = new DBPostComment(id);
		setId(id);
	}

	/*===================== Setters and Getters =============================*/
	public PostObject getParentPost() {
		return parentPost;
	}

	public void setParentPost(PostObject parentPost) {
		this.parentPost = parentPost;
		dbComment.setParentPostWithEntity(parentPost);
	}

	public String getCommentBody() {
		return commentBody;
	}

	public void setCommentBody(String commentBody) {
		this.commentBody = commentBody;
		dbComment.setCommentBody(commentBody);
	}

	public PostComment getChildComment() {
		return childComment;
	}

	public void setChildComment(PostComment childComment) {
		this.childComment = childComment;
		dbComment.setChildCommentWithEntity(childComment);
	}
	
	public UserObject getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UserObject createdBy) {
		this.createdBy = createdBy;
		dbComment.setCreatedByWithEntity(createdBy);
	}

	/*===================== Override super method =============================*/
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
	public DBEntityObject getDbObject() {
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbComment.setId(getId());
		}
		return dbComment;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject() {
		if (dbComment.getChildComment() != null) {
			setChildComment(new PostComment(dbComment.getChildComment().toString()));
		}
		setCommentBody(dbComment.getCommentBody());
		setCreatedDate(new DateTime(dbComment.getCreatedDate()));
		setLastModifiedDate(new DateTime(dbComment.getLastModifiedDate()));
		setParentPost(new PostObject(dbComment.getParentPost().toString()));
		setId(dbComment.getId());
		setCreatedBy(new UserObject(dbComment.getCreatedBy().toString()));
	}
	
	public PostComment load() throws Exception {
		return (PostComment) super.load();
	}
}
