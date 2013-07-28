package org.castafiore.mongo.query.qom;

import java.util.regex.Pattern;

import javax.jcr.query.qom.DescendantNode;

import com.mongodb.BasicDBObject;

public class MongoDescendantNode extends MongoConstraint implements DescendantNode {

	private String ancestorPath;
	
	private String selectorName;
	
	
	
	public MongoDescendantNode(String ancestorPath, String selectorName) {
		super();
		this.ancestorPath = ancestorPath;
		this.selectorName = selectorName;
	}

	@Override
	public String getAncestorPath() {
		return ancestorPath;
	}

	@Override
	public String getSelectorName() {
		return selectorName;
	}

	@Override
	public BasicDBObject getExpression() {
		Pattern pparentPath = Pattern.compile("/" + ancestorPath + "/[^/]*");
		BasicDBObject query = new BasicDBObject("path", pparentPath);
		return query;
	}

}
