package org.castafiore.mongo.nodetype;

import javax.jcr.Session;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeDefinition;
import javax.jcr.nodetype.PropertyDefinition;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MongoNodeTypeDefinition  implements NodeTypeDefinition{

	protected BasicDBObject d;
	
	protected Session session;
	
	
	public MongoNodeTypeDefinition(BasicDBObject d, Session session) {
		super();
		this.d = d;
		this.session = session;
	}

	@Override
	public NodeDefinition[] getDeclaredChildNodeDefinitions() {
		
		BasicDBList list = (BasicDBList)d.get("declaredChildNodeDefinitions");
		MongoNodeDefinition[] defs = new MongoNodeDefinition[list.size()];
		for(int i =0; i < list.size(); i++){
			BasicDBObject db = (BasicDBObject)list.get(i);
			MongoNodeDefinition def = new MongoNodeDefinition(db, session);
			defs[i] = def;
		}
		
		
		return defs;
	}

	@Override
	public PropertyDefinition[] getDeclaredPropertyDefinitions() {
		BasicDBList list = (BasicDBList)d.get("declaredPropertyDefinitions");
		MongoPropertyDefinition[] defs = new MongoPropertyDefinition[list.size()];
		for(int i =0; i < list.size(); i++){
			BasicDBObject db = (BasicDBObject)list.get(i);
			MongoPropertyDefinition def = new MongoPropertyDefinition(db,session);
			defs[i] = def;
		}
		
		
		return defs;
		
		
	}

	@Override
	public String[] getDeclaredSupertypeNames() {
		NodeType[] superTypes = getDeclaredSupertypes();
		String[] names = new String[superTypes.length];
		for(int i = 0; i < superTypes.length; i++){
			names[i] = superTypes[i].getName();
		}
		
		return names;
	}
	

	public NodeType[] getDeclaredSupertypes() {
		BasicDBList list = (BasicDBList)d.get("declaredSupertypes");
		MongoNodeType[] defs = new MongoNodeType[list.size()];
		for(int i =0; i < list.size(); i++){
			BasicDBObject db = (BasicDBObject)list.get(i);
			MongoNodeType def = new MongoNodeType(db, session);
			defs[i] = def;
		}
		
		
		return defs;
	}

	@Override
	public String getName() {
		return d.getString("name");
	}

	@Override
	public String getPrimaryItemName() {
		return d.getString("primaryItemName");
	}

	@Override
	public boolean hasOrderableChildNodes() {
		return d.getBoolean("hasOrderableChildNodes");
	}

	@Override
	public boolean isAbstract() {
		return d.getBoolean("abstract");
	}

	@Override
	public boolean isMixin() {
		return d.getBoolean("mixin");
	}

	@Override
	public boolean isQueryable() {
		return d.getBoolean("queryable");
	}

}
