package org.castafiore.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.form.EXCalendar;

public class EXAgendaCalendar extends EXCalendar {

	public EXAgendaCalendar(String name, Calendar date) {
		super(name);
		setStyle("width", "145px").setStyle("float", "left");
		addClass("agendacal");
		setStyle("width", "234px");
	}

	@Override
	public void onSelectDate(String dateText) {
		try{
		super.onSelectDate(dateText);
		
		Date date = new SimpleDateFormat("dd MM yyyy").parse(dateText);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		List<AgendaEvent> events = SpringUtil.getBeanOfType(AgendaService.class).getEvents(cal, Calendar.DATE);
		
		getAncestorOfType(EXDailyView.class).getDescendentOfType(EXAgendaDay.class).setDate(cal, events);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	
	

}
