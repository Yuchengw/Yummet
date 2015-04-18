package com.iamhere.enums;

/**
 * Represents the order status for all the orders:
 * 1. Open: The order is just created and bargain is not done 
 * 2. InProgress: The order is in bargain progress
 * 3. Close: The order bargain is done and the rate is done also
 * 4. Removed: The order is cancel by either Jia or Yi
 * 5. UderShip: The order bargain is done and the order is on the way
 * @author jassica
 *
 */
public enum OrderStatus {
	OPEN("Open", "o"), UNDERSHIP("UnderShip", "u"), CLOSE("Close", "c"), INPROGRESS("InProgress", "i"), REMOVED("Remove", "r");

	private String apiValue;
	private String dbValue;

	private OrderStatus(String apiValue, String dbValue) {
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

	public static OrderStatus fromDbValue(String dbValue) {
		switch (dbValue) {
		case "o":
			return OPEN;
		case "c":
			return CLOSE;
		case "i":
			return INPROGRESS;
		case "r":
			return REMOVED;
		case "u":
			return UNDERSHIP;
		}
		return null;
	}
}
