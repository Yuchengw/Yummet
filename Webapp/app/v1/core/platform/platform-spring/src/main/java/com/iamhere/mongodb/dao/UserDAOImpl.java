package com.iamhere.mongodb.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.iamhere.mongodb.entities.DBUserObject;
import com.mongodb.WriteResult;

@Deprecated
@Repository("userDaoImpl")
public class UserDAOImpl implements UserDAO {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public void insert(DBUserObject object, String collectionName) {
		mongoTemplate.insert(object, collectionName);
	}

	@Override
	public DBUserObject findOne(Map<String, Object> params,
			String collectionName) {
		return mongoTemplate.findOne(
				new Query(Criteria.where("email").is(params.get("email"))),
				DBUserObject.class, collectionName);
	}

	@Override
	public List<DBUserObject> findAll(Map<String, Object> params,
			String collectionName) {
		Criteria criteria = null;
		for (String key: params.keySet()) {
			if (criteria == null) {
				criteria = Criteria.where(key).is(params.get(key));
			}
			else {
				criteria = criteria.and(key).is(params.get(key));
			}
		}
		List<DBUserObject> result = mongoTemplate.find(new Query(criteria), DBUserObject.class,
				collectionName);
		return result;
	}

	@Override
	public void update(Map<String, Object> params, String collectionName) {
		Criteria criteria = null;
		for (String key: params.keySet()) {
			if (criteria == null) {
				criteria = Criteria.where(key).is(params.get(key));
			}
			else {
				criteria = criteria.and(key).is(params.get(key));
			}
		}
		mongoTemplate.upsert(new Query(criteria), new Update().set("name",
				params.get("name")), DBUserObject.class, collectionName);
	}

	@Override
	public void createCollection(String name) {
		mongoTemplate.createCollection(name);
	}

	@Override
	public void remove(Map<String, Object> params, String collectionName) {
		mongoTemplate.remove(new Query(Criteria.where("email")
				.is(params.get("email"))), DBUserObject.class, collectionName);
	}
	// private MongoOperations mongoOps;
	//
	// public UserDAOImpl(MongoOperations mongoOps){
	// this.mongoOps=mongoOps;
	// }
	//
	// public void create(DBUserObject p) {
	// this.mongoOps.insert(p, p.getDbTableName());
	// }
	//
	// public DBUserObject readByEmail(String email) {
	// Query query = new Query(Criteria.where("_id").is(email));
	// return this.mongoOps.findOne(query, DBUserObject.class, "Users" );
	// }
	//
	// public void update(DBUserObject p) {
	// this.mongoOps.save(p, p.getDbTableName());
	// }
	//
	// public int deleteByEmail(String email) {
	// Query query = new Query(Criteria.where("_id").is(email));
	// WriteResult result = this.mongoOps.remove(query, DBUserObject.class,
	// "Users");
	// return result.getN();
	// }
}
