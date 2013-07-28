package org.castafiore.mongo.query.qom;

import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.query.qom.Literal;

import com.mongodb.BasicDBObject;

public class MongoLiteral extends MongoStaticOperand implements Literal{

	private Value literalValue;
	
	
	
	public MongoLiteral(Value literalValue) {
		super();
		this.literalValue = literalValue;
	}



	@Override
	public Value getLiteralValue() {
		return literalValue;
	}



	@Override
	public BasicDBObject getExpression() {
		
		try {
			return new BasicDBObject("$expression", literalValue.getString());
		} catch (ValueFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

}
