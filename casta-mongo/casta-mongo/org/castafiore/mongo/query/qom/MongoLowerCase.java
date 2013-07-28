package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.DynamicOperand;
import javax.jcr.query.qom.LowerCase;

import com.mongodb.BasicDBObject;

public class MongoLowerCase extends MongoDynamicOperand implements LowerCase{

	private MongoDynamicOperand operand;
	
	
	
	public MongoLowerCase(MongoDynamicOperand operand) {
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
