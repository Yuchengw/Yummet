package my.example.rest;

import java.util.List;
import java.util.regex.Pattern;

import org.json.JSONArray;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mongodb.*;
import com.mongodb.util.JSON;

public class DBUserService {
	// TODO: make it configurable: connection pool? 
	// TODO: how about distribute to to replicates so we can scale?
	final static String dbServer="localhost";
	final static int dbPort=27017;
	final static String dbName="Yummet1";
	final static String dbUserId="appUser";
	final static String dbUserPasswd="appPwd";	

	private MongoClient dbCli;
	private DB userDB;
	
	public static DBUserService dbUserService = new DBUserService();

	public static DBUserService getInstance() {
		// TODO Auto-generated method stub
		return dbUserService;
	}

	//TODO: make it singleton? how?
	public DBUserService(){
		//dbCli=new MongoClient(new ServerAddress(dbServer),
		//		MongoClientOptions.builder().build());
		dbCli=new MongoClient(new ServerAddress(dbServer));
		//authenticate?
		//dbCli.setWriteConcern(WriteConcern.SAFE);
		//just for fun ...
		List<String> dbs = dbCli.getDatabaseNames();
		System.out.println(dbs); // [journaldev, local, admin]

		userDB=dbCli.getDB(dbName);
	
	}

	//dummy test code
	public void createUser(){
		BasicDBObject newUser=new BasicDBObject("firstName", "john").
			append("lastName","dow");
		if(userDB != null){
			userDB.getCollectionFromString("Users").insert(newUser);
			System.out.println("inserted record ");
		}
	}

	public void createUser(String email, String passwd) {
		BasicDBObject newUser=new BasicDBObject("email", email).
				append("passwd",passwd);
		userDB.getCollectionFromString("Users").insert(newUser);
	}

	public boolean loginUser(String email, String passwd) {
		BasicDBObject loginQuery=new BasicDBObject("email", email)
				.append("passwd",passwd);

	    int cnt=userDB.getCollectionFromString("Users").find(loginQuery).count();
		return cnt>=1;
		
	}

	//public JSONArray queryUser() {
	public String queryUser() {
		//Gson gson= new GsonBuilder().create();
		JSON json = new JSON();
		DBCursor cursor = userDB.getCollectionFromString("Users").find();

		//String gson.toJson(cursor);
		String serialize = json.serialize(cursor);
		//JSONArray AllJson = new JSONArray(serialize);
		//return AllJson;

		//just for fun
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(serialize);
		String prettyJsonString = gson.toJson(je);		
		
		//return serialize;
		return prettyJsonString;
		//return serialize;
		//return JsonWriter.formatJson(serialize);
	}
		
	public String queryUser(String email) {
		JSON json = new JSON();
		Pattern regex = Pattern.compile("test"); 
		BasicDBObject query=new BasicDBObject("email", email);
		query.put("email", regex);
		
		DBCursor cursor = userDB.getCollectionFromString("Users").find(query);

		String serialize = json.serialize(cursor);
		
		return serialize;
	}
	
}
