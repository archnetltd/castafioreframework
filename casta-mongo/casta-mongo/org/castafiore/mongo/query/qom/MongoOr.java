package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Constraint;
import javax.jcr.query.qom.Or;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MongoOr extends MongoConstraint implements Or{

	private MongoConstraint constraint1;
	
	private MongoConstraint constraint2;
	
	
	
	public MongoOr(MongoConstraint constraint1, MongoConstraint constraint2) {
		super();
		this.constraint1 = constraint1;
		this.constraint2 = constraint2;
	}

	@Override
	public Constraint getConstraint1() {
		return constraint1;
	}

	@Override
	public Constraint getConstraint2() {
		return constraint2;
	}

	@Override
	public BasicDBObject getExpression() {
		BasicDBObject obj = new BasicDBObject();
		BasicDBList list = new BasicDBList();
		list.add(constraint1.getExpression());
		list.add(constraint2.getExpression());
		obj.put("$or", list);
		return obj;
	}

}
