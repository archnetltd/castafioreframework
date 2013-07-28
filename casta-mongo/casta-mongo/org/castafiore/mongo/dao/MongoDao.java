package org.castafiore.mongo.dao;

import java.net.UnknownHostException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class MongoDao {
	
	public static Map<String, MongoDao> instances = new LinkedHashMap<String, MongoDao>();
	
	private DB database;
	
	private DBCollection nodes;
	
	private DBCollection namespace;
	
	private Map<String, DBCollection> workspaces= new LinkedHashMap<String, DBCollection>();
	
	
	public static MongoDao getInstance(String repository) throws UnknownHostException{
		if(!instances.containsKey(repository)){
			instances.put(repository, new MongoDao(repository));
		}
		return instances.get(repository);
	}
	
	
	private MongoDao(String repository) throws UnknownHostException {
		super();
		MongoClient mongoClient = new MongoClient();
		database = mongoClient.getDB(repository);
	}
	
	public boolean hasWorkspace(String name){
		return database.getCollectionNames().contains(name);
	}

	
	public void deleteWorkspace(String name){
		if(!hasWorkspace(name)){
			database.getCollection(name).drop();
		}
	}
	
	public DBCollection getNodeTypesCollection(){
		if(nodes == null){
			nodes = database.getCollection("__NodeTypes");
		}
		return nodes;
	}
	
	public DBCollection getNamespaceRegistryCollection(){
		if(namespace == null){
			namespace = database.getCollection("__NamespaceRegistry");
		}
		return namespace;
	}
	
	
	public DBCollection getWorkspace(String workspace){
		if(!workspaces.containsKey(workspace)){
			workspaces.put(workspace, database.getCollection(workspace));
		}
		
		return workspaces.get(workspace);
	}

}
