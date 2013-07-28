package org.castafiore.mongo.query;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.jcr.ItemExistsException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.UnsupportedRepositoryOperationException;
import javax.jcr.Value;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.Query;
import javax.jcr.query.QueryResult;
import javax.jcr.version.VersionException;

public class MongoQuery implements Query{

	private String language;
	
	private Map<String, Value> bindings = new LinkedHashMap<String, Value>();
	
	private String statement;
	
	private long limit;
	
	private long offset;
	
	private String storedQueryPath;
	
	private Session session;
	
	
	public MongoQuery(String language, String statement, Session session) {
		super();
		this.language = language;
		this.statement = statement;
		this.session = session;
	}

	public MongoQuery(String language, String statement, Session session, String storedQueryPath) {
		super();
		this.language = language;
		this.statement = statement;
		this.session = session;
		this.storedQueryPath = storedQueryPath;
	}
	
	
	@Override
	public void bindValue(String varName, Value value)
			throws IllegalArgumentException, RepositoryException {
		bindings.put(varName, value);
		
	}

	@Override
	public QueryResult execute() throws InvalidQueryException,
			RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getBindVariableNames() throws RepositoryException {
		return bindings.keySet().toArray(new String[]{});
	}

	@Override
	public String getLanguage() {
		return language;
	}

	@Override
	public String getStatement() {
		return statement;
	}

	@Override
	public String getStoredQueryPath() throws ItemNotFoundException,
			RepositoryException {
		return storedQueryPath;
	}

	@Override
	public void setLimit(long limit) {
		
		this.limit = limit;
	}

	@Override
	public void setOffset(long offset) {
		this.offset = offset;
		
	}

	@Override
	public Node storeAsNode(String absPath) throws ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException,
			UnsupportedRepositoryOperationException, RepositoryException {
		
		Node n = session.getRootNode().addNode(absPath, "nt:query");
		n.setProperty("jcr:statement", statement);
		n.setProperty("jcr:language", language);
		this.storedQueryPath = absPath;
		session.save();
		return n;

		
		
		
	}

}
