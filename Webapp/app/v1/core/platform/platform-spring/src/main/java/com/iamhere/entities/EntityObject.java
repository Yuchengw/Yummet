package com.iamhere.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import com.iamhere.enums.DMLEvents;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.platform.func.BulkEntityOperations;
import com.iamhere.platform.func.DmlOperationWrapper;
import com.iamhere.platform.func.DmlValidationHandler;

/**
 * Represente a signle entity row for data manipulation. This object contains an
 * array of fields with the purpose of holding data values for that row. The
 * class is responsible for persisting and reading those value.
 * 
 * @author jassica
 * @version 1
 */
public abstract class EntityObject implements Serializable {
	private static final long serialVersionUID = -6011241820070393952L;
	protected String id;
	private DateTime createdDate;
	private DateTime lastModifiedDate;
	private String cacheKey; // The cache identifier for the cache management
								// system

	// Primary getter and setters
	public void setId(String id) {
		this.id = id;
		setCacheKey(id);
	}

	public String getId() {
		return id;
	}

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * Save the current entity object and return DmlOperationWrapper with error
	 * If the current object is new, the save mode will be Create so that we
	 * will set system time stamp to the createdDatetime. If the current object
	 * is not new, the save mode will automatically update lastModifedDate to
	 * current system time.
	 * 
	 * @return
	 */
	public final DmlOperationWrapper save() {
		DMLEvents dmlType = DMLEvents.UPDATE;
		if (isNew()) {
			dmlType = DMLEvents.CREATE;
		}
		DmlOperationWrapper dmlResult = BulkEntityOperations.bulkSave(
				Collections.singletonList(this), dmlType);
		// TODO: how to use the return result
		saveRelatedInfoDuringUpdate();
		return dmlResult;
	}

	/**
	 * Load the whole entity information based on the given information. The
	 * major use case is fully load object information based on the entity id.
	 * If there are multiple objects get loaded, we will only return the first
	 * one.
	 * 
	 * @return
	 * @throws Exception
	 */
	public EntityObject load() throws Exception {
		List<EntityObject> reloads = BulkEntityOperations.bulkLoad(Collections
				.singletonList(this));
		// If there are multiple objects get loaded, we will only return the
		// first one.
		if (reloads != null && reloads.size() > 0) {
			return reloads.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Remove current object. Current remove will only remove the record from db
	 * if all the fields match. TODO: remove the object only by the id match
	 * 
	 * @return
	 */
	public boolean remove() {
		boolean removeResult = BulkEntityOperations.bulkRemove(Collections.singletonList(this));
		saveRelatedInfoDuringRemove();
		return removeResult;
	}

	/**
	 * Validations for the entity object. It includes two part 1. Pre-save: all
	 * the required fields check before save to db 2. DB-save: Validation for db
	 * save
	 * 
	 * @param dml
	 */
	public void saveHook_Validate(DmlValidationHandler dml) {
		// TODO: validate parent and child comment exists
		DBEntityObject dbObj = getDbObject();
		dbObj.saveHook_Validate(dml);

		DateTime now = new DateTime();
		if (dml.getDmlType() == DMLEvents.CREATE) {
			setCreatedDate(now);
			setLastModifiedDate(now);
		} else {
			if (!isRelatedInfoUpdate()) {
				// if it is update, the created date information should already
				// be there
				setLastModifiedDate(now);
			}
		}
	}

	/**
	 * Determine if current entity object is new
	 * 
	 * @return
	 */
	public boolean isNew() {
		return getId() == null || getId().isEmpty() || getCreatedDate() == null;
	}

	/**
	 * Get the db object information matched current entity object
	 * 
	 * @return
	 */
	public abstract DBEntityObject getDbObject();

	/**
	 * Reload all the entity field information from db object. No db access is
	 * required is for this step.
	 */
	public abstract void reloadAllFieldInformationFromDbObject(
			DBEntityObject dbObject);

	/**
	 * Get the db class for the current object
	 * 
	 * @return
	 */
	public abstract Class<?> getDbClass();

	/**
	 * Get the db table name
	 * 
	 * @return
	 */
	public abstract String getDbTableName();

	/**
	 * Cascade update the parent object information when the current object information is updated
	 * 
	 * @return
	 */
	public abstract DmlOperationWrapper saveRelatedInfoDuringUpdate();
	
	/**
	 * Cascade update the parent object information when the current object will be removed
	 * 
	 * @return
	 */
	public abstract DmlOperationWrapper saveRelatedInfoDuringRemove();

	/**
	 * Return true if parent object needs update
	 * 
	 * @return
	 */
	public abstract boolean isRelatedInfoUpdate();
}
