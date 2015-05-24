package com.yummet.platform.func;

import java.util.ArrayList;
import java.util.List;

import com.yummet.entities.EntityObject;
import com.yummet.enums.DMLEvents;

/**
 * DML event wrapper to hold DML event type information and error message
 * @author jassica
 * @since 1
 */
public class DmlValidationHandler {
	
	private List<String> errorMessages;
	private final EntityObject entityObject;
	private final DMLEvents dmlType;
	public DmlValidationHandler(EntityObject o, DMLEvents dmlType) {
		this.entityObject = o;
		this.errorMessages = new ArrayList<String>();
		this.dmlType = dmlType;
	}
	
	/**
	 * Call the entity object information to validate 
	 */
	public void validate() {
		entityObject.saveHook_Validate(this);
	}
	
	/**
	 * Add error message to the DML event
	 * @param s
	 */
	public void addError(String s) {
		this.errorMessages.add(s);
	}
	
	/**
	 * The current object is valid if there is no error message
	 * @return
	 */
	public boolean isCurrenctlyValid() {
		return this.errorMessages.isEmpty();
	}

	/**
	 * Get current DML event type
	 * @return
	 */
	public DMLEvents getDmlType() {
		return dmlType;
	}
	
	/**
	 * Flat the error message and return it
	 * @return error message
	 */
	public String getErrorMessages() {
		StringBuilder toStringMessage = new StringBuilder();
		for (String error: errorMessages) {
			toStringMessage.append(error);
		}
		return toStringMessage.toString();
	}
	
}
