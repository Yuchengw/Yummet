package com.yummet.api.config;

/**
 * @author yucheng
 * @since 1
 * */
public abstract class ApiVersion {
	
	double internalVersion;
	double apiVersion;
	String label;
	Integer year;
	
	public double getInternalVersion() {
		return internalVersion;
	}

	public double getApiVersion() {
		return apiVersion;
	}

	public String getLabel() {
		return label;
	}

	public Integer getYear() {
		return year;
	}
}
