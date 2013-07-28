package org.castafiore.security.logs;

import java.util.Calendar;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.wfs.Util;

public class Logger {
	
	public static void log(String type, String severity){
		Log log = new Log();
		log.setTime(Calendar.getInstance());
		log.setUser(Util.getRemoteUser());
		log.setType(type);
		log.setSeverity(severity);
		log.setIpaddress(CastafioreApplicationContextHolder.getCurrentApplication().getRemoteAddress());
		SpringUtil.getBeanOfType(Dao.class).getSession().save(log);
	}

}
