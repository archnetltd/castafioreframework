package org.castafiore.mongo.nodetype;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Session;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NodeDefinitionTemplate;

import com.mongodb.BasicDBObject;

public class MongoNodeDefinitionTemplate extends MongoNodeDefinition implements NodeDefinitionTemplate{

	public MongoNodeDefinitionTemplate(BasicDBObject d, Session session) {
		super(d, session);
	}

	@Override
	public void setAutoCreated(boolean autoCreated) {
		d.put("autoCreated", autoCreated);
		
	}

	@Override
	public void setDefaultPrimaryTypeName(String name)
			throws ConstraintViolationException {
		d.put("defaultPrimaryTypeName", name);
		
	}

	@Override
	public void setMandatory(boolean mandatory) {
		d.put("mandatory", mandatory);
		
	}

	@Override
	public void setName(String name) throws ConstraintViolationException {
		d.put("name",name);
		
	}

	@Override
	public void setOnParentVersion(int opv) {
		d.put("onParentVersion", opv);
		
	}

	@Override
	public void setProtected(boolean protectedStatus) {
		d.put("protected", protectedStatus);
		
	}

	@Override
	public void setRequiredPrimaryTypeNames(String[] names)
			throws ConstraintViolationException {
		List<String> ss = new ArrayList<String>(names.length);
		for(String name : names){
			ss.add(name);
		}
		
		d.put("requiredPrimaryTypeNames", ss);
		
	}

	@Override
	public void setSameNameSiblings(boolean allowSameNameSiblings) {
		d.put("allowSameNameSiblings", allowSameNameSiblings);
		
	}

}
