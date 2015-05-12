package com.iamhere.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iamhere.utilities.LogUtil;

import ch.qos.logback.classic.Logger;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 
 * @author jassica
 * @version 1
 */
@Repository("redisDataSource")
public class RedisDataSourceImpl implements RedisDataSource {
    private static final Logger log = LogUtil.getInstance(RedisDataSourceImpl.class);

    @Autowired
    private ShardedJedisPool shardedJedisPool;

    @Override
    public ShardedJedis getRedisClient() {
        try {
            ShardedJedis shardJedis = shardedJedisPool.getResource();
            return shardJedis;
        } catch (Exception e) {
            log.error("getRedisClent error", e);
        }
        return null;
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis) {
        shardedJedisPool.returnResource(shardedJedis);
    }

    @Override
    public void returnResource(ShardedJedis shardedJedis, boolean broken) {
        if (broken) {
            shardedJedisPool.returnBrokenResource(shardedJedis);
        } else {
            shardedJedisPool.returnResource(shardedJedis);
        }
    }
}
