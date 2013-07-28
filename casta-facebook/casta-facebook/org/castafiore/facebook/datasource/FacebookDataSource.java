package org.castafiore.facebook.datasource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.designer.dataenvironment.DataSet;
import org.castafiore.designer.dataenvironment.Datasource;
import org.castafiore.utils.StringUtil;

import com.restfb.DefaultLegacyFacebookClient;
import com.restfb.Parameter;
import com.restfb.types.NamedFacebookType;

public class FacebookDataSource implements Datasource{
	
	private String factoryId;
	
	private String name;
	
	private boolean cacheable = true;
	
	private Map<String,String> attributes = new HashMap<String, String>(4);
	
	private List<NamedFacebookType> cache = new ArrayList<NamedFacebookType>();
	
	private String log;
	
	public FacebookDataSource(String factoryId, String name) {
		super();
		this.factoryId = factoryId;
		this.name = name;
	}

	@Override
	public boolean cacheable() {
		return cacheable;
	}

	@Override
	public Datasource close() {
		try{
		 cache.clear();
		}catch(Exception e){
			log = e.getMessage();
		}
		return this;
	}

	@Override
	public String getAttribute(String key) {
		return attributes.get(key);
	}

	@Override
	public DataSet getDataSet() {
		return new FacebookDataSet(this, cache);
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
	public boolean isOpen() {
		return cache.size() > 0;
	}

	@Override
	public Datasource open() {
		try{
			String token = getAttribute("AccessToken");
			String fql = getAttribute("Query");
			String clazz = getAttribute("Type");
			
			if(StringUtil.isNotEmpty(token) && StringUtil.isNotEmpty(fql) && StringUtil.isNotEmpty(clazz)){
				Class<NamedFacebookType> cla = (Class<NamedFacebookType>)Thread.currentThread().getContextClassLoader().loadClass(clazz);
				DefaultLegacyFacebookClient client = new DefaultLegacyFacebookClient(token);
				cache =client.executeForList("fql.query", cla, Parameter.with("query", fql));
			}
			
			
			
		}catch(Exception e){
			log = log + "<br>" + e.getMessage();
		}
		
		return this;
		
	}

	@Override
	public Datasource reset() {
		return close();
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
	public String getLog() {
		return log;
	}

}
