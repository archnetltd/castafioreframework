package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.SameNode;

import com.mongodb.BasicDBObject;

public class MongoSameNode extends MongoConstraint implements SameNode{

	private String path;
	
	private String selectorName;
	
	
	
	public MongoSameNode(String path, String selectorName) {
		super();
		this.path = path;
		this.selectorName = selectorName;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

	@Override
	public BasicDBObject getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

}
