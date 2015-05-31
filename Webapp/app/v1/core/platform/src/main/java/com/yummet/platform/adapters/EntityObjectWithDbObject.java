package com.yummet.platform.adapters;

import com.yummet.entities.EntityObject;
import com.yummet.entities.PostComment;
import com.yummet.entities.PostObject;
import com.yummet.entities.UserObject;
import com.yummet.mongodb.entities.DBEntityObject;
import com.yummet.mongodb.entities.DBPostComment;
import com.yummet.mongodb.entities.DBPostObject;
import com.yummet.mongodb.entities.DBUserObject;

/**
 * 
 * */
// Do we still need this?
public class EntityObjectWithDbObject {
	
	public EntityObject getEntityObjectFromDb(DBEntityObject dbObject) {
		if (dbObject == null) 
			return null;
		
		EntityObject obj = null;
		if (dbObject instanceof DBUserObject) {
			obj = new UserObject(dbObject);
		} else if (dbObject instanceof DBPostObject) {
			DBPostObject dbPost = (DBPostObject) dbObject;
			obj = new PostObject(dbPost);
		} else if (dbObject instanceof DBPostComment) {
			DBPostComment dbComment = (DBPostComment) dbObject;
			obj = new PostComment(dbComment);
		}
		
		return obj;
	}
	
	public DBEntityObject getDbObjectFromEntityObject(EntityObject obj) {
		return null;
	}
}