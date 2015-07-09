package com.yummet.enums;

/**
 * Represents the order status for all the posts:
 * 1. Open: The post is just created and food is available 
 * 2. Close: All the food has gone and creator close it
 * 3. Expire: The food mentioned in the post is expired
 * 4. Removed: The post has canceled by the creator
 * @author jassica
 *
 */
public enum PostStatus {
	OPEN("Open", "o"), CLOSE("Close", "c"), EXPIRE("Expire", "e"), REMOVED(
			"Removed", "r");

	private String apiValue;
	private String dbValue;

	private PostStatus(String apiValue, String dbValue) {
		this.apiValue = apiValue;
		this.dbValue = dbValue;
	}

	public String getDbValue() {
		return this.dbValue;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append("Post Status");
		sb.append("{apiValue = ").append(apiValue);
		sb.append(", dbValue = ").append(dbValue);
		sb.append("}");
		return sb.toString();
	}

	public static PostStatus fromDbValue(String dbValue) {
		switch (dbValue) {
		case "o":
			return OPEN;
		case "c":
			return CLOSE;
		case "e":
			return EXPIRE;
		case "r":
			return REMOVED;
		}
		return null;
	}
}
