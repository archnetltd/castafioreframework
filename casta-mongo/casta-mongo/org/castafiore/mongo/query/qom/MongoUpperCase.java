package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.DynamicOperand;
import javax.jcr.query.qom.UpperCase;

import com.mongodb.BasicDBObject;

public class MongoUpperCase extends MongoDynamicOperand implements UpperCase{

	private MongoDynamicOperand operand;
	
	
	public MongoUpperCase(MongoDynamicOperand operand) {
		super();
		this.operand = operand;
	}


	@Override
	public DynamicOperand getOperand() {
		return operand;
	}


	@Override
	public BasicDBObject getExpression() {

		return operand.getExpression();
	}

}
