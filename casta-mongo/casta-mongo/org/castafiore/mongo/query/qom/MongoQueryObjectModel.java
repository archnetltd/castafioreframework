package org.castafiore.mongo.query.qom;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.query.Query;
import javax.jcr.query.qom.Column;
import javax.jcr.query.qom.Constraint;
import javax.jcr.query.qom.Ordering;
import javax.jcr.query.qom.QueryObjectModel;
import javax.jcr.query.qom.Source;

import org.apache.commons.lang.ArrayUtils;
import org.castafiore.mongo.nodetype.NodeTypeValue;
import org.castafiore.mongo.query.MongoQuery;

public class MongoQueryObjectModel extends MongoQuery implements QueryObjectModel{

	public static int AND =0;
	
	public static int OR = 1;
	
	
	
	public MongoQueryObjectModel(Session session, String nodeType, String selectorName) {
		super(Query.JCR_JQOM,"", session);
		this.source = new MongoSelector(nodeType, selectorName);
	}
	
	private MongoColumn[] columns = new MongoColumn[1];
	
	private MongoConstraint constraint;
	
	private MongoOrdering[] orderings=new MongoOrdering[1];
	
	private MongoSelector source;

	public MongoQueryObjectModel withLength(String propertyName, long length, String operator,int andOr ){
		MongoLength mlength = new MongoLength(new MongoPropertyValue(propertyName, source.getSelectorName()));
		
		MongoComparison c = new MongoComparison(mlength, new MongoLiteral(new NodeTypeValue(length)),operator );
		
		return addConstraint(c, andOr);
	}
	
	public  MongoQueryObjectModel addRestriction(String propertyName, String operator, Object value, int andOr)throws RepositoryException{
		MongoComparison c = new MongoComparison(new MongoPropertyValue(propertyName, source.getSelectorName()), new MongoLiteral(new NodeTypeValue(value)), operator);
		
		return addConstraint(c, andOr);
	}
	
	private MongoQueryObjectModel addConstraint(Constraint c, int andOr){
		if(constraint == null){
			constraint = (MongoConstraint)c;
		}else{
			if(andOr == AND)
				constraint = new MongoAnd((MongoConstraint)constraint, (MongoConstraint)c);
			else
				constraint = new MongoOr((MongoConstraint)constraint, (MongoConstraint)c);
		}
		return this;
	}
	
	public MongoQueryObjectModel addColumn(String columnName, String propertyName){
		MongoColumn c = new MongoColumn(columnName, propertyName, source.getSelectorName());
		ArrayUtils.add(columns, c);
		return this;
	}
	
	public MongoQueryObjectModel addOrder(String propertyName, String dir){
		MongoOrdering o = new MongoOrdering(new MongoPropertyValue(propertyName, source.getSelectorName()),dir);
		ArrayUtils.add(orderings, o);
		return this;
	}
	
	public MongoQueryObjectModel isDescendentOf(String parentPath, int andOr){
		MongoDescendantNode c = new MongoDescendantNode(parentPath, source.getSelectorName());
		return addConstraint(c, andOr);
	}
	
	public MongoQueryObjectModel contains(String expression,String propertyName, int andOr){
		MongoFullTextSearch c = new MongoFullTextSearch(new MongoLiteral(new NodeTypeValue(expression)), propertyName, source.getSelectorName());
		return addConstraint(c, andOr);
	}
	
	public MongoQueryObjectModel score(double value,String operator, int andOr){
		MongoFullTextSearchScore s = new MongoFullTextSearchScore(source.getSelectorName());
		MongoComparison c = new MongoComparison(s, new MongoLiteral(new NodeTypeValue(value)), operator);
		return addConstraint(c, andOr);
	}
	
	public MongoQueryObjectModel isChildNodeOf(String parentPath, int andOr){
		MongoChildNode c = new MongoChildNode(parentPath, source.getSelectorName());
		return addConstraint(c, andOr);
	}
	
	public MongoQueryObjectModel not(Constraint constraint, int andOr){
		MongoNot c = new MongoNot((MongoConstraint)constraint);
		return addConstraint(c, andOr);
	}
	
	
	public MongoQueryObjectModel hasProperty(String propertyName, int andOr){
		MongoPropertyExistence c = new MongoPropertyExistence(propertyName, source.getSelectorName());
		return addConstraint(c, andOr);
	}
	
	public MongoQueryObjectModel isSameNode(String path, int andOr){
		MongoSameNode c = new MongoSameNode(path, source.getSelectorName());
		return addConstraint(c, andOr);
	}
	
	

	public Column[] getColumns() {
		return columns;
	}

	

	public Constraint getConstraint() {
		return constraint;
	}

	

	public Ordering[] getOrderings() {
		return orderings;
	}

	

	public Source getSource() {
		return source;
	}

	
	
	
	
	
	

}
