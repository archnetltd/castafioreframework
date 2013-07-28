package org.castafiore.sms;

import org.castafiore.sms.eg.EgSMSContainer;

public class SMSUtil {
	
	
	private static SMSContainer ctn = null;
	static{
		ctn = new EgSMSContainer();
	}
	
	public static SMSContainer getContainer(){
		return ctn;
	}

}
