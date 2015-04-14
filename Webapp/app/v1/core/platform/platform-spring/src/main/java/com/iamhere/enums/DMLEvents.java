package com.iamhere.enums;

/**
 * Represent the DB events:
 * 1. Create: the record first save to db
 * 2. Save: the record update to db
 * 3. Remove: the record is removed from db
 * 
 * As the event does not need to save to DB, there is no api or db value required
 * @author jassica
 *
 */
public enum DMLEvents {
	CREATE, SAVE,  REMOVE;
	
	public String getValue() {
		switch (this) {
		case CREATE:
			return "create";
		case REMOVE:
			return "remove";
		case SAVE:
			return "save";
		} 
		throw new AssertionError();
	}
}
