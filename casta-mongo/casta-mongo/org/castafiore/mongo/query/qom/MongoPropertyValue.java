package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.PropertyValue;

import com.mongodb.BasicDBObject;

public class MongoPropertyValue extends MongoDynamicOperand implements PropertyValue{

	private String propertyName;
	
	private String selectorName;
	
	
	
	public MongoPropertyValue(String propertyName, String selectorName) {
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
		return new BasicDBObject("$expression", propertyName);
	}

}
