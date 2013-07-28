package org.castafiore.mongo.query.qom;

import javax.jcr.query.qom.Selector;

public class MongoSelector extends MongoSource implements Selector{

	private String nodeTypeName;
	
	private String selectorName;
	
	
	
	public MongoSelector(String nodeTypeName, String selectorName) {
		super();
		this.nodeTypeName = nodeTypeName;
		this.selectorName = selectorName;
	}

	@Override
	public String getNodeTypeName() {
		return nodeTypeName;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

}
