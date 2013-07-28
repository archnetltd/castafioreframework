package org.castafiore.agenda;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public interface AgendaService extends Serializable{
	
	public List<AgendaEvent> getEvents(Calendar date, int calendarField);
	
	
	public AgendaEvent createEvent(Calendar dateCalendar, Calendar endDate);

}
