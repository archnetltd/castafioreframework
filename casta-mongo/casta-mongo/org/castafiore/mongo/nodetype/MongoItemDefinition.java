package org.castafiore.mongo.nodetype;

import javax.jcr.Session;
import javax.jcr.nodetype.ItemDefinition;
import javax.jcr.nodetype.NodeType;

import com.mongodb.BasicDBObject;

public class MongoItemDefinition implements ItemDefinition{
	
	protected BasicDBObject d;
	protected Session session;

	public MongoItemDefinition(BasicDBObject d, Session session) {
		super();
		this.d = d;
		this.session = session;
	}

	@Override
	public NodeType getDeclaringNodeType() {
		
		MongoNodeType nt = new MongoNodeType((BasicDBObject)d.get("declaringNodeType"), session);
		return nt;
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
