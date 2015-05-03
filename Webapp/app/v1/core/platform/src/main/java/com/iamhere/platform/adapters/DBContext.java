package com.iamhere.platform.adapters;

/**
 * Provide DB context information for the app and platform
 * @author jassica
 * @version 1
 */
public class DBContext {
	public static DatabaseProvider getContext() {
		return new  MongoDbProvider();
	}
}
