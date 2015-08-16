package com.yummet.platform.adapters;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.yummet.entities.EntityObject;
import com.yummet.mongodb.YummetMongoConfig;
import com.yummet.utilities.LogUtil;

import org.slf4j.Logger;

/**
 * Mongo DB implementation for the DB functions
 * 
 * @author jassica
 * @version 1
 */
public class MongoDbProvider implements DatabaseProvider {
	// Auto threadsafe singleton pattern
	private static final MongoTemplate mongoOps = (MongoTemplate) new AnnotationConfigApplicationContext(YummetMongoConfig.class)
													.getBean("mongoTemplate");
	private static final Logger logger = LogUtil.getInstance(MongoDbProvider.class);

	public MongoDbProvider() {
	}
	
	
	public Object getDbThread() {
		return mongoOps;
	}
	/**
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<EntityObject> getAllTableRecords(String tableName, EntityObject info) {
		List<EntityObject> dbRecords = (List<EntityObject>) mongoOps.findAll(info.getClass());
		return dbRecords;
	}

	/**
	 * 
	 * */
	@SuppressWarnings("unchecked")
	public List<EntityObject> getRecordsBasedOnQuery(String tableName, EntityObject info, Map<String, Object> queryInfo) {
		Query query = createQueryBasedOnMap(queryInfo);
		List<EntityObject> dbRecords = (List<EntityObject>) mongoOps.find(query, info.getClass());
		return dbRecords;
	}
	
	/**
	 * Create the query object with the map query Info
	 * 
	 * @param queryInfo
	 * @return
	 */
	private Query createQueryBasedOnMap(Map<String, Object> queryInfo) {
		logger.debug(
				"Query record with information => " + queryInfo);
		Query query = new Query();
		Criteria criteria = null;
		for (String key : queryInfo.keySet()) {
			if (criteria == null) {
				criteria = Criteria.where(key).is(queryInfo.get(key));
			} else {
				criteria.and(key).is(queryInfo.get(key));
			}
			logger.debug(
					"Query record with criteria => key -  " + key
							+ "; value - " + queryInfo.get(key));
		}
		query.addCriteria(criteria);
		return query;
	}

	/**
	 * 
	 * */
	public void saveRecords(String tableName, EntityObject[] records) {
		if (records == null) {
			return;
		}
		int numberOfRecordsSaved = 0;
		logger.debug("Start saving " + records.length + " records => ");
		for (EntityObject record : records) {
			mongoOps.save(record);
			numberOfRecordsSaved++;
			logger.debug("Saving " + numberOfRecordsSaved + " => " + record.toString());
			// TODO: how to make bulk happen
		}
		assert (numberOfRecordsSaved == records.length);
	}

	/**
	 * 
	 * */
	public void insertRecords(String tableName, EntityObject[] records) throws Exception {
		if (records == null) {
			return;
		}
		int numberOfRecordsSaved = 0;
		logger.debug("Start inserting " + records.length + " records => ");
		for (EntityObject record : records) {
			mongoOps.save(record);
			numberOfRecordsSaved++;
			logger.debug("Inserting " + numberOfRecordsSaved + " => " + record.toString());
			// TODO: how to make bulk happen
		}
		assert (numberOfRecordsSaved == records.length);
	}

	/**
	 * Remove all the records with id passed in If the record has no id, the
	 * first version will ignore the removing request
	 */
	public void removeRecords(String tableName, EntityObject[] records) throws Exception {
		if (records == null) {
			return;
		}
		int numberOfRecordsSaved = 0;
		logger.debug("Start removing " + records.length + " records => ");
		for (EntityObject record : records) {
			mongoOps.remove(record);
			numberOfRecordsSaved++;
			logger.debug("Removing " + numberOfRecordsSaved + " => "
							+ record.toString());
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
		assert (numberOfRecordsSaved == records.length);
	}

	/**
	 * 
	 * */
	public boolean exists(String tablename, Map<String, Object> queryInfo, EntityObject record) throws Exception {
		Query query = createQueryBasedOnMap(queryInfo);
		logger.debug("Calling MongoDb Exist function on record => " + record.toString());
		return mongoOps.exists(query, record.getClass());
	}

	@Override
	public List<EntityObject> getRecordsBasedOnQuery(EntityObject info) {
		return getRecordsBasedOnQuery(info.getDbTableName(), info, info.getFieldsAndValues());
	}
}