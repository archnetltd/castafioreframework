package org.castafiore.security;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.castafiore.spring.SpringUtil;

public class CastafioreWebListener implements HttpSessionListener {

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		
		
	}

	@Override
	public synchronized void sessionDestroyed(HttpSessionEvent event) {
		
		SpringUtil.getSecurityService().logout(event.getSession().getId());
		
	}

}
