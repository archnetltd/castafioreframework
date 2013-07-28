package org.castafiore.sms;

import java.util.Calendar;
import java.util.HashMap;

public class SMSSession extends HashMap<String, Object>{
	
	private Calendar dateAccessed = Calendar.getInstance();

	public Calendar getDateAccessed() {
		return dateAccessed;
	}

	public void setDateAccessed(Calendar dateAccessed) {
		this.dateAccessed = dateAccessed;
	}

		
	

}
