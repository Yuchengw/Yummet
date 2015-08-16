package com.yummet.enums;

import com.google.gson.annotations.SerializedName;

/**
 * Represents the visibility for a certain post:
 * 1. Public: Everyone can see the post
 * 2. Private: Only a certain group of user can see the post
 * @author jassica
 *
 */
public enum PostVisibilityEnum {
	@SerializedName("public")
	PUBLIC("Public", "p"), 
	@SerializedName("private")
	PRIVATE("Private", "r");

	private String apiValue;
	private String dbValue;

	private PostVisibilityEnum(String apiValue, String dbValue) {
		this.apiValue = apiValue;
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return this.dbValue;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Post visibility");
		sb.append("{apiValue = ").append(apiValue);
		sb.append(", dbValue = ").append(dbValue);
		sb.append("}");
		return sb.toString();
	}

	public static PostVisibilityEnum fromDbValue(String dbValue) {
		switch (dbValue) {
		case "p":
			return PUBLIC;
		case "r":
			return PRIVATE;
		}
		return null;
	}
}
