package com.iamhere.entities;

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
 *
 */
public abstract class EntityObject {
	private String id;
	private DateTime createdDate;
	private DateTime lastModifiedDate;

	// Primary getter and setters
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public DateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(DateTime createdDate) {
		this.createdDate = createdDate;
		getDbObject().setCreatedDate(createdDate.toDate());
	}

	public DateTime getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(DateTime lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
		getDbObject().setLastModifiedDate(lastModifiedDate.toDate());
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
		DMLEvents dmlType = DMLEvents.SAVE;
		if (isNew()) {
			dmlType = DMLEvents.CREATE;
		}
		return BulkEntityOperations.bulkSave(Collections.singletonList(this),
				dmlType);
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
		return BulkEntityOperations.bulkRemove(Collections.singletonList(this));
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
		getDbObject().saveHook_Validate(dml);

		if (dml.getDmlType() == DMLEvents.CREATE) {
			setCreatedDate(new DateTime());
			setLastModifiedDate(getCreatedDate());
		} else {
			setLastModifiedDate(new DateTime());
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
	public abstract void reloadAllFieldInformationFromDbObject();

}
