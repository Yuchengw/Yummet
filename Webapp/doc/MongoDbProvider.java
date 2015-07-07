package com.iamhere.platform.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.iamhere.entities.EntityObject;
import com.iamhere.entities.PostComment;
import com.iamhere.entities.PostObject;
import com.iamhere.entities.UserObject;
import com.iamhere.mongodb.entities.DBEntityObject;
import com.iamhere.mongodb.entities.DBPostComment;
import com.iamhere.mongodb.entities.DBPostObject;
import com.iamhere.mongodb.entities.DBUserObject;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;

public class MongoDbProvider implements DatabaseProvider {
	MongoDatabase database;
	DB db;
	final static String MONGO_DB_NAME = "iamhere";//"amher";
	final static String MONGO_USERNAME = "amheredb";//"MongoAdmin";
	final static String MONGO_PASSWORD = "amhere1234";//"Iamhere";
	final static String MONGO_ROLE = "admin";
	Mongo m = null;

	@SuppressWarnings("deprecation")
	public MongoDbProvider() {
		try {
			@SuppressWarnings("resource")
			MongoClient mongoClient = new MongoClient(new MongoClientURI(
					"mongodb://" + MONGO_USERNAME + ":" + MONGO_PASSWORD
							+ "@localhost/" + MONGO_DB_NAME));//MONGO_ROLE));
			database = mongoClient.getDatabase(MONGO_DB_NAME);
			db = mongoClient.getDB(MONGO_DB_NAME);
		} catch (MongoException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Object getInstance() {
		assert (database != null);
		return database;
	}

	@Override
	public List<EntityObject> getAllTableRecords(String tableName,
			EntityObject info) {
		List<EntityObject> records = new ArrayList<EntityObject>();
		// MongoCollection<Document> collection = database
		// .getCollection(tableName);
		// MongoCursor<Document> cursor = collection.find().iterator();
		// try {
		// while (cursor.hasNext()) {
		// records.add(cursor.next());
		// }
		// } finally {
		// cursor.close();
		// return records.toArray();
		// }
		DBCollection collection = db.getCollection(tableName);
		collection.setObjectClass(info.getDbObject().getClass());
		DBCursor cursor = collection.find();
		while (cursor.hasNext()) {
			records.add((EntityObject) cursor.next());
		}
		cursor.close();
		return records;
	}

	@Override
	public List<EntityObject> getRecordsBasedOnQuery(String tableName,
			EntityObject info, Map<String, Object> queryInfo) {
		List<EntityObject> records = new ArrayList<EntityObject>();
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.putAll(queryInfo);
		DBCollection collection = db.getCollection(tableName);
		collection.setObjectClass(info.getDbObject().getClass());
		DBCursor cursor = collection.find(searchQuery);

		try {
			while (cursor.hasNext()) {
				EntityObject obj = null;
				DBEntityObject dbObject = (DBEntityObject) cursor.next();
				if ( dbObject instanceof DBUserObject) {
					DBUserObject dbUser = (DBUserObject) dbObject;
					obj = new UserObject(dbUser);
				} else if(dbObject instanceof DBPostObject) {
					DBPostObject dbPost = (DBPostObject) dbObject;
					obj = new PostObject(dbPost);
				} else if (dbObject instanceof DBPostComment) {
					DBPostComment dbComment = (DBPostComment) dbObject;
					obj = new PostComment(dbComment);
				}
				records.add(obj);
			}
		} finally {
			cursor.close();
		}
		return records;
	}

	@Override
	public void saveRecords(String tableName, EntityObject[] records) {
		if (records == null) {
			return;
		}
		DBCollection collection = db.getCollection(tableName);
		for (EntityObject record : records) {
			collection.save(record.getDbObject());
			// TODO: how to make bulk happen
		}

		assert (collection.count() == records.length);

	}

	@Override
	public void insertRecords(String tableName, EntityObject[] records)
			throws Exception {
		if (records == null) {
			return;
		}
		// List<Document> documents = new ArrayList<Document>();
		// for (int i = 0; i < records.length; i++) {
		// Entity record = records[i];
		// Map<String, Serializable> values = record.getFieldsAndValues();
		// Document doc = new Document();
		// doc.putAll(values);
		// // for (Map.Entry<String, Serializable> entry : values.entrySet()) {
		// // doc.append(entry.getKey(), entry.getValue());
		// // }
		// documents.add(doc);
		// }
		// MongoCollection<Document> collection =
		// database.getCollection(tableName);
		// collection.insertMany(documents);
		DBCollection collection = db.getCollection(tableName);
		DBEntityObject[] dbObjects = new DBEntityObject[records.length];
		for (int i = 0; i < dbObjects.length; i++) {
			dbObjects[i] = records[i].getDbObject();
		}
		collection.insert(dbObjects, WriteConcern.SAFE);

		assert (collection.count() == records.length);
	}

	@Override
	public void removeRecords(String tableName, EntityObject[] records)
			throws Exception {
		if (records == null) {
			return;
		}
		DBCollection collection = db.getCollection(tableName);
		for (EntityObject record : records) {
			if (record.getId() != null) {
				collection.remove(new BasicDBObject().append("_id", record.getDbObject().get_id()));
			} else {
				collection.remove(record.getDbObject());
			}
		}
		// MongoCollection collection = database.getCollection(tableName);
		// Document query = new Document();
		// List list = new ArrayList();
		// for (int i = 0; i < records.length; i++) {
		// Entity record = records[i];
		// Map<String, Serializable> values = record.getFieldsAndValues();
		// list.add(values.get("email"));
		// }
		// query.put("email", new Document("$in", list));
		// DeleteResult result = collection.deleteMany(query);
		// assert (result.getDeletedCount() == records.length);
	}
}
