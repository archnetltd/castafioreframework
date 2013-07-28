package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Column;

public class MongoColumn implements Column{

	private String columnName;
	
	private String propertyName;
	
	private String selectorName;
	
	
	
	public MongoColumn(String columnName, String propertyName,
			String selectorName) {
		super();
		this.columnName = columnName;
		this.propertyName = propertyName;
		this.selectorName = selectorName;
	}

	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public String getPropertyName() {
		return propertyName;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

}
