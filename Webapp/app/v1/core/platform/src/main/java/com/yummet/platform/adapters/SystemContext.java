package com.yummet.platform.adapters;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yummet.cache.CacheManager;
import com.yummet.cache.RedisDataSourceCacheManager;

/**
 * Provide System context information for the app and platform
 * @author jassica
 * @since 1
 */
public class SystemContext implements ApplicationContextAware {
	
	private static ApplicationContext ctx = null;

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
		return new RedisDataSourceCacheManager();
	}

	 public static ApplicationContext getApplicationContext() {
		 return ctx;
	 }
	 
	 public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		 this.ctx = ctx;
	 }
}