package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.And;
import javax.jcr.query.qom.Constraint;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;

public class MongoAnd extends MongoConstraint implements And{

	private MongoConstraint constraint1;
	
	private MongoConstraint constraint2;
	
	public MongoAnd(MongoConstraint constraint1, MongoConstraint constraint2) {
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
		obj.put("$and", list);
		return obj;
	}

}
