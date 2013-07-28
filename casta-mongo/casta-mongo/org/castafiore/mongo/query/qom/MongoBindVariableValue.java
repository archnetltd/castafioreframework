package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.BindVariableValue;

import com.mongodb.BasicDBObject;

public class MongoBindVariableValue extends MongoStaticOperand implements BindVariableValue{

	private String bindVariableName;
	
	
	
	public MongoBindVariableValue(String bindVariableName) {
		super();
		this.bindVariableName = bindVariableName;
	}



	@Override
	public String getBindVariableName() {
		return bindVariableName;
	}



	@Override
	public BasicDBObject getExpression() {
		return new BasicDBObject("$bindVariableName", this.bindVariableName);
	}

}
