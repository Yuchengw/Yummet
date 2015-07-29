package com.yummet.api.config;

/**
 * @author yucheng
 * @since 1
 * */
public enum ApiFamily {
	Rest;
	
	public boolean isRestApi() {
		return this == ApiFamily.Rest;
	}
}
