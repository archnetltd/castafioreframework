package org.castafiore.mongo.query.qom;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.query.InvalidQueryException;
import javax.jcr.query.qom.And;
import javax.jcr.query.qom.BindVariableValue;
import javax.jcr.query.qom.ChildNode;
import javax.jcr.query.qom.ChildNodeJoinCondition;
import javax.jcr.query.qom.Column;
import javax.jcr.query.qom.Comparison;
import javax.jcr.query.qom.Constraint;
import javax.jcr.query.qom.DescendantNode;
import javax.jcr.query.qom.DescendantNodeJoinCondition;
import javax.jcr.query.qom.DynamicOperand;
import javax.jcr.query.qom.EquiJoinCondition;
import javax.jcr.query.qom.FullTextSearch;
import javax.jcr.query.qom.FullTextSearchScore;
import javax.jcr.query.qom.Join;
import javax.jcr.query.qom.JoinCondition;
import javax.jcr.query.qom.Length;
import javax.jcr.query.qom.Literal;
import javax.jcr.query.qom.LowerCase;
import javax.jcr.query.qom.NodeLocalName;
import javax.jcr.query.qom.NodeName;
import javax.jcr.query.qom.Not;
import javax.jcr.query.qom.Or;
import javax.jcr.query.qom.Ordering;
import javax.jcr.query.qom.PropertyExistence;
import javax.jcr.query.qom.PropertyValue;
import javax.jcr.query.qom.QueryObjectModel;
import javax.jcr.query.qom.QueryObjectModelConstants;
import javax.jcr.query.qom.QueryObjectModelFactory;
import javax.jcr.query.qom.SameNode;
import javax.jcr.query.qom.SameNodeJoinCondition;
import javax.jcr.query.qom.Selector;
import javax.jcr.query.qom.Source;
import javax.jcr.query.qom.StaticOperand;
import javax.jcr.query.qom.UpperCase;

import org.castafiore.mongo.Mongo;

import com.mongodb.gridfs.GridFSInputFile;

public class MongoQueryObjectModelFactory implements QueryObjectModelFactory{

	private Session session;
	
	
	
	public MongoQueryObjectModelFactory(Session session) {
		super();
		this.session = session;
	}

	@Override
	public And and(Constraint constraint1, Constraint constraint2)	throws InvalidQueryException, RepositoryException {
		return new MongoAnd((MongoConstraint)constraint1, (MongoConstraint)constraint2);
	}

	@Override
	public Ordering ascending(DynamicOperand operand)
			throws InvalidQueryException, RepositoryException {
		return  new MongoOrdering(operand,QueryObjectModelConstants.JCR_ORDER_ASCENDING );
	}

	@Override
	public BindVariableValue bindVariable(String bindVariableName)
			throws InvalidQueryException, RepositoryException {
		return new MongoBindVariableValue(bindVariableName);
	}

	@Override
	public ChildNode childNode(String selectorName, String path)
			throws InvalidQueryException, RepositoryException {
		return new MongoChildNode(path, selectorName);
	}

	@Override
	public ChildNodeJoinCondition childNodeJoinCondition(
			String childSelectorName, String parentSelectorName)
			throws InvalidQueryException, RepositoryException {
		return new MongoChildNodeJoinCondition(childSelectorName, parentSelectorName);
	}

	@Override
	public Column column(String selectorName, String propertyName,
			String columnName) throws InvalidQueryException,
			RepositoryException {
		return new MongoColumn(columnName, propertyName, selectorName);
	}

	@Override
	public Comparison comparison(DynamicOperand operand1, String operator,
			StaticOperand operand2) throws InvalidQueryException,
			RepositoryException {
		return new MongoComparison((MongoDynamicOperand)operand1, (MongoStaticOperand)operand2, operator);
	}

	@Override
	public QueryObjectModel createQuery(Source source, Constraint constraint,
			Ordering[] orderings, Column[] columns)
			throws InvalidQueryException, RepositoryException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DescendantNode descendantNode(String selectorName, String path)
			throws InvalidQueryException, RepositoryException {
		return new MongoDescendantNode(path, selectorName);
	}

	@Override
	public DescendantNodeJoinCondition descendantNodeJoinCondition(
			String descendantSelectorName, String ancestorSelectorName)
			throws InvalidQueryException, RepositoryException {
		return new MongoDescendantNodeJoinCondition(ancestorSelectorName, descendantSelectorName);
	}

	@Override
	public Ordering descending(DynamicOperand operand)
			throws InvalidQueryException, RepositoryException {
		return new MongoOrdering(operand, JCR_ORDER_DESCENDING);
	}

	@Override
	public EquiJoinCondition equiJoinCondition(String selector1Name,
			String property1Name, String selector2Name, String property2Name)
			throws InvalidQueryException, RepositoryException {
		return new MongoEquiJoinCondition(property1Name, property2Name, selector1Name, selector2Name);
	}

	@Override
	public FullTextSearch fullTextSearch(String selectorName,
			String propertyName, StaticOperand fullTextSearchExpression)
			throws InvalidQueryException, RepositoryException {
		return new MongoFullTextSearch(fullTextSearchExpression, propertyName, selectorName);
	}

	@Override
	public FullTextSearchScore fullTextSearchScore(String selectorName)
			throws InvalidQueryException, RepositoryException {
		return new MongoFullTextSearchScore(selectorName);
	}

	@Override
	public Join join(Source left, Source right, String joinType,
			JoinCondition joinCondition) throws InvalidQueryException,
			RepositoryException {
		return new MongoJoin(joinCondition, joinType, left, right);
	}

	@Override
	public Length length(PropertyValue propertyValue)
			throws InvalidQueryException, RepositoryException {
		return new MongoLength(propertyValue);
	}

	@Override
	public Literal literal(Value literalValue) throws InvalidQueryException,
			RepositoryException {
		return new MongoLiteral(literalValue);
	}

	@Override
	public LowerCase lowerCase(DynamicOperand operand)
			throws InvalidQueryException, RepositoryException {
		return new MongoLowerCase((MongoDynamicOperand)operand);
	}

	@Override
	public NodeLocalName nodeLocalName(String selectorName)
			throws InvalidQueryException, RepositoryException {
		return new MongoNodeLocalName(selectorName);
	}

	@Override
	public NodeName nodeName(String selectorName) throws InvalidQueryException,
			RepositoryException {
		return new MongoNodeName(selectorName);
	}

	@Override
	public Not not(Constraint constraint) throws InvalidQueryException,
			RepositoryException {
		return new MongoNot((MongoConstraint)constraint);
	}

	@Override
	public Or or(Constraint constraint1, Constraint constraint2)
			throws InvalidQueryException, RepositoryException {
		return new MongoOr((MongoConstraint)constraint1, (MongoConstraint)constraint2);
	}

	@Override
	public PropertyExistence propertyExistence(String selectorName,
			String propertyName) throws InvalidQueryException,
			RepositoryException {
		return new MongoPropertyExistence(propertyName, selectorName);
	}

	@Override
	public PropertyValue propertyValue(String selectorName, String propertyName)
			throws InvalidQueryException, RepositoryException {
		return new MongoPropertyValue(propertyName, selectorName);
	}

	@Override
	public SameNode sameNode(String selectorName, String path)
			throws InvalidQueryException, RepositoryException {
		return new MongoSameNode(path, selectorName);
	}

	@Override
	public SameNodeJoinCondition sameNodeJoinCondition(String selector1Name,
			String selector2Name, String selector2Path)
			throws InvalidQueryException, RepositoryException {
		return new MongoSameNodeJoinCondition(selector1Name, selector2Name, selector2Path);
	}

	@Override
	public Selector selector(String nodeTypeName, String selectorName)
			throws InvalidQueryException, RepositoryException {
		return new MongoSelector(nodeTypeName, selectorName);
	}

	@Override
	public UpperCase upperCase(DynamicOperand operand)
			throws InvalidQueryException, RepositoryException {
		
		return new MongoUpperCase((MongoDynamicOperand)operand);
	}

}
