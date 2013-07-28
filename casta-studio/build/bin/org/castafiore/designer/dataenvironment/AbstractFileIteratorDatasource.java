package org.castafiore.designer.dataenvironment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.wfs.types.File;



public abstract  class AbstractFileIteratorDatasource implements Datasource {
	
	private Map<String, String> attributes = new HashMap<String, String>(3);
	
	private String factoryId;
	
	private String name;
	
	protected List<File> data = null;
	
	protected String log;
	
	
	
	public AbstractFileIteratorDatasource(String name, String factoryId) {
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
		
		return true;
	}

	@Override
	public Datasource close() {
		data = null;
		return this;
	}

	@Override
	public DataSet getDataSet(){
		return new FileIteratorDataSet(this, data, getEntity());
	}

	public abstract Class getEntity();
	@Override
	public boolean isOpen() {
		return data!=null;
	}

	@Override
	public Datasource open() {
		if(!isOpen()){
			data = doLoadData();
		}
		return this;
	}
	
	public abstract List<File> doLoadData();

	@Override
	public Datasource reset() {
		return close().open();
	}

	
	public String getLog(){
		return log;
	}
}
