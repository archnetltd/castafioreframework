package org.castafiore.mongo.nodetype;

import java.util.ArrayList;
import java.util.List;

import javax.jcr.Session;
import javax.jcr.Value;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.PropertyDefinition;
import javax.jcr.nodetype.PropertyDefinitionTemplate;

import com.mongodb.BasicDBObject;

public class MongoPropertyDefinition extends MongoItemDefinition implements PropertyDefinition, PropertyDefinitionTemplate{

	
	
	public MongoPropertyDefinition(BasicDBObject d, Session session) {
		super(d, session);
		
	}

	@Override
	public String[] getAvailableQueryOperators() {
		return ((List<String>)d.get("availableQueryOperators")).toArray(new String[]{});
	}

	@Override
	public Value[] getDefaultValues() {
		return ((List<Value>)d.get("defaultValues")).toArray(new Value[]{});
	}

	@Override
	public int getRequiredType() {
		return d.getInt("requiredType");
	}

	@Override
	public String[] getValueConstraints() {
		return ((List<String>)d.get("valueConstraints")).toArray(new String[]{});
	}

	@Override
	public boolean isFullTextSearchable() {
		return d.getBoolean("fullTextSearchable");
	}

	@Override
	public boolean isMultiple() {
		return d.getBoolean("multiple");
	}

	@Override
	public boolean isQueryOrderable() {
		return d.getBoolean("queryOrderable");
	}

	@Override
	public void setAutoCreated(boolean autoCreated) {
		d.put("autoCreated", autoCreated);
		
	}

	@Override
	public void setAvailableQueryOperators(String[] operators) {
		List<String> ss = new ArrayList<String>(operators.length);
		for(String s : operators){
			ss.add(s);
		}
		
		d.put("availableQueryOperators", ss);
		
	}

	@Override
	public void setDefaultValues(Value[] defaultValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFullTextSearchable(boolean fullTextSearchable) {
		d.put("fullTextSearchable", fullTextSearchable);
		
	}

	@Override
	public void setMandatory(boolean mandatory) {
		d.put("mandatory", mandatory);
		
	}

	@Override
	public void setMultiple(boolean multiple) {
		d.put("multiple", multiple);
		
	}

	@Override
	public void setName(String name) throws ConstraintViolationException {
		d.put("name", name);
		
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
	public void setQueryOrderable(boolean queryOrderable) {
		d.put("queryOrderable", queryOrderable);
		
	}

	@Override
	public void setRequiredType(int type) {
		d.put("requiredType", type);
		
	}

	@Override
	public void setValueConstraints(String[] constraints) {
		List<String> ss = new ArrayList<String>(constraints.length);
		for(String s : constraints){
			ss.add(s);
		}
		
		d.put("valueConstraints", ss);
		
	}

}
