package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.ChildNodeJoinCondition;



public class MongoChildNodeJoinCondition extends MongoJoinCondition implements ChildNodeJoinCondition {

	private String childSelectorName;
	
	private String parentSelectorName;
	
	
	
	public MongoChildNodeJoinCondition(String childSelectorName,
			String parentSelectorName) {
		super();
		this.childSelectorName = childSelectorName;
		this.parentSelectorName = parentSelectorName;
	}

	@Override
	public String getChildSelectorName() {
		return childSelectorName;
	}

	@Override
	public String getParentSelectorName() {
		return parentSelectorName;
	}

}
