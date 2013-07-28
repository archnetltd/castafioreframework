package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Constraint;

import com.mongodb.BasicDBObject;

public abstract class MongoConstraint implements Constraint{
	
	public abstract BasicDBObject getExpression();
	

}
