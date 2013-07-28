package org.castafiore.mongo.query.qom;

import java.util.regex.Pattern;

import javax.jcr.query.qom.PropertyExistence;

import com.mongodb.BasicDBObject;

public class MongoPropertyExistence extends MongoConstraint implements PropertyExistence{

	private String propertyName;
	
	private String selectorName;
	
	
	
	public MongoPropertyExistence(String propertyName, String selectorName) {
		super();
		this.propertyName = propertyName;
		this.selectorName = selectorName;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

	@Override
	public BasicDBObject getExpression() {
	
		BasicDBObject query = new BasicDBObject(propertyName,new BasicDBObject("$exists", true));
		return query;
	}

}
