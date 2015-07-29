package com.yummet.api.config;

import java.io.Serializable;

/**
 * @author yucheng
 * @since 1
 * */
public class PlatformApiVersion extends ApiVersion implements Serializable{
	
	public static final PlatformApiVersion CURRENT;
	public static final PlatformApiVersion[] allActiveVersions; 
	
	public static final PlatformApiVersion VERSION_1 = new PlatformApiVersion(1, 1.0, "v1", 2015);
	
	static {
		allActiveVersions = new PlatformApiVersion[] {VERSION_1};
		CURRENT = versionForInternal(ApiConstants.CURRENT_API_VERSION);
	}
	
	
	private PlatformApiVersion(double internalVersion, double apiVersion) {
		this(internalVersion, apiVersion, null, null);
	}
	
	private PlatformApiVersion(double internalVersion, double apiVersion, String label, Integer year) {
		this.internalVersion = internalVersion;
		this.apiVersion = apiVersion;
		this.label = label;
		this.year = year;
	}
	
	
	private static PlatformApiVersion versionForInternal(Double internalVersion) {
		if (internalVersion != null) {
			for (ApiVersion v : allActiveVersions) {
				if (v.getInternalVersion() == internalVersion) {
					return (PlatformApiVersion)v;
				}
			}
		}
		return null;
	}
}
