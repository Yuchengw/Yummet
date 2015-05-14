package com.iamhere.enums;

public enum TimeUnit {
		MINUTES("minute", "m"), 
		HOURs("hour", "h"), 
		DAYS("day", "d"), 
		MONTHS("month", "t"), 
		YEARS ("year", "y");
		private String apiValue;
		private String dbValue;
		
		private TimeUnit(String apiValue, String dbValue) {
			this.apiValue = apiValue;
			this.dbValue = dbValue;
		}		
		
		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			sb.append("Time Unit");
			sb.append("{apiValue = ").append(apiValue);
			sb.append(", dbValue = ").append(dbValue);
			sb.append("}");
			return sb.toString();
		}
}
