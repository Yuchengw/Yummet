package com.iamhere.platform.func;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.iamhere.entities.EntityObject;
import com.iamhere.enums.DMLEvents;
import com.iamhere.platform.adapters.MongoDbProvider;
import com.iamhere.utilities.LogUtil;

/**
 * Hold DML related information:
 * 1. Validation for each entityObject
 * 2. Return the valid entiities from the input
 * @author jassica
 *
 */
public class DmlOperationWrapper {

	private final List<EntityObject> originalEntityObjects;
	private final Map<EntityObject, DmlValidationHandler> validationHandlers;
	private boolean hasError = false;
	// private final DMLEvents dmlType;
	
	public DmlOperationWrapper(
			Collection<? extends EntityObject> entityObjects, DMLEvents dmlType) {
		this.originalEntityObjects = Collections
				.unmodifiableList(new ArrayList<EntityObject>(entityObjects));
		this.validationHandlers = new HashMap<EntityObject, DmlValidationHandler>(
				entityObjects.size() << 1);
		for (EntityObject eo : entityObjects) {
			DmlValidationHandler handler = new DmlValidationHandler(eo, dmlType);
			this.validationHandlers.put(eo, handler);
		}
		// this.dmlType = dmlType;
	}

	public List<EntityObject> getEntityObjectsWithoutError() {
		List<EntityObject> validEntities = new ArrayList<EntityObject>(
				this.originalEntityObjects.size());
		for (EntityObject eo : this.originalEntityObjects) {
			DmlValidationHandler handler = this.validationHandlers.get(eo);
			handler.validate();
			if (handler.isCurrenctlyValid()) {
				validEntities.add(eo);
				LogUtil.getInstance(DmlOperationWrapper.class).debug("Save validation pass for record ==> " + eo);
			} else {
				hasError = true;
				LogUtil.getInstance(DmlOperationWrapper.class).debug("Save validation with error message  for record ==> " + handler.getErrorMessages());
			}
		}
		return Collections.unmodifiableList(validEntities);
	}

	public boolean isBulkSuccess() {
		return !hasError;
	}

	public boolean isDmlSuccessOnEntity(EntityObject eo) {
		DmlValidationHandler handler = this.validationHandlers.get(eo);
		return handler.isCurrenctlyValid();
	}

	public void addErrorToDmlOperation(EntityObject eo, String errorMessage) {
		DmlValidationHandler handler = this.validationHandlers.get(eo);
		handler.addError(errorMessage);
		hasError = true;
	}
}
