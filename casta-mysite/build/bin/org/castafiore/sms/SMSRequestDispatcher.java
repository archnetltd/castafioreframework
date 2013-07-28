package org.castafiore.sms;

import java.io.Writer;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class SMSRequestDispatcher {
	
	private PathMatcher matcher = new AntPathMatcher();
	
	private Map<String, String> configs = new ListOrderedMap();
	
	public void doDispatch(SMSRequest request, Writer writer)throws InvalidSMSLetConfigException{
		
		String toNumber = request.getTo();
		
		Iterator<String> patterns = configs.keySet().iterator();
		
		while(patterns.hasNext()){
			
			String pattern = patterns.next();
			
			if(matcher.match(pattern, toNumber)){
				
				String clazz = configs.get(pattern);
				try{
					
					SMSLet smsLet = (SMSLet)Thread.currentThread().getContextClassLoader().loadClass(clazz).newInstance();
					
					SMSResponse response = new SMSResponse(request, writer);
					
					smsLet.doService(request, response);
					
				}catch(Exception e){
					
					throw new InvalidSMSLetConfigException(e);
				}
			}
		}
	}

	public PathMatcher getMatcher() {
		return matcher;
	}

	public void setMatcher(PathMatcher matcher) {
		this.matcher = matcher;
	}

	public Map<String, String> getConfigs() {
		return configs;
	}

	public void setConfigs(Map<String, String> configs) {
		this.configs = configs;
	}
	
	
	
}
