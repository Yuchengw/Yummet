package com.iamhere.platform.func;

import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import com.iamhere.entities.EntityObject;
import com.iamhere.enums.DMLEvents;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.platform.adapters.DBContext;
import com.iamhere.platform.adapters.DatabaseProvider;

/**
 * Static utilities for performing insert and update operations on entity
 * objects
 * 
 * @author jassica
 *
 */
public class BulkEntityOperations {

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

	public static List<EntityObject> bulkLoad(
			Collection<EntityObject> entityObjects) {
		ArrayList<EntityObject> reload = new ArrayList<EntityObject>();
		if (entityObjects == null) {
			throw new IllegalStateException(
					"bulk operations should not allow null list objects");
		}
		if (entityObjects.isEmpty()) {
			return reload;
		}

		DatabaseProvider dbContext = DBContext.getContext();
		for (EntityObject eo : entityObjects) {
			DBEntityObject dbEo = eo.getDbObject();
			List<EntityObject> queryResults = dbContext.getRecordsBasedOnQuery(
					dbEo.getDbTableName(), eo, dbEo.getFieldsAndValues());
			// TODO the return result should be greater 1 or exactly 1?
			if (queryResults != null && queryResults.size() > 0) {
				eo = queryResults.get(0);
				reload.add(queryResults.get(0));
			}
		}
		return reload;
	}

	private static DmlOperationWrapper performBulkDml(
			Collection<? extends EntityObject> entityObjects, DMLEvents dmlType) {
		DmlOperationWrapper dmlOperationState = new DmlOperationWrapper(
				entityObjects, dmlType);
		List<EntityObject> afterFirstValidationObjects = dmlOperationState
				.getEntityObjectsWithoutError();
		// do the db work
		// TODO: change it to bulk
		DatabaseProvider dbContext = DBContext.getContext();
		for (EntityObject eo : afterFirstValidationObjects) {
			try {
				DBEntityObject dbEo = eo.getDbObject();
				dbContext.saveRecords(dbEo.getDbTableName(),
						new EntityObject[] { eo });
				// reload does not required for MongoDb as id will be already in
				// the eo
				eo.setId(dbEo.getId());
				// TODO
			} catch (Exception e) {
				// TODO: put the error information back to dmlOperationState
				dmlOperationState.addErrorToDmlOperation(eo, e.getMessage());
			}
		}
		return dmlOperationState;
	}

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
		DatabaseProvider dbContext = DBContext.getContext();
		for (EntityObject eo : entityObjects) {
			try {
				DBEntityObject dbEo = eo.getDbObject();
				dbContext.removeRecords(dbEo.getDbTableName(),
						new EntityObject[] { eo });
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}
}
