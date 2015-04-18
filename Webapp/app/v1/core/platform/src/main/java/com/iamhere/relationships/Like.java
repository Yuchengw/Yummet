package com.iamhere.relationships;

import com.iamhere.entities.UserObject;
import com.iamhere.trashbin.Entity;

public class Like extends Entity {
	private UserObject whoLikes;
	private Object likesWhom;

	// TODO: In the first version, likesWhom will just be AbstractPost.
	// It can be post Comment in the future
	public Like(UserObject creator, Object target) {
		this.whoLikes = creator;
		this.likesWhom = target;
	}

	public UserObject getWhoLikes() {
		return whoLikes;
	}

	public void setWhoLikes(UserObject whoLikes) {
		this.whoLikes = whoLikes;
	}

	public Object getLikesWhom() {
		return likesWhom;
	}

	public void setLikesWhom(Object likesWhom) {
		this.likesWhom = likesWhom;
	}
}
