package com.yummet.relationships;

import com.yummet.entities.UserObject;

/**
 * @author Jessica
 * @version 1
 * */
public class Like extends Relationship {
	private UserObject whoLikes;
	private Object likesWhom;

	// TODO: In the first version, likesWhom will just be AbstractPost.
	// It can be post Comment in the future
	public Like(UserObject creator, Object target) {
		this.whoLikes = creator;
		this.likesWhom = target;
	}

	public UserObject getWhoLikes() {
		return this.whoLikes;
	}

	public void setWhoLikes(UserObject whoLikes) {
		this.whoLikes = whoLikes;
	}

	public Object getLikesWhom() {
		return this.likesWhom;
	}

	public void setLikesWhom(Object likesWhom) {
		this.likesWhom = likesWhom;
	}
}
