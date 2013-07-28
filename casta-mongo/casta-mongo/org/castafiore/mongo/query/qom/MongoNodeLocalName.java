package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.NodeLocalName;

import com.mongodb.BasicDBObject;

public class MongoNodeLocalName extends MongoDynamicOperand implements NodeLocalName{

	private String selectorName;
	
	
	
	public MongoNodeLocalName(String selectorName) {
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
