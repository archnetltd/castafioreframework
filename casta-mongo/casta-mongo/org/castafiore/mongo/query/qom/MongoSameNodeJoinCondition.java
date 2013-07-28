package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.SameNodeJoinCondition;

public class MongoSameNodeJoinCondition extends MongoJoinCondition implements SameNodeJoinCondition{

	private String selector1Name;
	
	private String selector2Name;
	
	private String selector2Path;
	
	public MongoSameNodeJoinCondition(String selector1Name,
			String selector2Name, String selector2Path) {
		super();
		this.selector1Name = selector1Name;
		this.selector2Name = selector2Name;
		this.selector2Path = selector2Path;
	}

	@Override
	public String getSelector1Name() {
		return selector1Name;
	}

	@Override
	public String getSelector2Name() {
		return selector2Name;
	}

	@Override
	public String getSelector2Path() {
		return selector2Path;
	}

}
