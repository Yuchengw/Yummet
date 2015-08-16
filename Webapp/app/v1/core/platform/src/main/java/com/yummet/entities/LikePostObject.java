package com.yummet.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.platform.func.DmlOperationWrapper;
import com.yummet.platform.func.DmlValidationHandler;

/**
 * Platform entity for the likes relationship
 * 
 * @author Jessica
 * @version 1
 *
 */
@Document(collection = "Likes")
public class LikePostObject extends EntityObject {
	private static final long serialVersionUID = -6011241820070393959L;
	private UserObject liker;
	private String parentPost;
	private String parentPostType;

	/* ===================== Constructors ============================= */
	public LikePostObject() {
	}

	public LikePostObject(String id) {
		setId(id);
	}

	/* ===================== Setters and Getters ============================= */
	public String getParentPost() {
		return parentPost;
	}

	public void setParentPost(String parentPost) {
		this.parentPost = parentPost;
	}

	public String getParentPostType() {
		return parentPostType;
	}

	public void setParentPostType(String parentPostType) {
		// P for provide post and R for request post
		this.parentPostType = parentPostType;
	}

	public UserObject getLiker() {
		return liker;
	}

	public void setLiker(UserObject liker) {
		this.liker = liker;
	}

	/* ===================== Override super method ============================= */
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		if (getLiker() == null) {
			dml.addError("Like creator is not set!");
		}
		if (getParentPost() == null) {
			dml.addError("Parent post is forget to set!");
		}
		if (getParentPostType() == null) {
			dml.addError("Parent post type is forget to set!");
		}
		super.saveHook_Validate(dml);
	}

	@Override
	public void setId(String id) {
		super.setId(id);
		setCacheKey(id);
	}

	public LikePostObject load() throws Exception {
		return (LikePostObject) super.load();
	}

	@Override
	public String toString() {
		return "LikeInfo is [id=" + getId() + ", parentPost = "
				+ getParentPost() + ", parentPostType = " + getParentPostType()
				+ "]";
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		PostObject post = loadParentEntityInfo();
		// Try to load the post object information
		try {
			post = post.load();
			post.addLike(getId(), getLiker().getEmail());
			// TODO: verify if the parent has saved successfully
			return post.save();
		} catch (Exception e) {
			// TODO: if save fails, we need revert
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
		// update parent post information to remove the order information
		PostObject post = loadParentEntityInfo();
		// Try to load the post object information
		try {
			post = post.load();
			post.removeLike(getId());
			// TODO: verify if the parent has saved successfully
			return post.save();
		} catch (Exception e) {
			// TODO: if save fails, we need revert
			e.printStackTrace();
		}
		return null;
	}

	protected PostObject loadParentEntityInfo() {
		String postType = getParentPostType();
		PostObject post = null;
		if (postType.equals("P")) {
			post = new ProvidePostObject(getParentPost());
		} else if (postType.equals("R")) {
			post = new RequestPostObject(getParentPost());
		}
		return post;
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getDbTableName() {
		return "Likes";
	}

}