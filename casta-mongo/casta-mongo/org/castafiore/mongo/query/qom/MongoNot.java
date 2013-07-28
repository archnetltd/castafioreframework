package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Constraint;
import javax.jcr.query.qom.Not;

import com.mongodb.BasicDBObject;

public class MongoNot extends MongoConstraint implements Not{

	private MongoConstraint constraint;
	
	
	public MongoNot(MongoConstraint constraint) {
		super();
		this.constraint = constraint;
	}


	@Override
	public Constraint getConstraint() {
		return constraint;
	}


	@Override
	public BasicDBObject getExpression() {
		BasicDBObject o = new BasicDBObject();
		o.put("$not", constraint.getExpression());
		return o;
	}

}
