package com.iamhere.relationships;

import com.iamhere.entities.EntityObject;
import com.iamhere.entities.UserObject;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBFollowObject;

public class Follow extends EntityObject {
	private static final long serialVersionUID = -6011241820070393959L;  
	
	private UserObject creator;
	private EntityObject target;
	
	public Follow(UserObject creator, EntityObject target) {
		this.creator = creator;
		this.target = target;
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
	public DBEntityObject getDbObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject(DBEntityObject dbObject) {
		DBFollowObject dbFollow = (DBFollowObject)  dbObject;
		setCreator(new UserObject(dbFollow.getCreator()));
		setTarget(new EntityObject(dbFollow.getTarget()));
	}

	@Override
	public Class<?> getDbClass() {
		return DBFollowObject.class;
	}

	@Override
	public String getDbTableName() {
		return "Follows";
	}
	
}
