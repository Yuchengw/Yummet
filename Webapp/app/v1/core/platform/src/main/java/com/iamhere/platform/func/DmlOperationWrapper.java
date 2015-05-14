package com.iamhere.platform.func;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.iamhere.entities.EntityObject;
import com.iamhere.enums.DMLEvents;
import com.iamhere.utilities.LogUtil;

/**
 * Hold DML related information:
 * 1. Validation for each entityObject
 * 2. Return the valid entiities from the input
 * @author jassica
 *
 */
public class DmlOperationWrapper {

	private static final Logger logger = LogUtil.getInstance(DmlOperationWrapper.class);
	
	private final List<EntityObject> originalEntityObjects;
	private final Map<EntityObject, DmlValidationHandler> validationHandlers;
	private boolean hasError = false;
	// private final DMLEvents dmlType;
	
	/**
	 * 
	 * */
	public DmlOperationWrapper(Collection<? extends EntityObject> entityObjects, DMLEvents dmlType) {
		this.originalEntityObjects = ImmutableList.copyOf(new ArrayList<EntityObject>(entityObjects));
		this.validationHandlers = new HashMap<EntityObject, DmlValidationHandler>(entityObjects.size() << 1);
		for (EntityObject eo : entityObjects) {
			DmlValidationHandler handler = new DmlValidationHandler(eo, dmlType);
			this.validationHandlers.put(eo, handler);
		}
		// this.dmlType = dmlType;
	}

	/**
	 * 
	 * */
	public List<EntityObject> getEntityObjectsWithoutError() {
		List<EntityObject> validEntities = new ArrayList<EntityObject>(this.originalEntityObjects.size());
		for (EntityObject eo : this.originalEntityObjects) {
			DmlValidationHandler handler = this.validationHandlers.get(eo);
			handler.validate();
			if (handler.isCurrenctlyValid()) {
				validEntities.add(eo);
				logger.debug("Save validation pass for record ==> " + eo);
			} else {
				hasError = true;
				logger.debug("Save validation with error message  for record ==> " + handler.getErrorMessages());
			}
		}
		return ImmutableList.copyOf(validEntities);
	}

	/**
	 * */
	public boolean isBulkSuccess() {
		return !hasError;
	}

	/**
	 * */
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
