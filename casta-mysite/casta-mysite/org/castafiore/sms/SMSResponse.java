package org.castafiore.sms;

import java.io.Serializable;
import java.io.Writer;

public class SMSResponse implements Serializable {

	private Writer writer;

	private SMSRequest request;

	public SMSResponse(SMSRequest request, Writer writer) {
		super();
		this.request = request;
		this.writer = writer;
	}

	public SMSRequest getRequest() {
		return request;
	}

	public Writer getWriter() {
		return writer;
	}
	
	public void setResponse(String response){
		try{
			getWriter().write(response.toCharArray());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

}
