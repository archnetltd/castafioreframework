package org.castafiore.mongo;
import java.net.UnknownHostException;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
public class Mongo {

	
	public static DBCollection c;
	public static DBCollection o;
	
	static{
		try {
			MongoClient mongoClient = new MongoClient();
			c = mongoClient.getDB("Castafiore").getCollection("Nodes");
			c.setObjectClass(MongoNode.class);
			o = mongoClient.getDB("Castafiore").getCollection("Organizations");
			o.setObjectClass(Organization.class);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private static void createOrganization(){
		Organization o = new Organization("elieandsons");
		o.setAddressLine1("setAddressLine1");
		o.setAddressLine2("setAddressLine2");
		o.setAddressLine3("setAddressLine3");
		o.setBrn("setBrn");
		o.setCategory("setCategory");
		o.setCity("setCity");
		o.setCountry("setCountry");
		o.setCurrency("setCurrency");
		o.setDetail("setDetail");
		o.setEmail("setEmail");
		o.setFax("setFax");
		o.setLogo("setLogo");
		o.setMobile("setMobile");
		o.setPhone("setPhone");
		o.setSummary("setSummary");
		o.setTitle("setTitle");
		o.setVatRegNumber("setVatRegNumber");
		o.setWebsite("setWebsite");
		o.setZipCode("setZipCode");
		o.save();
	}
	
	public static void getOrganization(){
		System.out.println(new MongoRepository().getOrganization("elieandsons"));
	}
	
	
	
	public static void testFiles(){
		MongoSession session = new MongoRepository().getOrganization("elieandsons").getSession();
		MongoNode root = session.getRootNode();
		MongoNode preferences = root.createNode("preferences", "nt:folder");
		MongoNode applications = root.createNode("applications", "nt:folder");
		MongoNode system = root.createNode("system", "nt:folder");
		applications.createNode("mongodb", "nt:folder");
		session.save();
	}
	
	public static void testData(){
		MongoSession session = new MongoRepository().getOrganization("elieandsons").getSession();
		MongoNode root = session.getRootNode();
		System.out.println(root);
		DBCursor cursor = root.getNodes();
		while(cursor.hasNext()){
			System.out.println(cursor.next());
		}
		
		System.out.println(session.getNode("/elieandsons/applications/mongodb"));
	}
	
	
	public static void main(String[] args)throws Exception {
		//createOrganization();
		//getOrganization();
		//testFiles();
		testData();
		
	//	MongoNode m = new MongoNode();
		//m.put("name", "root");
		//m.save();
//		c.setObjectClass(MongoNode.class);
		
		
		
//	MongoNode f = (MongoNode)c.findOne();
//	f.remove();
//	f.save();
	
		//MongoNode hello = f.createNode("hello", "nt:folder");
		//MongoNode d = hello.createNode("sdf", "nt:mmmdf");
		
		
		
		
		
//		Mongo m = new Mongo();
//	
//		DBCollection c = m.getCollection("Files");
//
//		System.out.println(c);
//		
////		BasicDBObject o = new BasicDBObject();
////		o.append("name", "elieandsons")
////		.append("dateCreated", new Date())
////		.append("dateModified", new Date())
////		.append("owner", "kureem")
////		.append("status", 1)
////		.append("permissions", "rw")
////		.append("metadata", new String[]{"test", "ods", "etr"})
////		.append("tags", new String[]{"a", "asdf", "fsdf"});
////		
////		c.save(o);
//		
//		BasicDBObject query = new BasicDBObject();
//		query.append("owner", "kureem");
//		DBCursor cursor = c.find(query);
//		while(cursor.hasNext()){
//			DBObject o = cursor.next();
//			
////			List<DBObject> objs = new ArrayList<DBObject>();
////			for(int i = 0; i < 100000;i++){
////				objs.add(m.getBasicFile("cc"));
////			}
////			
////			o.put("children", objs);
////			
////			c.save(o);
//			
//			System.out.println(o);
//			
//		}
		
		
		
	}
}
