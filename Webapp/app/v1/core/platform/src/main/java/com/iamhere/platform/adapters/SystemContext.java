package com.iamhere.platform.adapters;

import com.iamhere.cache.CacheManager;
import com.iamhere.cache.RedisDataSourceCacheManager;
import com.iamhere.cache.RedisProvider;

/**
 * Provide System context information for the app and platform
 * @author jassica
 * @version 1
 */
public class SystemContext {
	/**
	 * Get the db driver for the system
	 * @return
	 */
	public static DatabaseProvider getContext() {
		return new  MongoDbProvider();
	}
	
	/**
	 * Get the cache driver for the system
	 * @return
	 */
	public static CacheManager getCacheContext() {
		//return RedisProvider.getInstance();
		// try the pool connection settings
		return new RedisDataSourceCacheManager();
	}
}