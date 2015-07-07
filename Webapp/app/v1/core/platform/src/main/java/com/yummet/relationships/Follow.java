package com.yummet.relationships;

import com.yummet.entities.UserObject;

/**
 * @author jessica
 * @version 1
 * */
public class Follow extends Relationship {
	private UserObject creator;
	private Object target;
	
	public Follow(UserObject creator, UserObject target) {
		this.creator = creator;
		this.target = target;
	}

	public UserObject getCreator() {
		return this.creator;
	}

	public void setCreator(UserObject creator) {
		this.creator = creator;
	}

	public Object getTarget() {
		return this.target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
}