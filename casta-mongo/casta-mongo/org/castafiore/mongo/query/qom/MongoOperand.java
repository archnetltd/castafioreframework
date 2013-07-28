package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Operand;

import com.mongodb.BasicDBObject;

public abstract class MongoOperand implements Operand{
	
	public abstract BasicDBObject getExpression();

}
