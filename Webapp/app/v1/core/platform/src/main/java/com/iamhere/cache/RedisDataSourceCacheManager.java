package com.iamhere.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;

import com.iamhere.entities.EntityObject;
import com.iamhere.utilities.SerializeUtil;

/**
 * Implements the cache management for the Redis with multiple client pool supported
 * 
 * @author jassica
 *
 */
public class RedisDataSourceCacheManager implements CacheManager {
	@Autowired
	private static RedisClientTemplate redisClientTemplate = (RedisClientTemplate) new ClassPathXmlApplicationContext(
			"classpath:spring.xml").getBean("redisClientTemplate");
	
	private static Jedis jedis;
	@Autowired
	@Qualifier("jedisConnectionFactory")
	private JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory)new ClassPathXmlApplicationContext(
			"classpath:spring.xml").getBean("jedisConnectionFactory");
	
	/**
	 * Get a jedis client
	 * 
	 * @return
	 */
	private Jedis getJedis() {
		if (jedis == null) {
			return jedisConnectionFactory.getShardInfo().createResource();
		}
		return jedis;
	}
	
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

	public void removeRecords(EntityObject[] records) throws Exception {
		for (EntityObject record : records) {
			redisClientTemplate.del(record.getCacheKey());
		}
	}

	public void delete(final EntityObject record) throws Exception {
		removeRecords(new EntityObject[] { record });
	}

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
			byte[] value = redisClientTemplate.get(record.getCacheKey()
					.getBytes());
			if (value == null) {
				return null;
			}
			EntityObject cacheValue = SerializeUtil.deserialize(value);
			return cacheValue;
		} catch (Exception e) {
			return null;
		}
	}

	public boolean exists(EntityObject record) throws Exception {
		return redisClientTemplate.exists(record.getCacheKey().getBytes());
	}

	/**
	 * Remove all redis data
	 * 
	 * @return
	 */
	public String flushDB() {
		return this.getJedis().flushDB();
	}

}