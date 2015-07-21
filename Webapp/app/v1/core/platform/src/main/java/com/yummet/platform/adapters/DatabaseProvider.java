package com.yummet.platform.adapters;

import java.util.List;
import java.util.Map;

import com.yummet.entities.EntityObject;

/**
 * Database provider for the whole system
 * 
 * @author Jessica
 * @version 1
 */
public interface DatabaseProvider {
	
	public Object getDbThread();
	/**
	 * Get all the records for a certain table and map all the information in @EntityObject
	 * format
	 * 
	 * @param tableName
	 * @param info
	 * @return
	 */
	public List<EntityObject> getAllTableRecords(String tableName,
			EntityObject info);

	/**
	 * Get all the records mapped the query and return the information in @EntityObject
	 * format
	 * 
	 * @param tableName
	 * @param info
	 * @param queryInfo
	 * @return
	 */
	public List<EntityObject> getRecordsBasedOnQuery(String tableName,
			EntityObject info, Map<String, Object> queryInfo);

	public List<EntityObject> getRecordsBasedOnQuery(EntityObject info);
	
	/**
	 * Insert a bulk of records to the same table name into the database
	 * 
	 * @param tableName
	 * @param records
	 * @throws Exception
	 */
	public void insertRecords(String tableName, EntityObject[] records)
			throws Exception;

	/**
	 * Remove a set of records from the same table
	 * 
	 * @param tableName
	 * @param records
	 * @throws Exception
	 */
	public void removeRecords(String tableName, EntityObject[] records)
			throws Exception;

	/**
	 * Update a bulk records to the same table
	 * 
	 * @param tableName
	 * @param records
	 * @throws Exception
	 */
	public void saveRecords(String tableName, EntityObject[] records)
			throws Exception;

	/**
	 * Return true if the record with same primary key already exists
	 * 
	 * @param tablename
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public boolean exists(String tablename, Map<String, Object> query,
			EntityObject record) throws Exception;
}