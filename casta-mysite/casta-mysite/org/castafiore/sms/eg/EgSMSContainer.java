package org.castafiore.sms.eg;

import org.castafiore.sms.SMSContainer;
import org.castafiore.sms.SMSRequestDispatcher;
import org.castafiore.sms.SMSSessionMonitor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class EgSMSContainer implements SMSContainer {
	
	private  static ApplicationContext _ctx = null;
	
	static{
		_ctx = new ClassPathXmlApplicationContext("org/castafiore/sms/config.xml");
	}

	public SMSRequestDispatcher getSMSRequestDispatcher() {
		return _ctx.getBean(SMSRequestDispatcher.class);
	}

	public SMSSessionMonitor getSMSSessionMonitor() {
		return _ctx.getBean(SMSSessionMonitor.class);
	}

}
