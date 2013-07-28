package org.castafiore.mongo.query.qom;

import java.util.regex.Pattern;

import javax.jcr.query.qom.ChildNode;

import com.mongodb.BasicDBObject;

public class MongoChildNode extends MongoConstraint implements ChildNode{

	private String parentPath;
	
	private String selectorName;
	
	
	
	public MongoChildNode(String parentPath, String selectorName) {
		super();
		this.parentPath = parentPath;
		this.selectorName = selectorName;
	}

	@Override
	public String getParentPath() {
		return parentPath;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

	@Override
	public BasicDBObject getExpression() {
		///
		Pattern pparentPath = Pattern.compile("/" + parentPath + "/[.^/]*");
		BasicDBObject query = new BasicDBObject("path", pparentPath);
		return query;
	}

}
