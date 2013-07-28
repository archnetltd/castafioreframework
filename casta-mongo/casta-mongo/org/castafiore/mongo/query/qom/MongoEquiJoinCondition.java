package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.EquiJoinCondition;

public class MongoEquiJoinCondition extends MongoJoinCondition implements
		EquiJoinCondition {
	
	private String property1Name;
	
	private String property2Name;
	
	private String selector1Name;
	
	private String selector2Name;
	
	

	public MongoEquiJoinCondition(String property1Name, String property2Name,
			String selector1Name, String selector2Name) {
		super();
		this.property1Name = property1Name;
		this.property2Name = property2Name;
		this.selector1Name = selector1Name;
		this.selector2Name = selector2Name;
	}

	@Override
	public String getProperty1Name() {
		return property1Name;
	}

	@Override
	public String getProperty2Name() {
		return property2Name;
	}

	@Override
	public String getSelector1Name() {
		return selector1Name;
	}

	@Override
	public String getSelector2Name() {
		return selector2Name;
	}

}
