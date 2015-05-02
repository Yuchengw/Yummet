package com.iamhere.platform.func;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.iamhere.cache.CacheManager;
import com.iamhere.entities.EntityObject;
import com.iamhere.enums.DMLEvents;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.platform.adapters.SystemContext;
import com.iamhere.platform.adapters.DatabaseProvider;
import com.iamhere.utilities.LogUtil;

/**
 * Static utilities for performing insert and update operations on entity
 * objects
 * 
 * @author jassica
 *
 */
public class BulkEntityOperations {
	/**
	 * Bulk save a collection of entities
	 * 
	 * @param entityObjects
	 * @param dmlType
	 * @return
	 */
	public static DmlOperationWrapper bulkSave(
			Collection<? extends EntityObject> entityObjects, DMLEvents dmlType) {
		// Validate the objects first
		validateEntityObjects(entityObjects);
		if (!entityObjects.isEmpty()) {
			DmlOperationWrapper dmlState = performBulkDml(entityObjects,
					dmlType);
			return dmlState;
		}
		return null;
	}

	/**
	 * Bulk load a collection of entities
	 * 
	 * @param entityObjects
	 * @return
	 */
	public static List<EntityObject> bulkLoad(
			Collection<EntityObject> entityObjects) {
		ArrayList<EntityObject> reload = new ArrayList<EntityObject>();
		ArrayList<EntityObject> needLoadFromDb = new ArrayList<EntityObject>();
		if (entityObjects == null) {
			throw new IllegalStateException(
					"bulk operations should not allow null list objects");
		}
		if (entityObjects.isEmpty()) {
			return reload;
		}

		// Check the cache first to see if the entity is there. If missing
		// happens, we will go for db to find the information.
		CacheManager cacheContext = SystemContext.getCacheContext();
		for (EntityObject eo : entityObjects) {
			EntityObject cacheEo = cacheContext.get(eo);
			if (cacheEo != null) {
				reload.add(cacheEo);
			} else {
				// if we can not find the object from cache, we need search for
				// db
				needLoadFromDb.add(eo);
			}
		}

		// Load from db if there is any record information cannot be found under
		// cache
		if (!needLoadFromDb.isEmpty()) {
			DatabaseProvider dbContext = SystemContext.getContext();
			for (EntityObject eo : needLoadFromDb) {
				DBEntityObject dbEo = eo.getDbObject();
				List<EntityObject> queryResults = dbContext
						.getRecordsBasedOnQuery(dbEo.getDbTableName(), eo,
								dbEo.getFieldsAndValues());
				// TODO the return result should be greater 1 or exactly 1?
				if (queryResults != null && queryResults.size() > 0) {
					LogUtil.getInstance(BulkEntityOperations.class)
							.debug("The query returns more than 1 result. And we will only get the first one. <==");
					eo = queryResults.get(0);
					reload.add(eo);
					// Update the cache information if we find the result under database
					try {
						boolean cacheSucceeds = cacheContext.insert(eo);
						if (cacheSucceeds) {
							LogUtil.getInstance(BulkEntityOperations.class)
									.debug("Update cache with the new db values Succeeds<==");
						} else {
							LogUtil.getInstance(BulkEntityOperations.class)
									.debug("Update cache with the new db values Fails <==");
						}
					} catch (Exception e) {
						LogUtil.getInstance(BulkEntityOperations.class).debug(
								"Update cache with the new db values Fails <=="
										+ e.getStackTrace());
					}
				} else {
					LogUtil.getInstance(BulkEntityOperations.class).debug(
							"The query find nothing. <==");
				}
			}
		}
		return reload;
	}

	/**
	 * Bulk Create or Update the records
	 * @param entityObjects
	 * @param dmlType
	 * @return
	 */
	private static DmlOperationWrapper performBulkDml(
			Collection<? extends EntityObject> entityObjects, DMLEvents dmlType) {
		DmlOperationWrapper dmlOperationState = new DmlOperationWrapper(
				entityObjects, dmlType);
		List<EntityObject> afterFirstValidationObjects = dmlOperationState
				.getEntityObjectsWithoutError();
		// do the db work
		// TODO: change it to bulk
		// TODO: hash against the entity to make bulk really happens
		DatabaseProvider dbContext = SystemContext.getContext();
		CacheManager cacheContext = SystemContext.getCacheContext();
		// We assume a collection items will be saved and we will do them one by
		// one
		for (EntityObject eo : afterFirstValidationObjects) {
			try {
				dbContext.saveRecords(eo.getDbTableName(),
						new EntityObject[] { eo });
				LogUtil.getInstance(BulkEntityOperations.class).debug(
						"Save succeeds. <==" + eo);
				// Save or update the cache
				boolean cacheResult = cacheContext
						.saveRecords(new EntityObject[] { eo });
				if (!cacheResult) {
					dmlOperationState.addErrorToDmlOperation(eo,
							"The cache save fails with some reason!");
				}
				// TODO: If cache fails, should roll over db?
			} catch (Exception e) {
				// TODO: put the error information back to dmlOperationState
				dmlOperationState.addErrorToDmlOperation(eo, e.getMessage());
				LogUtil.getInstance(BulkEntityOperations.class).debug(
						"Save fails. <==");
			}
		}
		return dmlOperationState;
	}

	/**
	 * Validate all the records before bulk operation
	 * @param entityObjects
	 */
	static final void validateEntityObjects(
			Collection<? extends EntityObject> entityObjects) {
		if (entityObjects == null) {
			throw new IllegalStateException(
					"bulk operations should not allow null list objects");
		}
		if (entityObjects.isEmpty()) {
			return;
		}

		// TODO:
		Map<EntityObject, Boolean> ident = new IdentityHashMap<EntityObject, Boolean>(
				entityObjects.size() << 1);
		for (EntityObject eo : entityObjects) {
			if (eo == null) {
				throw new IllegalStateException(
						"bulk operations recieve a null object in the list");
			}
			if (ident.put(eo, Boolean.TRUE) != null) {
				throw new IllegalStateException(
						"bulk operations recieved the same objects more than twice");
			}
		}
	}

	// TODO: Return a list of result???
	public static boolean bulkRemove(List<EntityObject> entityObjects) {
		if (entityObjects == null || entityObjects.isEmpty()) {
			return true;
		}
		DatabaseProvider dbContext = SystemContext.getContext();
		CacheManager redisContext = SystemContext.getCacheContext();
		for (EntityObject eo : entityObjects) {
			try {
				boolean removeResult = dbContext.removeRecords(
						eo.getDbTableName(), new EntityObject[] { eo });
				if (removeResult) {
					// remove the record from the cache
					redisContext.removeRecords(new EntityObject[] { eo });
				}
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
