package org.castafiore.agenda;

import java.util.Calendar;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.utils.DateUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.Directory;
import org.hibernate.criterion.Restrictions;

public class AgendaServiceImpl implements AgendaService {
	
	
	public List<AgendaEvent> getEvents(Calendar date, int calendarField){
		String dir = "/root/users/" + Util.getRemoteUser() + "/agenda";
		Directory d = null;
		if(!SpringUtil.getRepositoryService().itemExists(dir)){
			Directory us = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser(), Util.getRemoteUser());
			d= us.createFile("agenda",Directory.class);
			d.save();
			
		}
		
		Calendar startDate = (Calendar)date.clone();
		Calendar endDate = (Calendar)date.clone();
		
		if(calendarField == Calendar.YEAR){
			startDate.set(Calendar.MONTH, Calendar.JANUARY);
			startDate.set(Calendar.DATE, 1);
			startDate.set(Calendar.HOUR, 1);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			startDate.set(Calendar.AM_PM, Calendar.AM);
			
			
			endDate.set(Calendar.DATE, 31);
			endDate.set(Calendar.MONTH, Calendar.DECEMBER);
			endDate.set(Calendar.HOUR, 11);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			endDate.set(Calendar.AM_PM, Calendar.PM);
			
		}else if(calendarField == Calendar.MONTH){
			startDate.set(Calendar.DATE, 1);
			startDate.set(Calendar.HOUR, 1);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			startDate.set(Calendar.AM_PM, Calendar.AM);
			
			
			endDate.set(Calendar.DATE, DateUtil.getDaysInMonth(date.get(Calendar.MONTH), date.get(Calendar.YEAR)));
			endDate.set(Calendar.HOUR, 11);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			endDate.set(Calendar.AM_PM, Calendar.PM);
			
		
			
			
		}else if(calendarField == Calendar.DATE){
			
			startDate.set(Calendar.HOUR, 1);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			startDate.set(Calendar.AM_PM, Calendar.AM);
			
			
			endDate.set(Calendar.HOUR, 11);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			//endDate.set(Calendar.MILLISECOND, 999);
			endDate.set(Calendar.AM_PM, Calendar.PM);
		}else if(calendarField== Calendar.HOUR){
			
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			startDate.set(Calendar.AM_PM, Calendar.AM);
			
			
			
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			endDate.set(Calendar.AM_PM, Calendar.PM);
		}else if(calendarField == Calendar.WEEK_OF_MONTH){
			int currentDay = date.get(Calendar.DAY_OF_WEEK)-1;
			startDate.add(Calendar.DATE, currentDay*-1);
			startDate.set(Calendar.HOUR, 1);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			startDate.set(Calendar.AM_PM, Calendar.AM);
			
			
			endDate = (Calendar)startDate.clone();
			endDate.add(Calendar.DATE, 6);
			endDate.set(Calendar.HOUR, 11);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			endDate.set(Calendar.AM_PM, Calendar.PM);
		}
		
		
		QueryParameters params  = new QueryParameters().setEntity(AgendaEvent.class).addSearchDir(dir);
		params.addRestriction(Restrictions.ge("startTime", startDate)).addRestriction(Restrictions.le("endTime", endDate));
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		
		return result;
	}

	@Override
	public AgendaEvent createEvent(Calendar dateCalendar, Calendar endDate) {
		String dir = "/root/users/" + Util.getRemoteUser() + "/agenda";
		Directory d = null;
		if(!SpringUtil.getRepositoryService().itemExists(dir)){
			Directory us = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser(), Util.getRemoteUser());
			d= us.createFile("agenda",Directory.class);
			d.save();
			
			d= d.createFile(dateCalendar.get(Calendar.YEAR) + "",Directory.class);
			d= d.createFile(dateCalendar.get(Calendar.MONTH) + "",Directory.class);
			d.getParent().save();
			
			AgendaEvent ev = d.createFile(dateCalendar.getTime().getTime() + "", AgendaEvent.class);
			ev.setStartTime(dateCalendar);
			ev.setEndTime(endDate);
			 return ev;
			
		}
		dir = dir + "/" + dateCalendar.get(Calendar.YEAR);
		
		if(!SpringUtil.getRepositoryService().itemExists(dir)){
			Directory us = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser() + "/agenda", Util.getRemoteUser());
			d= us.createFile(dateCalendar.get(Calendar.YEAR) + "",Directory.class);
			d= d.createFile(dateCalendar.get(Calendar.MONTH) + "",Directory.class);
			d.getParent().save();
			
			AgendaEvent ev = d.createFile(dateCalendar.getTime().getTime() + "", AgendaEvent.class);
			ev.setStartTime(dateCalendar);
			ev.setEndTime(endDate);
			 return ev;
			
		}
		
		
		dir = dir + "/" + dateCalendar.get(Calendar.MONTH);
		
		if(!SpringUtil.getRepositoryService().itemExists(dir)){
			Directory us = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser() + "/agenda/" + dateCalendar.get(Calendar.YEAR) , Util.getRemoteUser());
			d= us.createFile(dateCalendar.get(Calendar.MONTH) + "",Directory.class);
			d.save();
			
			AgendaEvent ev = d.createFile(dateCalendar.getTime().getTime() + "", AgendaEvent.class);
			ev.setStartTime(dateCalendar);
			ev.setEndTime(endDate);
			 return ev;
		}
		
		 d = SpringUtil.getRepositoryService().getDirectory("/root/users/" + Util.getRemoteUser() + "/agenda/" + dateCalendar.get(Calendar.YEAR) + "/" + dateCalendar.get(Calendar.MONTH), Util.getRemoteUser());
		 AgendaEvent ev = d.createFile(System.currentTimeMillis() + "", AgendaEvent.class);
		 ev.setStartTime(dateCalendar);
		 ev.setEndTime(endDate);
		 return ev;
	}

}
