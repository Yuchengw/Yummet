package com.yummet.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.enums.PostVisibilityEnum;

/**
 * Represents the request posts in the application. It is used by those who want
 * to ask for items.
 * 
 * @author jassica
 *
 */
@Document(collection = "RequestPosts")
public class RequestPostObject extends PostObject {
	private static final long serialVersionUID = -6011241820070393925L;

	public RequestPostObject() {
	}

	public RequestPostObject(String id) {
		super(id);
	}

	public RequestPostObject(UserObject creator) {
		setCreator(creator);
		setPostCategory("Food");
		// Post visibility by default is Public
		setVisibility(PostVisibilityEnum.PUBLIC);
	}
	
	public RequestPostObject(UserObject creator, String subject,
			String location, int quantity) {
		super(creator, subject, location, quantity);
	}

	@Override
	public String getDbTableName() {
		return "RequestPosts";
	}
}
