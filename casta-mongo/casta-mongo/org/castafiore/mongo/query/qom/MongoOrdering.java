package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.DynamicOperand;
import javax.jcr.query.qom.Ordering;

public class MongoOrdering implements Ordering {

	private DynamicOperand operand;
	
	private String order;
	
	
	
	public MongoOrdering(DynamicOperand operand, String order) {
		super();
		this.operand = operand;
		this.order = order;
	}

	@Override
	public DynamicOperand getOperand() {
		return operand;
	}

	@Override
	public String getOrder() {
		return order;
	}

}
