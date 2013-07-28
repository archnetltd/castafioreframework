package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.FullTextSearch;
import javax.jcr.query.qom.StaticOperand;

import com.mongodb.BasicDBObject;

public class MongoFullTextSearch extends MongoConstraint implements FullTextSearch{

	private StaticOperand fullTextSearchExpression;
	
	private String propertyName;
	
	private String selectorName;
	
	
	
	public MongoFullTextSearch(StaticOperand fullTextSearchExpression,
			String propertyName, String selectorName) {
		super();
		this.fullTextSearchExpression = fullTextSearchExpression;
		this.propertyName = propertyName;
		this.selectorName = selectorName;
	}

	@Override
	public StaticOperand getFullTextSearchExpression() {
		return fullTextSearchExpression;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

	@Override
	public BasicDBObject getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

}
