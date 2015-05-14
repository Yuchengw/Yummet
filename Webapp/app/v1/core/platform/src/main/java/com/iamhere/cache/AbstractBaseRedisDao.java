package com.iamhere.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 
 * @author jassica
 *
 * @param <K>
 * @param <V>
 * 
 * @version 1
 */
public abstract class AbstractBaseRedisDao<K, V> {  
     
   @Autowired  
   protected RedisTemplate<K, V> redisTemplate;
	
   /** 
    * Set up redisTemplate 
    * @param redisTemplate the redisTemplate to set 
    */  
   public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {  
       this.redisTemplate = redisTemplate;  
   }  
   
   public RedisTemplate<K, V>  getRedisTemplate() {  
	   return redisTemplate;
   }  
     
   /** 
    * Get RedisSerializer 
    */  
   protected RedisSerializer<String> getRedisStringSerializer() {  
       return redisTemplate.getStringSerializer();  
   }  
}  