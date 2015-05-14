package com.iamhere.platform.adapters;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.iamhere.cache.CacheManager;
import com.iamhere.cache.RedisDataSourceCacheManager;
import com.iamhere.cache.RedisProvider;

/**
 * Provide System context information for the app and platform
 * @author jassica
 * @version 1
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
	
//	/**
//	 * Get the db driver for the system
//	 * @return
//	 */
//	public static DatabaseProvider getContext() {
//		return new  MongoDbProvider();
//	}
//	
//	/**
//	 * Get the cache driver for the system
//	 * @return
//	 */
//	public static CacheManager getCacheContext() {
//		//return RedisProvider.getInstance();
//		// try the pool connection settings
//		return new RedisDataSourceCacheManager();
//	}
//
//	@Override
//	public void setApplicationContext(ApplicationContext arg0)
//			throws BeansException {
//		// TODO Auto-generated method stub
//		
//	}
}