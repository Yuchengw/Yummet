package com.iamhere.cache;

import redis.clients.jedis.ShardedJedis;

/**
 * Interface for the shared redis management
 * @author jassica
 * @version 1
 */
public interface RedisDataSource {
	public abstract ShardedJedis getRedisClient();
    public void returnResource(ShardedJedis shardedJedis);
    public void returnResource(ShardedJedis shardedJedis,boolean broken);
}
