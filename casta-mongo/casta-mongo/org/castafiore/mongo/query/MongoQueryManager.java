package org.castafiore.mongo.query;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.qom.QueryObjectModelFactory;

import org.castafiore.mongo.query.qom.MongoQueryObjectModelFactory;

public class MongoQueryManager implements QueryManager{

	private Session session;
	
	
	
	public MongoQueryManager(Session session) {
		super();
		this.session = session;
	}

	@Override
	public Query createQuery(String statement, String language)
			throws InvalidQueryException, RepositoryException {
		return new MongoQuery(statement, language,session);
	}

	@Override
	public QueryObjectModelFactory getQOMFactory() {
		return new MongoQueryObjectModelFactory(session);
	}

	@Override
	public Query getQuery(Node node) throws InvalidQueryException,
			RepositoryException {
		if(node.isNodeType("nt:query")){
			return new MongoQuery(node.getProperty("jcr:language").getString(), node.getProperty("jcr:statement").getString(), node.getSession(), node.getPath());
		}else{
			throw new InvalidQueryException("the node " + node.getPath() + " is not of type nt:query");
		}
	}

	@Override
	public String[] getSupportedQueryLanguages() throws RepositoryException {
		return new String[]{Query.JCR_JQOM,Query.JCR_SQL2};
	}

}
