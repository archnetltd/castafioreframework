package org.castafiore.mongo.nodetype;

import java.util.List;

import javax.jcr.Session;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MongoNodeDefinition extends MongoItemDefinition implements NodeDefinition{
	
	

	public MongoNodeDefinition(BasicDBObject d, Session session) {
		super(d, session);
		
		
	}

	@Override
	public boolean allowsSameNameSiblings() {
		return d.getBoolean("allowSameNameSiblings");
	}

	@Override
	public NodeType getDefaultPrimaryType() {
		
		BasicDBObject o = (BasicDBObject)d.get("defaultPrimaryType");
		return new MongoNodeType(o, session);
		
		
	}

	@Override
	public String getDefaultPrimaryTypeName() {
		return d.getString("defaultPrimaryTypeName");
	}

	@Override
	public String[] getRequiredPrimaryTypeNames() {
		return ((List<String>)d.get("requiredTypeNames")).toArray(new String[]{});
	}

	@Override
	public NodeType[] getRequiredPrimaryTypes() {
		BasicDBList list = (BasicDBList)d.get("requiredPrimaryTypes");
		MongoNodeType[] defs = new MongoNodeType[list.size()];
		for(int i =0; i < list.size(); i++){
			BasicDBObject db = (BasicDBObject)list.get(i);
			MongoNodeType def = new MongoNodeType(db, session);
			defs[i] = def;
		}
		
		
		return defs;
		
		
		//return ((List<NodeType>)d.get("requiredPrimaryTypes")).toArray(new NodeType[]{});
	}

	@Override
	public NodeType getDeclaringNodeType() {
		BasicDBObject o = (BasicDBObject)d.get("declaringNodeType");
		return new MongoNodeType(o, session);
		
	}

	@Override
	public String getName() {
		return d.getString("name");
	}

	@Override
	public int getOnParentVersion() {
		return d.getInt("onParentVersion");
	}

	@Override
	public boolean isAutoCreated() {
		return d.getBoolean("autoCreated");
	}

	@Override
	public boolean isMandatory() {
		return d.getBoolean("mandatory");
	}

	@Override
	public boolean isProtected() {
		return d.getBoolean("protected");
	}
}
