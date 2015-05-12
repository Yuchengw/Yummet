package com.iamhere.mongodb.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.iamhere.platform.func.DmlValidationHandler;

@Document(collection = "Follows")
public class DBFollowObject  extends DBEntityObject {
	private String creator;
	private String target;
	
	public DBFollowObject()  {
	}

	public DBFollowObject(String id) {
		setId(id);
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String getDbTableName() {
		return "Follows";
	}

	@Override
	public String toString() {
		return "Orders [Creator is =  " + creator + ", target = "  + target + "]";
	}

	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		// TODO: the target can only be user or post
	}
}

