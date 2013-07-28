package org.castafiore.agenda;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXDailyView extends EXContainer implements Event{
	
	
	

	public EXDailyView(String name, Calendar date) {
		super(name, "div");
		List<AgendaEvent> events = SpringUtil.getBeanOfType(AgendaService.class).getEvents(date, Calendar.DATE);
		addChild(new EXAgendaDay("day", date,events));
	
	}
	




	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		return true;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
