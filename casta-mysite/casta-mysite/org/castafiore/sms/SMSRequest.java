package org.castafiore.sms;

import java.io.Reader;
import java.io.Serializable;

import org.castafiore.sms.SMSSessionMonitor.SessionKey;

public class SMSRequest implements Serializable{
	
	private String from;
	
	private String to;
	
	private Reader reader;
	
	

	public SMSRequest(String from, String to, Reader reader) {
		super();
		this.from = from;
		this.to = to;
		this.reader = reader;
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

	public SMSSession getSession(){
		return SMSUtil.getContainer().getSMSSessionMonitor().createOrUpdateSession(this);
	}
	
	
	public SessionKey getKey(){
		return new SessionKey(from,to);
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}
	

}
