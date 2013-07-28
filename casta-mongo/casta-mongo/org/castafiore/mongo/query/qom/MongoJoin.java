package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Join;
import javax.jcr.query.qom.JoinCondition;
import javax.jcr.query.qom.Source;

public class MongoJoin implements Join {

	private JoinCondition joinCondition;
	
	private String joinType;
	
	private Source left;
	
	private Source right;
	
	
	
	public MongoJoin(JoinCondition joinCondition, String joinType, Source left,
			Source right) {
		super();
		this.joinCondition = joinCondition;
		this.joinType = joinType;
		this.left = left;
		this.right = right;
	}

	@Override
	public JoinCondition getJoinCondition() {
		return joinCondition;
	}

	@Override
	public String getJoinType() {
		return joinType;
	}

	@Override
	public Source getLeft() {
		return left;
	}

	@Override
	public Source getRight() {
		return  right;
	}

}
