package com.iamhere.mongodb.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.iamhere.entities.EntityObject;
import com.iamhere.entities.UserObject;
import com.iamhere.platform.func.DmlValidationHandler;

@Document(collection = "Likes")
public class DBLikeObject  extends DBEntityObject {
	private UserObject creator;
	private EntityObject target;
	
	public DBLikeObject()  {
	}

	public DBLikeObject(String id) {
		setId(id);
	}

	public UserObject getCreator() {
		return creator;
	}

	public void setCreator(UserObject creator) {
		this.creator = creator;
	}

	public EntityObject getTarget() {
		return target;
	}

	public void setTarget(EntityObject target) {
		this.target = target;
	}

	@Override
	public String getDbTableName() {
		return "Likes";
	}

	@Override
	public String toString() {
		return "Likes [Creator is =  " + creator + ", target = "  + target + "]";
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		// TODO: the target can only be user or post, post comment
	}
}
