package org.castafiore.mongo.query.qom;

import java.util.regex.Pattern;

import javax.jcr.query.qom.Length;
import javax.jcr.query.qom.PropertyValue;

import com.mongodb.BasicDBObject;

public class MongoLength extends MongoDynamicOperand implements Length{

	private PropertyValue propertyValue;
	
	
	
	public MongoLength(PropertyValue propertyValue) {
		super();
		this.propertyValue = propertyValue;
	}



	@Override
	public PropertyValue getPropertyValue() {
		return propertyValue;
	}



	@Override
	public BasicDBObject getExpression() {
		
		BasicDBObject query = new BasicDBObject("$expression", propertyValue.getPropertyName());
		return query;
	}





}
