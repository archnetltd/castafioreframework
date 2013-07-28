package org.castafiore.designer.dataenvironment.invocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;

public class MethodInvocationDatasource implements Datasource{

private Map<String, String> attributes = new HashMap<String, String>(3);
	
	private String factoryId;
	
	private String name;
	
	private String log = "";
	
	
	
	public MethodInvocationDatasource(String name, String factoryId) {
		super();
		this.factoryId = factoryId;
		this.name = name;
	}

	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public String getFactoryId() {
		return factoryId;
	}

	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public Datasource setAttribute(String key, String value) {
		attributes.put(key, value);
		return this;
	}

	@Override
	public Datasource setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public boolean cacheable() {
		
		return false;
	}

	@Override
	public Datasource close() {
		return this;
	}

	@Override
	public DataSet getDataSet(){
		
		String attr = getAttribute("Service");
		String method = getAttribute("Method");
		//String 
		if(attr==null || method == null)
			log = log + ("<br>Return value of " + attr + "." + method + " should be of type java.util.List");
			
		
		
		
		Object service = SpringUtil.getBean(attr);
		if(service == null){
			log = log + ("<br>The bean " + attr + " is not configured in context");
		}
		Class c = service.getClass();
		try{
			Object o = c.getMethod(method).invoke(service);
			if(o instanceof List){
				return new MethodInvocationDataSet(this, (List)o);
			}else{
				log = log + ("<br>Return value of " + attr + "." + method + " should be of type java.util.List");
			}
		}catch(Exception e){
			log = log + ("<br>" + e.getMessage());
		}
		
		return null;
		
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public Datasource open() {
		return this;
	}

	@Override
	public Datasource reset() {
		return this;
	}

	@Override
	public String getLog() {
		return log;
	}

}
