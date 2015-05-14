package com.iamhere.mongodb.entities;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

import com.iamhere.platform.func.DmlValidationHandler;

/**
 * DB representation of the entity object in mongodb. All the object needs
 * extends @ReflectionDBObject to have java and db mapping happen automatically
 * 
 * @author jassica
 *
 */
public abstract class DBEntityObject  {
	@Id
	private String id;
	private DateTime createdDate;
	private DateTime lastModifiedDate;

	// Primary getters and setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	 * Return the field information to a map so that mongodb can easily understand it
	 * @return
	 */
	public Map<String, Object> getFieldsAndValues() {
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", getId());
		return maps;
	}
	
	/**
	 * Db validation for the mongodb
	 * @param dml
	 */
	public abstract void saveHook_Validate(DmlValidationHandler dml);

	/**
	 * Return the mongo db collection name for the given entity object
	 * @return
	 */
	public abstract String getDbTableName();

	/**
	 * Return the string representation of the object
	 */
	public abstract String toString();
}