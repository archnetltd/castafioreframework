package org.castafiore.shoppingmall.employee.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.employee.TimesheetEntry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class ProjectTimesheetEntries {
	
	
	private String project;
	
	private List<TimesheetEntry> entries = new ArrayList<TimesheetEntry>();
	
	private Calendar startDate;
	
	private Calendar endDate;
	
	private String userid_;

	public ProjectTimesheetEntries(String project, Calendar startDate,
			Calendar endDate, String userid) {
		super();
		this.project = project;
		this.startDate = startDate;
		this.endDate = endDate;
		userid_ = userid;
		
		
		QueryParameters params = new QueryParameters();
		params.setEntity(TimesheetEntry.class).addRestriction(Restrictions.between("date", startDate, endDate));
		params.addSearchDir(MallUtil.getCurrentMerchant().getAbsolutePath() + "/employees/" + userid);
		//params.addRestriction(Restrictions.eq("owner", userid_));
		params.addRestriction(Restrictions.eq("project", project));
		params.addOrder(Order.asc("date"));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		
		entries = result;
	}

	public List<TimesheetEntry> getEntries() {
		return entries;
	}
	
	
	public TimesheetEntry getEntry(Calendar date){
		for(TimesheetEntry entry : entries){
			if((entry.getDate().get(Calendar.DATE) == date.get(Calendar.DATE)) && (entry.getDate().get(Calendar.MONTH) == date.get(Calendar.MONTH)))
				return entry;
		}
		
		return null;
	}

	public String getProject() {
		return project;
	}
	
	
	

}
