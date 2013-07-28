package org.castafiore.mongo.nodetype;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeIterator;
import javax.jcr.nodetype.NodeTypeTemplate;
import javax.jcr.nodetype.PropertyDefinition;

import com.mongodb.BasicDBObject;

public class MongoNodeType extends MongoNodeTypeDefinition implements NodeType, NodeTypeTemplate{


	
	
	public MongoNodeType(BasicDBObject d, Session s) {
		super(d, s);
		

	}

	@Override
	public boolean canAddChildNode(String childNodeName) {
		NodeDefinition residual = null;
		NodeDefinition child = null;
		for(NodeDefinition defn : getChildNodeDefinitions()){
			String name = defn.getName();
			if(name.equals("*")){
				residual = defn;
			}else if(name.equals(childNodeName)){
				child = defn;
			}
			
			if(child!= null && child.getDefaultPrimaryType() != null){

				NodeType n = child.getDefaultPrimaryType();
				if(!child.isAutoCreated() && child.allowsSameNameSiblings()){
					NodeType[] reqs = child.getRequiredPrimaryTypes();
					for(NodeType r : reqs){
						if(n.isNodeType(r.getName())){
							return true;
						}
					}
				}
			}
			else if(residual != null && residual.getDefaultPrimaryType() != null){
				NodeType n = residual.getDefaultPrimaryType();
				NodeType[] reqs = residual.getRequiredPrimaryTypes();
				for(NodeType r : reqs){
					if(n.isNodeType(r.getName())){
						return true;
					}
				}
			}
		}
		
		
		
		
		return false;
	}
	
	

	@Override
	public boolean canAddChildNode(String childNodeName, String nodeTypeName) {
		
		NodeDefinition residual = null;
		NodeDefinition child = null;
		for(NodeDefinition defn : getChildNodeDefinitions()){
			String name = defn.getName();
			if(name.equals("*")){
				residual = defn;
			}else if(name.equals(childNodeName)){
				child = defn;
			}
			
			if(child!= null){

				NodeType n;
				try {
					n = session.getWorkspace().getNodeTypeManager().getNodeType(nodeTypeName);
				} catch (NoSuchNodeTypeException e) {
					return false;
				} catch (RepositoryException e) {
					return false;
				}
				if(!child.isAutoCreated() && child.allowsSameNameSiblings()){
					NodeType[] reqs = child.getRequiredPrimaryTypes();
					for(NodeType r : reqs){
						if(n.isNodeType(r.getName())){
							return true;
						}
					}
				}
			}
			else if(residual != null && residual.getDefaultPrimaryType() != null){
				NodeType n;
				try {
					n = session.getWorkspace().getNodeTypeManager().getNodeType(nodeTypeName);
				} catch (NoSuchNodeTypeException e) {
					return false;
				} catch (RepositoryException e) {
					return false;
				}
				NodeType[] reqs = residual.getRequiredPrimaryTypes();
				for(NodeType r : reqs){
					if(n.isNodeType(r.getName())){
						return true;
					}
				}
			}
		}
		
		
		
		
		return false;
	}

	@Override
	public boolean canRemoveItem(String itemName) {
		PropertyDefinition props[] =  getPropertyDefinitions();
		for(PropertyDefinition p : props){
			if(p.getName().equals(itemName)){
				if(!p.isMandatory() && !p.isProtected()){
					return true;
				}
			}
		}
		
		
		for(NodeDefinition d : getChildNodeDefinitions()){
			if(!d.isMandatory() && !d.isProtected()){
				return true;
			}
		}
		
		return true;
	}

	@Override
	public boolean canRemoveNode(String nodeName) {
		for(NodeDefinition d : getChildNodeDefinitions()){
			if(!d.isMandatory() && !d.isProtected()){
				return true;
			}
		}
		
		return true;
	}

	@Override
	public boolean canRemoveProperty(String propertyName) {
		PropertyDefinition p = getPropertyDefinition(propertyName);
		if(!p.isProtected()){
			return true;
		}else
			return false;
	}
	
	

	@Override
	public boolean canSetProperty(String propertyName, Value value) {
		return true;
	}

	@Override
	public boolean canSetProperty(String propertyName, Value[] values) {
		return true;
	}

	@Override
	public NodeDefinition[] getChildNodeDefinitions() {
		NodeType[] superTypes = getSupertypes();
		NodeDefinition[] declared = getDeclaredChildNodeDefinitions();
		
		List<NodeDefinition> result = new ArrayList<NodeDefinition>();
		for(NodeDefinition nd : declared){
			result.add(nd);
		}
		for(NodeType n : superTypes){
			NodeDefinition[] snds = n.getDeclaredChildNodeDefinitions();
			for(NodeDefinition nd : snds){
				result.add(nd);
			}
		}
		
		return result.toArray(new NodeDefinition[result.size()]);
	}

	@Override
	public NodeTypeIterator getDeclaredSubtypes() {
		// TODO Auto-generated method stub
		/**
		 * create mongo query that has extends directly from this nodeName
		 */
		return null;
	}

	

	@Override
	public PropertyDefinition[] getPropertyDefinitions() {
		NodeType[] superTypes = getSupertypes();
		PropertyDefinition[] declared = getDeclaredPropertyDefinitions();
		
		List<PropertyDefinition> result = new ArrayList<PropertyDefinition>();
		for(PropertyDefinition nd : declared){
			result.add(nd);
		}
		for(NodeType n : superTypes){
			PropertyDefinition[] snds = n.getDeclaredPropertyDefinitions();
			for(PropertyDefinition nd : snds){
				result.add(nd);
			}
		}
		
		return result.toArray(new PropertyDefinition[result.size()]);
		
	}
	
	public PropertyDefinition getPropertyDefinition(String name){
		for(PropertyDefinition p : getPropertyDefinitions()){
			if(p.getName().equals(name))
				return p;
		}
		
		return null;
	}
	
	

	@Override
	public NodeTypeIterator getSubtypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NodeType[] getSupertypes() {
		List<NodeType> supertypes = new ArrayList<NodeType>();
		
		loadSuperTypes(this, supertypes);
		return supertypes.toArray(new NodeType[supertypes.size()]);
		
	}
	
	
	protected void loadSuperTypes(NodeType type, List<NodeType> list){
		NodeType[] supertypes = type.getDeclaredSupertypes();
		for(NodeType n : supertypes){
			list.add(n);
			loadSuperTypes(n, list);
		}
	}

	@Override
	public boolean isNodeType(String nodeTypeName) {
		if(getName().equals(nodeTypeName))
			return true;
		NodeType[] types = getSupertypes();
		for(NodeType t : types){
			if(t.getName().equals(nodeTypeName)){
				return true;
			}
		}
		return false;
	}

	@Override
	public List getNodeDefinitionTemplates() {
		return new ArrayList();
	}

	@Override
	public List getPropertyDefinitionTemplates() {
		return new ArrayList();
	}

	@Override
	public void setAbstract(boolean abstractStatus) {
		d.put("abstract", abstractStatus);
		
	}

	@Override
	public void setDeclaredSuperTypeNames(String[] names)
			throws ConstraintViolationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setMixin(boolean mixin) {
		d.put("mixin", mixin);
		
	}

	@Override
	public void setName(String name) throws ConstraintViolationException {
		d.put("name", name);
		
	}

	@Override
	public void setOrderableChildNodes(boolean orderable) {
		d.put("hasOrderableChildNodes", orderable);
		
	}

	@Override
	public void setPrimaryItemName(String name)
			throws ConstraintViolationException {
		d.put("primaryItemName", name);
		
	}

	@Override
	public void setQueryable(boolean queryable) {
		d.put("queryable", queryable);
		
	}

}
