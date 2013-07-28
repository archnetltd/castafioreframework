package org.castafiore.agenda;

import java.util.Calendar;
import java.util.Map;

import jodd.datetime.JDateTime;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;

public class EXEvent extends EXContainer implements Event{

	public EXEvent(String name, AgendaEvent event) {
		super(name, "div");
		setStyle("cursor", "pointer");
		addEvent(this, CLICK);
		setStyle("float", "left");
		setEvent(event);
	}
	
	
	public void setEvent(AgendaEvent event){
		setAttribute("path", event.getAbsolutePath());
		Calendar start = event.getStartTime();
		Calendar end = event.getEndTime();
		
		float pixPerMinute = 35f/60f;
	
		long sMilli = start.getTimeInMillis();
		long eMilli = end.getTimeInMillis();
		
		long dMilli = eMilli - sMilli;
		
		int sMinute = start.get(Calendar.MINUTE);
		int eMinute = end.get(Calendar.MINUTE);
		if(dMilli > (1000)*60*60*24){
			//whole day
		}else{
			float startOff = (pixPerMinute * sMinute);
			float botOffset = pixPerMinute * (60-eMinute);
			float height;
			//setStyle("margin-top",  + "px");
			int startH = start.get(Calendar.HOUR);
			int endH = end.get(Calendar.HOUR);
			
			if(start.get(Calendar.AM_PM) == Calendar.PM){
				startH = startH + 12;
			}
			
			if(end.get(Calendar.AM_PM) == Calendar.PM){
				endH = endH + 12;
			}
			
			int hourDiff = endH - startH;
			height = ((hourDiff + 1) * 35) - (startOff + botOffset);
			setStyle("height", height + "px");
			setStyle("margin-top", startOff + "px");
			setStyle("margin-bottom", botOffset + "px");
			setAttribute("hrs", hourDiff + "");
		}
		setStyle("background-color", event.getColor());
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		AgendaEvent event = (AgendaEvent)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		EXAgendaEventForm form = new EXAgendaEventForm("ee");
		form.setEvent(event);
		container.getAncestorOfType(PopupContainer.class).addPopup(form);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
