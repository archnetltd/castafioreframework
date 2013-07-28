package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.FullTextSearchScore;

import com.mongodb.BasicDBObject;

public class MongoFullTextSearchScore extends MongoDynamicOperand implements FullTextSearchScore{

	private String selectorName;
	
	
	
	public MongoFullTextSearchScore(String selectorName) {
		super();
		this.selectorName = selectorName;
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
