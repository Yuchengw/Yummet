package com.iamhere.platform.adapters;

import com.iamhere.entities.EntityObject;
import com.iamhere.entities.PostComment;
import com.iamhere.entities.PostObject;
import com.iamhere.entities.UserObject;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBPostComment;
import com.iamhere.mongodb.entities.DBPostObject;
import com.iamhere.mongodb.entities.DBUserObject;

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
//		EntityObject obj = null;
//		if (dbObject instanceof DBUserObject) {
//			DBUserObject dbUser = (DBUserObject) dbObject;
//			obj = new UserObject(dbUser);
//		} else if (dbObject instanceof DBPostObject) {
//			DBPostObject dbPost = (DBPostObject) dbObject;
//			obj = new PostObject(dbPost);
//		} else if (dbObject instanceof DBPostComment) {
//			DBPostComment dbComment = (DBPostComment) dbObject;
//			obj = new PostComment(dbComment);
//		}
	}
}
