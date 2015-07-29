package com.yummet.mongodb;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
/**
 * This is the java style configuration for Mongotemplate
 * @author Yucheng
 * @since 1
 * */
@Configuration
public class YummetMongoConfig {
 
	private static final String MONGODB_NAME = "iamhere";
	private static final String MONGODB_ADDRESS = "127.0.0.1";
	
	public @Bean MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(MONGODB_ADDRESS), MONGODB_NAME);
	}
 
	public @Bean MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}
}