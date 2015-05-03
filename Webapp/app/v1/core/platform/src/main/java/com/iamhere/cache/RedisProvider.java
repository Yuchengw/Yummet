package com.iamhere.cache;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;

import com.iamhere.entities.EntityObject;
import com.iamhere.utilities.SerializeUtil;

/**
 * Implements the cache management for the Redis
 * @author Jessica
 * @version 1
 */
public class RedisProvider extends AbstractBaseRedisDao<String, EntityObject> implements CacheManager {
	
	@Autowired
	@Qualifier("jedisConnectionFactory")
	private JedisConnectionFactory jedisConnectionFactory;
	// singleton to initialize the service
	private static Jedis jedis;
	
	private RedisProvider() {
		
	}
	
	private static class RedisProviderSingletonProvider {
		private static final RedisProvider INSTANCE = new RedisProvider();
	}
	
	public static RedisProvider getInstance() {
		return RedisProviderSingletonProvider.INSTANCE;
	}

	public boolean insertRecords(final EntityObject[] records) throws Exception {
		Assert.notEmpty(records);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisStringSerializer();
				for (EntityObject record : records) {
					byte[] key = serializer.serialize(record.getCacheKey());
					byte[] value = SerializeUtil.serialize(record);
					connection.setNX(key, value);
				}
				return true;
			}
		}, false, true);
		return result;
	}

	public boolean insert(final EntityObject entity) throws Exception {
		return insertRecords(new EntityObject[] { entity });
	}

	public void removeRecords(EntityObject[] records) throws Exception {
		List<String> list = new ArrayList<String>();
		for (EntityObject record : records) {
			list.add(record.getCacheKey());
		}
		redisTemplate.delete(list);
	}

	public void delete(final EntityObject record) throws Exception {
		removeRecords(new EntityObject[] { record });
	}

	public boolean saveRecords(final EntityObject[] records) throws Exception {
		Assert.notEmpty(records);
		boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
				RedisSerializer<String> serializer = getRedisStringSerializer();
				for (EntityObject record : records) {
					byte[] key = serializer.serialize(record.getCacheKey());
					byte[] value = SerializeUtil.serialize(record);
					connection.set(key, value);
				}
				return true;
			}
		}, false, true);
		return result;
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
		EntityObject result = redisTemplate.execute(new RedisCallback<EntityObject>() {
					public EntityObject doInRedis(RedisConnection connection) throws DataAccessException {
						RedisSerializer<String> serializer = getRedisStringSerializer();
						byte[] key = serializer.serialize(record.getCacheKey());
						byte[] value = connection.get(key);
						if (value == null) {
							return null;
						}
						EntityObject cacheValue = SerializeUtil.deserialize(value);
						return cacheValue;
					}
				});
		return result;
	}

	public boolean exists(EntityObject record) throws Exception {
		EntityObject result = get(record);
		return result != null;
	}

	/**
     * Remove all redis data
     * @return
     */
    public String flushDB(){
        return this.getJedis().flushDB();
    }
	
	  /**
     * Get a jedis client
     * @return
     */
    private Jedis getJedis(){
        if(jedis == null){
            return jedisConnectionFactory.getShardInfo().createResource();
        }
        return jedis;
    }
}