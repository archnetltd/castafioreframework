package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.DescendantNodeJoinCondition;

public class MongoDescendantNodeJoinCondition extends MongoJoinCondition implements DescendantNodeJoinCondition{

	private String ancestorSelectorName;
	
	private String descendantSelectorName;
	
	
	
	public MongoDescendantNodeJoinCondition(String ancestorSelectorName,
			String descendantSelectorName) {
		super();
		this.ancestorSelectorName = ancestorSelectorName;
		this.descendantSelectorName = descendantSelectorName;
	}

	@Override
	public String getAncestorSelectorName() {
		return ancestorSelectorName;
	}

	@Override
	public String getDescendantSelectorName() {
		return descendantSelectorName;
	}

}
