package org.castafiore.sms;

import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SMSSessionMonitor implements Serializable{
	
	private Map<SessionKey, SMSSession> sessions = new HashMap<SessionKey, SMSSession>();
	
	private int timeout = 1000*60*30;//30 mins
	
	
	public boolean hasSession(SMSRequest request){
		SessionKey key = request.getKey();
		return sessions.containsKey(key);
	}
	
	
	public SMSSession createOrUpdateSession(SMSRequest request){
		SessionKey key = request.getKey();
		if(sessions.containsKey(key)){
			sessions.get(key).setDateAccessed(Calendar.getInstance());
			return sessions.get(key);
		}else{
			SMSSession session = new SMSSession();
			sessions.put(key, session);
			return session;
		}
	}
	
	
	
	public int getTimeout() {
		return timeout;
	}


	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}


	public void doCleanUp(){
		Iterator<SessionKey> iterKeys = sessions.keySet().iterator();
		long currentTime = Calendar.getInstance().getTime().getTime();
		List<SessionKey> toRemove = new java.util.LinkedList<SessionKey>();
		while(iterKeys.hasNext()){
			SessionKey key = iterKeys.next();
			SMSSession session = sessions.get(key);
			
			long lastAccess = session.getDateAccessed().getTime().getTime();
			if((lastAccess + timeout) > currentTime){
				toRemove.add(key);
			}
		}
		
		for(SessionKey key : toRemove){
			sessions.remove(key);
		}
	}
	
	public static class SessionKey{
		
		private String from;
		
		private String to;
		
		public SessionKey(String from, String to) {
			super();
			this.from = from;
			this.to = to;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}
		
		public boolean equals(Object o){
			if(o instanceof SessionKey){
				SessionKey sk = (SessionKey)o;
				return from.equals(sk.from) && to.equals(sk.to);
			}
			return false;
		}
	}

}
