package com.iamhere.relationships;

import com.iamhere.entities.UserObject;
import com.iamhere.trashbin.Entity;

public class Follow extends Entity {
	private UserObject creator;
	private Object target;
	
	public Follow(UserObject creator, UserObject target) {
		this.creator = creator;
		this.target = target;
	}

	public UserObject getCreator() {
		return creator;
	}

	public void setCreator(UserObject creator) {
		this.creator = creator;
	}

	public Object getTarget() {
		return target;
	}

	public void setTarget(Object target) {
		this.target = target;
	}
	
}
