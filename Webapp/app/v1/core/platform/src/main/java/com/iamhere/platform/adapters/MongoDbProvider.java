package com.iamhere.platform.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.iamhere.entities.EntityObject;
import com.iamhere.entities.PostComment;
import com.iamhere.entities.PostObject;
import com.iamhere.entities.UserObject;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBPostComment;
import com.iamhere.mongodb.entities.DBPostObject;
import com.iamhere.mongodb.entities.DBUserObject;
import com.iamhere.utilities.LogUtil;

/**
 * Mongo DB implementation for the DB functions
 * 
 * @author jassica
 *
 */
public class MongoDbProvider implements DatabaseProvider {
	public RegistrationBean registrationBean;
	// Auto threadsafe singleton pattern
	private static final MongoTemplate mongoOps = (MongoTemplate) new ClassPathXmlApplicationContext("spring.xml").getBean("mongoTemplate");

	public MongoDbProvider() {
	}

	@Override
	public List<EntityObject> getAllTableRecords(String tableName,
			EntityObject info) {
		List<EntityObject> records = new ArrayList<EntityObject>();
		List<DBEntityObject> dbRecords = (List<DBEntityObject>) mongoOps
				.findAll(info.getDbObject().getClass());
		// UserDAO userDaoImpl = (UserDAO) context.getBean("userDaoImpl");
		for (DBEntityObject dbObject : dbRecords) {
			EntityObject obj = null;
			if (dbObject instanceof DBUserObject) {
				DBUserObject dbUser = (DBUserObject) dbObject;
				obj = new UserObject(dbUser);
			} else if (dbObject instanceof DBPostObject) {
				DBPostObject dbPost = (DBPostObject) dbObject;
				obj = new PostObject(dbPost);
			} else if (dbObject instanceof DBPostComment) {
				DBPostComment dbComment = (DBPostComment) dbObject;
				obj = new PostComment(dbComment);
			}
			records.add(obj);
		}
		return records;
	}

	@Override
	public List<EntityObject> getRecordsBasedOnQuery(String tableName,
			EntityObject info, Map<String, Object> queryInfo) {
		List<EntityObject> records = new ArrayList<EntityObject>();
		Query query = createQueryBasedOnMap(queryInfo);
		List<DBEntityObject> dbRecords = (List<DBEntityObject>) mongoOps.find(
				query, info.getDbObject().getClass());
		
		for (DBEntityObject dbObject : dbRecords) {
			EntityObject obj = null;
			if (dbObject instanceof DBUserObject) {
				DBUserObject dbUser = (DBUserObject) dbObject;
				obj = new UserObject(dbUser);
			} else if (dbObject instanceof DBPostObject) {
				DBPostObject dbPost = (DBPostObject) dbObject;
				obj = new PostObject(dbPost);
			} else if (dbObject instanceof DBPostComment) {
				DBPostComment dbComment = (DBPostComment) dbObject;
				obj = new PostComment(dbComment);
			}
			records.add(obj);
		}
		return records;
	}

	/**
	 * Create the query object with the map query Info
	 * @param queryInfo
	 * @return
	 */
	private Query createQueryBasedOnMap(Map<String, Object> queryInfo) {
		LogUtil.getInstance(MongoDbProvider.class).debug("Query record with information => " + queryInfo);
		Query query = new Query();
		Criteria criteria = null;
		for (String key : queryInfo.keySet()) {
			if (criteria == null) {
				criteria = Criteria.where(key).is(queryInfo.get(key));
			} else {
				criteria.and(key).is(queryInfo.get(key));
			}
			LogUtil.getInstance(MongoDbProvider.class).debug("Query record with criteria => key -  " + key + "; value - " + queryInfo.get(key) );
		}
		query.addCriteria(criteria);
		return query;
	}

	@Override
	public void saveRecords(String tableName, EntityObject[] records) {
		if (records == null) {
			return;
		}
		int numberOfRecordsSaved = 0;
		LogUtil.getInstance(MongoDbProvider.class).debug("Start saving " + records.length + " records => " );
		for (EntityObject record : records) {
			mongoOps.save(record.getDbObject());
			numberOfRecordsSaved++;
			LogUtil.getInstance(MongoDbProvider.class).debug("Saving " + numberOfRecordsSaved + " => " + record.getDbObject().toString());
			// TODO: how to make bulk happen
		}
		assert (numberOfRecordsSaved == records.length);
	}

	@Override
	public void insertRecords(String tableName, EntityObject[] records)
			throws Exception {
		if (records == null) {
			return;
		}
		int numberOfRecordsSaved = 0;
		LogUtil.getInstance(MongoDbProvider.class).debug("Start inserting " + records.length + " records => " );
		for (EntityObject record : records) {
			mongoOps.save(record.getDbObject());
			numberOfRecordsSaved++;
			LogUtil.getInstance(MongoDbProvider.class).debug("Inserting " + numberOfRecordsSaved + " => " + record.getDbObject().toString());
			// TODO: how to make bulk happen
		}
		assert (numberOfRecordsSaved == records.length);
	}

	/**
	 * Remove all the records with id passed in If the record has no id, the
	 * first version will ignore the removing request
	 */
	@Override
	public void removeRecords(String tableName, EntityObject[] records)
			throws Exception {
		if (records == null) {
			return;
		}
		int numberOfRecordsSaved = 0;
		LogUtil.getInstance(MongoDbProvider.class).debug("Start removing " + records.length + " records => " );
		for (EntityObject record : records) {
			mongoOps.remove(record.getDbObject());
			numberOfRecordsSaved++;
			LogUtil.getInstance(MongoDbProvider.class).debug("Removing " + numberOfRecordsSaved + " => " + record.getDbObject().toString());
		}
		// As remove is using filters on the object all fields, we need either
		// pass in the whole object matching information or switch the passin
		// object information and remove the fields we need.
		// To make performance better, we don't want every remove to load whole
		// object from db first, we will tweast the object information and only
		// pass in the object information which has been set.
		// Option One: 1. Filter all the objects and get all with id first and
		// bulk remove all objects
		// 2. Get all the objects where the id is not provided so we will remove
		// all the records match the criteria one by one
		// Option Two: 1. Treat each object as its own and remove the field we
		// don't care as the value is null 2. Remove the records one by one
		// Currently we assume all records to be removed has id provided
		assert(numberOfRecordsSaved == records.length);
	}

	@Override
	public boolean exists(String tablename,  Map<String, Object> queryInfo, EntityObject record)
			throws Exception {
		Query query = createQueryBasedOnMap(queryInfo);
		LogUtil.getInstance(MongoDbProvider.class).debug("Calling MongoDb Exist function on record => " + record.getDbObject().toString() );
		return  mongoOps.exists(
				query, record.getDbObject().getClass());
	}
}
