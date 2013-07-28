package org.castafiore.sms;

import java.io.IOException;

public abstract class SMSLet {
	
	public abstract void doService(SMSRequest request, SMSResponse response)throws SMSException, IOException;

}
