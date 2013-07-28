package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.NodeName;

import com.mongodb.BasicDBObject;

public class MongoNodeName extends MongoDynamicOperand implements NodeName{

	private String selectorName;
	
	
	
	public MongoNodeName(String selectorName) {
		super();
		this.selectorName = selectorName;
	}



	@Override
	public String getSelectorName() {
		return selectorName;
	}



	@Override
	public BasicDBObject getExpression() {
		return new BasicDBObject("$expression", "name");
	}

}
