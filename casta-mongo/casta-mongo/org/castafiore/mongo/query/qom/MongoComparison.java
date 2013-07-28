package org.castafiore.mongo.query.qom;

import java.util.regex.Pattern;

import javax.jcr.query.qom.Comparison;
import javax.jcr.query.qom.DynamicOperand;
import javax.jcr.query.qom.QueryObjectModelConstants;
import javax.jcr.query.qom.StaticOperand;

import com.mongodb.BasicDBObject;

public class MongoComparison extends MongoConstraint implements Comparison{

	private MongoDynamicOperand op1;
	
	private MongoStaticOperand op2;
	
	private String op;
	
	
	
	public MongoComparison(MongoDynamicOperand op1, MongoStaticOperand op2, String op) {
		super();
		this.op1 = op1;
		this.op2 = op2;
		this.op = op;
	}

	@Override
	public DynamicOperand getOperand1() {
		return op1;
	}

	@Override
	public StaticOperand getOperand2() {
		return op2;
	}

	@Override
	public String getOperator() {
		return op;
	}

	@Override
	public BasicDBObject getExpression() {
		String oo = null;
		if(QueryObjectModelConstants.JCR_OPERATOR_EQUAL_TO.equals(op)){
			
		}else if(QueryObjectModelConstants.JCR_OPERATOR_GREATER_THAN.equals(op)){
			oo= "$gt";
		}else if(QueryObjectModelConstants.JCR_OPERATOR_GREATER_THAN_OR_EQUAL_TO.equals(op)){
			oo= "$gte";
		}else if(QueryObjectModelConstants.JCR_OPERATOR_LESS_THAN.equals(op)){
			oo= "$lt";
		}else if(QueryObjectModelConstants.JCR_OPERATOR_LESS_THAN_OR_EQUAL_TO.equals(op)){
			oo= "$lte";
		}else if(QueryObjectModelConstants.JCR_OPERATOR_LIKE.equals(op)){
			
		}else if(QueryObjectModelConstants.JCR_OPERATOR_NOT_EQUAL_TO.equals(op)){
			oo= "$ne";
		}
		
		
		if(oo != null){
			BasicDBObject object = new BasicDBObject();
			
			BasicDBObject o = new BasicDBObject();
			o.put(oo, op2.getExpression().get("$expression"));
			object.put(op1.getExpression().getString("$expression"),  o);
			return object;
		}else if(QueryObjectModelConstants.JCR_OPERATOR_EQUAL_TO.equals(op)){
			BasicDBObject o = new BasicDBObject();
			o.put(op1.getExpression().getString("$expression"), op2.getExpression().get("$expression"));
			return o;
		}else{
			//like
			String opp = op2.getExpression().getString("$expression");
			Pattern pparentPath = Pattern.compile(".*" + opp + ".*");
			BasicDBObject query = new BasicDBObject(op1.getExpression().getString("$expression"), pparentPath);
			return query;
		}
		
	}

}
