package com.yummet.cache;

import redis.clients.jedis.ShardedJedis;

/**
 * @author yucheng
 * @version 1
 * */
public interface RedisDataSource {
	public abstract ShardedJedis getRedisClient();
    public void returnResource(ShardedJedis shardedJedis);
    public void returnResource(ShardedJedis shardedJedis,boolean broken);
}