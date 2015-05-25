package com.iamhere.platform.adapters;

/**
 * Provide DB context information for the app and platform
 * @author jassica
 *
 */
public class DBContext {
	public static DatabaseProvider getContext() {
		return new  MongoDbProvider();
	}
}
