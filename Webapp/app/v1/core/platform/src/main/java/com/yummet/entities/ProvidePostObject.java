package com.yummet.entities;

import org.springframework.data.mongodb.core.mapping.Document;

import com.yummet.enums.PostVisibilityEnum;

/**
 * Represents the provide post in the application. It is used by those who want
 * to share items to others.
 * 
 * @author jassica
 *
 */
@Document(collection = "ProvidePosts")
public class ProvidePostObject extends PostObject {
	private static final long serialVersionUID = -6011241820070393915L;

	public ProvidePostObject() {
	}

	public ProvidePostObject(String id) {
		super(id);
	}

	public ProvidePostObject(UserObject creator, String subject,
			String location, int quantity) {
		super(creator, subject, location, quantity);
	}

	public ProvidePostObject(UserObject creator) {
		setCreator(creator);
		setPostCategory("Food");
		// Post visibility by default is Public
		setVisibility(PostVisibilityEnum.PUBLIC);
	}

	@Override
	public String getDbTableName() {
		return "ProvidePosts";
	}
}
