package org.castafiore.mongo.query;

import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;
import javax.jcr.query.qom.Column;

public class MongoQueryResult implements QueryResult{
	
	private String[] selectorNames;
	
	private Column[] columns;
	
	private NodeIterator nodes;
	
	private RowIterator rows;
	
	

	public MongoQueryResult(String[] selectorNames, Column[] columns,
			NodeIterator nodes, RowIterator rows) {
		super();
		this.selectorNames = selectorNames;
		this.columns = columns;
		this.nodes = nodes;
		this.rows = rows;
	}

	@Override
	public String[] getColumnNames() throws RepositoryException {
		String[]cols = new String[columns.length];
		
		for(int i =0; i < columns.length;i++){
			cols[i] = columns[i].getColumnName();
		}
		return cols;
	}

	@Override
	public NodeIterator getNodes() throws RepositoryException {
		return nodes;
	}

	@Override
	public RowIterator getRows() throws RepositoryException {
		return rows;
	}

	@Override
	public String[] getSelectorNames() throws RepositoryException {
		return selectorNames;
	}

}
