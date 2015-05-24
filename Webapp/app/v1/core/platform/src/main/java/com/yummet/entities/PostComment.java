package com.yummet.entities;

import com.yummet.mongodb.entities.DBEntityObject;
import com.yummet.mongodb.entities.DBPostComment;
import com.yummet.platform.func.DmlValidationHandler;
import com.yummet.utilities.TextUtil;

/**
 * Platform entity for the PostComment
 * @author Jassica 
 * @version 1
 */
public class PostComment extends EntityObject {
	private static final long serialVersionUID = -6011241820070393956L;  
	private PostObject parentPost;
	// TODO: to keep it simple now and not consider the rendering
	private String commentBody;
	private PostComment childComment; // TODO: is this only for child or sibling as well
	private UserObject createdBy;
	
	/*===================== Constructors =============================*/
	public PostComment(PostObject parent, String comment) {
		setParentPost(parent);
		setCommentBody(comment);
	}
	
	public PostComment(DBPostComment db) {
		reloadAllFieldInformationFromDbObject(db);
	}

	public PostComment(String id) {
		setId(id);
	}

	/*===================== Setters and Getters =============================*/
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
		DBPostComment dbComment = new DBPostComment();
		dbComment.setParentPostWithEntity(parentPost);
		dbComment.setCommentBody(commentBody);
		dbComment.setChildCommentWithEntity(childComment);
		dbComment.setCreatedByWithEntity(createdBy);
		dbComment.setCreatedDate(getCreatedDate());
		dbComment.setLastModifiedDate(getLastModifiedDate());
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbComment.setId(getId());
		}
		return dbComment;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject(DBEntityObject dbObject) {
		DBPostComment dbComment = (DBPostComment) dbObject;
		if (dbComment.getChildComment() != null) {
			setChildComment(new PostComment(dbComment.getChildComment()));
		}
		setCommentBody(dbComment.getCommentBody());
		setCreatedDate(dbComment.getCreatedDate());
		setLastModifiedDate(dbComment.getLastModifiedDate());
		setParentPost(new PostObject(dbComment.getParentPost()));
		setId(dbComment.getId());
		setCreatedBy(new UserObject(dbComment.getCreatedBy()));
	}
	
	public PostComment load() throws Exception {
		return (PostComment) super.load();
	}

	@Override
	public Class<?> getDbClass() {
		return DBPostComment.class;
	}

	@Override
	public String getDbTableName() {
		return new DBPostComment().getDbTableName();
	}
}