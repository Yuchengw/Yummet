package com.yummet.cache;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Jedis;

/**
 * Encapsulate the Redis Method under platform service
 * @author jassica
 *
 */
@Deprecated
public class RedisService {

    /**
     * Remove the cache value from byte key
     * @param key
     */
    public void del(byte [] key){
        this.getJedis().del(key);
    }
    
    /**
     * Remove the cache value from string key
     * @param key
     */
    public void del(String key){
        this.getJedis().del(key);
    }

    /**
     * Add byte key value pair and set the cache expire time
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(byte [] key,byte [] value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
    
    /**
     * Add string key and value and set the cache expire time
     * @param key
     * @param value
     * @param liveTime
     */
    public void set(String key,String value,int liveTime){
        this.set(key, value);
        this.getJedis().expire(key, liveTime);
    }
    
    /**
     * Add String key and value where expire time leaves default
     * @param key
     * @param value
     */
    public void set(String key,String value){
        this.getJedis().set(key, value);
    }
    
    /**
     * Add byte key and value where expire time leaves default
     * @param key
     * @param value
     */
    public void set(byte [] key,byte [] value){
        this.getJedis().set(key, value);
    }
    
    /**
     * Get redis value (String)
     * @param key
     * @return
     */
    public String get(String key){
        String value = this.getJedis().get(key);
        return value;
    }
    
    /**
     * Get redis value (byte [] )(反序列化)
     * @param key
     * @return
     */
    public byte[] get(byte [] key){
        return this.getJedis().get(key);
    }

    /**
     * Find keys through regex pattern match
     * @param pattern
     * @return
     */
    public Set<String> keys(String pattern){
        return this.getJedis().keys(pattern);
    }

    /**
     * Check if the key already exists
     * @param key
     * @return
     */
    public boolean exists(String key){
        return this.getJedis().exists(key);
    }
    
    /**
     * Remove all redis data
     * @return
     */
    public String flushDB(){
        return this.getJedis().flushDB();
    }
    
    /**
     * Get the general information for the redis
     */
    public long dbSize(){
        return this.getJedis().dbSize();
    }
    
    /**
     * Check if Redis connection is successful
     * @return
     */
    public String ping(){
        return this.getJedis().ping();
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
    private RedisService (){

    }

    private static Jedis jedis;
    @Autowired
    @Qualifier("jedisConnectionFactory")
    private JedisConnectionFactory jedisConnectionFactory;
}
