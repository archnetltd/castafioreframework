package org.castafiore.security.logs;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.utils.StringUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class EXLogSearch extends EXFieldSet{

	public EXLogSearch() {
		super("EXLogSearch", "Search Logs",false);
		addField("From :", new EXDatePicker("from"));
		addField("To :", new EXDatePicker("to"));
		addField("User :", new EXInput("user"));
		addField("Type :", new EXInput("type"));
		addField("Severity :", new EXInput("severity"));
	}
	
	public List<Log> search(){
		Date from = (Date)getField("from").getValue();
		Date to = (Date)getField("to").getValue();
		String user = getField("user").getValue().toString();
		String type = getField("type").getValue().toString();
		String severity = getField("severity").getValue().toString();
		
		Criteria crit = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createCriteria(Log.class);
		
		Calendar cfrom = Calendar.getInstance();
		cfrom.setTime(from);
		Calendar cto = Calendar.getInstance();
		cto.setTime(to);
		
		crit.add(Restrictions.between("time", cfrom, cto));
		if(StringUtil.isNotEmpty(user)){
			crit.add(Restrictions.like("user", "%" + user + "%"));
		}
		if(StringUtil.isNotEmpty(severity)){
			crit.add(Restrictions.like("severity", "%" + severity + "%"));
		}
		if(StringUtil.isNotEmpty(type)){
			crit.add(Restrictions.like("type", "%" + type + "%"));
		}
		
		return crit.list();
	}

}
