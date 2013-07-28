package org.castafiore.mongo.nodetype;

import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;

import org.castafiore.mongo.MongoRangeIterator;

public class MongoNodeTypeIterator extends MongoRangeIterator implements NodeTypeIterator{

	@Override
	public NodeType nextNodeType() {
		return (NodeType)next();
	}

}
