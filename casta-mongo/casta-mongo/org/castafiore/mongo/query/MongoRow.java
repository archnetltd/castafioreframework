package org.castafiore.mongo.query;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import javax.jcr.query.Row;
import javax.jcr.query.qom.Column;

public class MongoRow implements Row{
	
	private Node node;
	
	private double score;
	
	private Column[] columns;
	
	

	public MongoRow(Node node, double score, Column[] columns) {
		super();
		this.node = node;
		this.score = score;
		this.columns = columns;
	}

	@Override
	public Node getNode() throws RepositoryException {
		return node;
	}

	@Override
	public Node getNode(String selectorName) throws RepositoryException {
		return node;
	}

	@Override
	public String getPath() throws RepositoryException {
		return node.getPath();
	}

	@Override
	public String getPath(String selectorName) throws RepositoryException {
		return node.getPath();
	}

	@Override
	public double getScore() throws RepositoryException {
		return score;
	}

	@Override
	public double getScore(String selectorName) throws RepositoryException {
		return score;
	}

	@Override
	public Value getValue(String columnName) throws ItemNotFoundException,
			RepositoryException {
		for(Column c : columns){
			if(c.getColumnName().equals(columnName)){
				Property p = node.getProperty(c.getPropertyName());
				if(!p.isMultiple()){
					return p.getValue();
				}else{
					Value[] vals = p.getValues();
					if(vals != null && vals.length > 0){
						return vals[0];
					}
				}
				break;
			}
		}
		return null;
	}

	@Override
	public Value[] getValues() throws RepositoryException {
		List<Value> values = new ArrayList<Value>();
		for(Column c : columns){
			
			Property p = node.getProperty(c.getPropertyName());
			if(!p.isMultiple()){
				Value v = p.getValue();
				if(v != null)
					values.add(p.getValue());
			}else{
				Value[] vals = p.getValues();
				if(vals != null && vals.length > 0){
					for(Value v : vals){
						values.add(v);
					}
				}
			}
				
		}
		return values.toArray(new Value[values.size()]);
	}

}
