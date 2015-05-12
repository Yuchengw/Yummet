package com.iamhere.cache;

import com.iamhere.entities.EntityObject;

/**
 * Interface for the cache management
 * @author jassica
 * @version 1
 *
 */
public interface CacheManager {
	/**
	 * Insert a bulk of records
	 * @param tableName
	 * @param records
	 * @throws Exception
	 */
	public boolean insertRecords(EntityObject[] records)
			throws Exception;

	/**
	 * Insert a single record
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public boolean insert(final EntityObject entity) throws Exception;
	
	/**
	 * Remove a set of records 
	 * @param tableName
	 * @param records
	 * @throws Exception
	 */
	public void removeRecords(EntityObject[] records)
			throws Exception;

	/**
	 * Remove a single record
	 * @param record
	 * @throws Exception
	 */
	public void delete(final EntityObject record) throws Exception;
	
	/**
	 * Update a bulk records 
	 * @param tableName
	 * @param records
	 * @throws Exception
	 */
	public boolean saveRecords(EntityObject[] records)
			throws Exception;
	
	/**
	 * Update a single record
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public boolean update(final EntityObject record) throws Exception;
	
	/**
	 * Get a record informatino from cache with primary key information provided
	 * @param record
	 * @return
	 */
	public EntityObject get(final EntityObject record);
	
	/**
	 * Return true if the record with same primary key already exists
	 * @param tablename
	 * @param record
	 * @return
	 * @throws Exception
	 */
	public boolean exists(EntityObject record) throws Exception;
	
	/**
	 * Delete the data from cache
	 * @return
	 */
	public String flushDB();
}