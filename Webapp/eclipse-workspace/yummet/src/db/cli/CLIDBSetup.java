package db.cli;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * This is the DB initial class for setuping and connecting to MongoDb
 * @author yucheng
 * @version 1
 * */
public class CLIDBSetup {

	private static final String  MONGODB_DBNAME = "iamhere";
	private static final String  MONGODB_HOST = "127.0.0.1";
	private static final String  MONGODB_ADMIN = "amheredb";
	private static final String  MONDBODB_ADMIN_PASSWORD = "amhere1234";
	private static final String  USER_COLLECTION = "Users";
	private static final Integer MONGODB_PORT = 27017;
	
	
	public static void main(String[] args) throws Exception {
		initialMongoDb();
	}
	
	/**
	 * Create MongoDB
	 * */
	private static void initialMongoDb() {
		/**** Connect to MongoDB ****/
		MongoCredential yummetDBAuth = MongoCredential.createCredential(MONGODB_ADMIN, MONGODB_DBNAME, MONDBODB_ADMIN_PASSWORD.toCharArray());
		List<MongoCredential> auths = new ArrayList<MongoCredential>();
		auths.add(yummetDBAuth);
		
		ServerAddress serverAddress = new ServerAddress(MONGODB_HOST , MONGODB_PORT);
		// Since 2.10.0, uses MongoClient
		MongoClient mongo = new MongoClient(serverAddress);
		/**** Get database ****/
		// if database doesn't exists, MongoDB will create it for you
		MongoDatabase db = mongo.getDatabase(MONGODB_DBNAME);
		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		MongoCollection<Document> table = db.getCollection(USER_COLLECTION);
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email", "ycwmike@gmail.com");
		FindIterable<Document> cursor = table.find(whereQuery);
		while (cursor.iterator().hasNext()) {
			System.out.println(cursor.iterator().next());
			break;
		}
		// if there is no admin user found, create one
		try {
			createAdminUser(table);
		} catch (Exception e) {
			System.out.println("Error happened when creating Admin user " + e.getMessage());
		}
	}
	
	/**
	 * This is used for creating amin user
	 * */
	private static void createAdminUser(MongoCollection<Document> table) throws Exception{
		/**** Insert admin user ****/
		// create a document to store key and value
		Document document = new Document();
		document.put("email", "ycwmike@gmail.com");
		document.put("firstName", "Yucheng");
		document.put("lastName", "test1234");
		document.put("phone", "123223122");
		document.put("creditInfo", 1232321321);
		document.put("activeScore", Integer.MAX_VALUE);
		document.put("alias", "ycwmike");
		document.put("role", "admin");
		document.put("isEmailAuthorized", true); // hack here:)
		document.put("password", "1234");
		table.insertOne(document);
	}
}
