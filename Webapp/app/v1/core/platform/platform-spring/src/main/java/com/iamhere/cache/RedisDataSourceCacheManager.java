package com.iamhere.cache;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Assert;

import com.iamhere.entities.EntityObject;
import com.iamhere.utilities.SerializeUtil;

/**
 * Implements the cache management for the Redis
 * 
 * @author jassica
 *
 */
public class RedisDataSourceCacheManager implements CacheManager {
		@Autowired  
		private static RedisClientTemplate redisClientTemplate = (RedisClientTemplate) new ClassPathXmlApplicationContext("classpath:spring.xml").getBean("redisClientTemplate");
		
//	   /** 
//	    * Set up redisTemplate 
//	    * @param redisTemplate the redisTemplate to set 
//	    */  
//	   public void setRedisTemplate(RedisClientTemplate redisTemplate) {  
//	       this.redisTemplate = redisTemplate;  
//	   }  
//	   
//	   public RedisClientTemplate  getRedisTemplate() {  
//		   return redisTemplate;
//	   }  
	     
//	   /** 
//	    * Get RedisSerializer 
//	    */  
//	   protected RedisSerializer<String> getRedisStringSerializer() {  
//	       return redisTemplate.getStringSerializer();  
//	   }  
	   
	// Auto threadsafe singleton to initialize the service
//	private static final RedisDataSourceCacheManager service = (RedisDataSourceCacheManager) new ClassPathXmlApplicationContext(
//			"classpath:spring.xml").getBean("redisClientTemplate");

//	public static RedisDataSourceCacheManager getInstance() {
//		return service;
//	}

	@Override
	public boolean insertRecords(EntityObject[] records) throws Exception {
		Assert.notEmpty(records);
		try {
			for (EntityObject record : records) {
				byte[] key = record.getCacheKey().getBytes();
				byte[] value = SerializeUtil.serialize(record);
				redisClientTemplate.set(key, value);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean insert(final EntityObject entity) throws Exception {
		return insertRecords(new EntityObject[] { entity });
	}

	@Override
	public void removeRecords(EntityObject[] records) throws Exception {
		for (EntityObject record : records) {
			redisClientTemplate.del(record.getCacheKey());
		}
	}

	public void delete(final EntityObject record) throws Exception {
		removeRecords(new EntityObject[] { record });
	}

	@Override
	public boolean saveRecords(EntityObject[] records) throws Exception {
		Assert.notEmpty(records);
		try {
			for (EntityObject record : records) {
				byte[] key = record.getCacheKey().getBytes();
				byte[] value = SerializeUtil.serialize(record);
				redisClientTemplate.set(key, value);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean update(final EntityObject record) throws Exception {
		if (get(record) == null) {
			return insert(record);
		}
		return saveRecords(new EntityObject[] { record });
	}

	/**
	 * @param keyId
	 * @return
	 */
	public EntityObject get(final EntityObject record) {
		try {
				byte[] value = redisClientTemplate.get(record.getCacheKey().getBytes());
				if (value == null) {
					return null;
				}
				EntityObject cacheValue = SerializeUtil
						.deserialize(value);
				return cacheValue;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean exists(EntityObject record) throws Exception {
		return redisClientTemplate.exists(record.getCacheKey().getBytes());
	}

}
