package org.castafiore.sms;

public interface SMSContainer {
	
	public SMSRequestDispatcher getSMSRequestDispatcher();
	
	public SMSSessionMonitor getSMSSessionMonitor();

}
