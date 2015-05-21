package com.yummet.business.bean;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;

/**
 * @author yucheng
 * @since 1
 * */
public abstract class BeanObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 149762661465164766L;
	
	private String id;
	private Date createdDate;
	private Date lastModifiedDate;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
    @JsonSerialize(using=DateSerializer.class)
	public Date getCreatedDate() {
		return createdDate;
	}
    
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
    @JsonSerialize(using=DateSerializer.class)
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
    
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
}
