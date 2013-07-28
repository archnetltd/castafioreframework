package org.castafiore.security.sessions;

import java.io.Serializable;
import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;

public class LoggedSession implements Serializable{
	
	private String sessionId;
	
	private String username;
	
	private String fullName;
	
	private String ipAddress;
	
	private Calendar timeLogin;
	
	private Object context;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Calendar getTimeLogin() {
		return timeLogin;
	}

	public void setTimeLogin(Calendar timeLogin) {
		this.timeLogin = timeLogin;
	}
	
	
	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public boolean equals(Object o){
		return EqualsBuilder.reflectionEquals(o, this, new String[]{"timeLogin"});
	}

}
