package com.yummet.relationships;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yucheng
 * @version 1
 *
 */
public abstract class Relationship implements Serializable{
	private Date createdDate;
	
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	private Date lastModifiedDate;
}
