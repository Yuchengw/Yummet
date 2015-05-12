package com.iamhere.relationships;

import com.iamhere.entities.EntityObject;
import com.iamhere.entities.UserObject;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBLikeObject;
import com.iamhere.platform.func.DmlOperationWrapper;
import com.iamhere.platform.func.DmlValidationHandler;
import com.iamhere.utilities.TextUtil;

/**
 * Represend a user like a certain post, comment or etc
 * @author jassica
 *
 */
public class Like extends EntityObject {
	private static final long serialVersionUID = -6011241820070393959L;  
	
	private UserObject creator;
	private EntityObject target;

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
	
	public Like(UserObject creator, EntityObject target) {
		this.setCreator(creator);
		this.setTarget(target);
	}

	public Like(DBEntityObject dbComment) {
		reloadAllFieldInformationFromDbObject(dbComment);
	}

	public Like(String id) {
		setId(id);
	}
	
	@Override
	public void saveHook_Validate(DmlValidationHandler dml) {
		if (getCreator() == null) {
			dml.addError("Like creator is not set!");
		}
		if (getTarget() == null) {
			dml.addError("Like target is not set!");
		}

		super.saveHook_Validate(dml);
	}
	
	@Override
	public DBEntityObject getDbObject() {
		DBLikeObject dbLike = new DBLikeObject();
		dbLike.setCreator(getCreator());
		dbLike.setTarget(getTarget());
		dbLike.setCreatedDate(getCreatedDate());
		dbLike.setLastModifiedDate(getLastModifiedDate());
		if (!TextUtil.isNullOrEmpty(getId())) {
			dbLike.setId(getId());
		}

		return dbLike;
	}

	@Override
	public void reloadAllFieldInformationFromDbObject(DBEntityObject dbObject) {
		DBLikeObject dbLike = (DBLikeObject) dbObject;
		setCreator(dbLike.getCreator());
		setTarget(dbLike.getTarget());
		setCreatedDate(dbLike.getCreatedDate());
		setId(dbLike.getId());
		setLastModifiedDate(dbLike.getLastModifiedDate());
	}

	public Like load() throws Exception {
		return (Like) super.load();
	}
	
	@Override
	public Class<?> getDbClass() {
		return DBLikeObject.class;
	}

	@Override
	public String getDbTableName() {
		return new DBLikeObject().getDbTableName();
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringUpdate() {
		return null;
	}

	@Override
	public boolean isRelatedInfoUpdate() {
		return false;
	}

	@Override
	public DmlOperationWrapper saveRelatedInfoDuringRemove() {
//		creator.removeCreatedPosts(getId());
		return null;
	}
}
