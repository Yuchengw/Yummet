package com.yummet.platform.adapters;

import com.yummet.cache.CacheManager;
import com.yummet.cache.RedisProvider;

/**
 * Provide System context information for the app and platform
 * @author jassica
 * @since 1
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
		return RedisProvider.getInstance();
	}
}
