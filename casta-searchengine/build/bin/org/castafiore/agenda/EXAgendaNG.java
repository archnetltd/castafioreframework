package org.castafiore.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXTimeRangerPicker;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.js.JArray;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;

public class EXAgendaNG extends EXContainer implements Event{

	
	public EXAgendaNG(String name) {
		super(name, "div");
		addScript(ResourceUtil.getDownloadURL("classpath", "org/castafiore/agenda/fullcalendar.min.js"));
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/agenda/fullcalendar.css"));
		addEvent(this, MISC);
		setStyle("width", "900px");
		addChild(new EXButton("showMyAgenda", "My Agenda").addEvent(this, CLICK));
	}

	
	@Override
	public void onReady(ClientProxy proxy) {
		SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy hh:mm");
		super.onReady(proxy);
		JMap o = new JMap();
		o.put("header", new JMap().put("left", "prev,next,today").put("center", "title").put("right", "month,agendaWeek,agendaDay"));
		o.put("height", "600");
		o.put("editable", true);
		o.put("theme", true);
		
		List<AgendaEvent> events = SpringUtil.getBeanOfType(AgendaService.class).getEvents(Calendar.getInstance(), Calendar.MONTH);
		JArray arr = new JArray();
		for(AgendaEvent event : events){
			if(event.getStartTime().getTimeInMillis() < event.getEndTime().getTimeInMillis()){
				JMap jE = new JMap().put("title", event.getTitle()).put("start", new Var("new Date('" +format.format(event.getStartTime().getTime()) + "')" ));
				jE.put("end", new Var("new Date('" +format.format(event.getEndTime().getTime()) + "')"));
				jE.put("backgroundColor", event.getColor()).put("borderColor", event.getColor());
				jE.put("description", event.getSummary());
				jE.put("path", event.getAbsolutePath());
				jE.put("allDay ", false);
				arr.add(jE);
			}
		}
		o.put("events", arr);
		
		
		JMap oDayClick = new JMap();
		oDayClick.put("dayClick", "true");
		oDayClick.put("hour", new Var("date.getHours()"));
		oDayClick.put("minute", new Var("date.getMinutes()"));
		oDayClick.put("month", new Var("date.getMonth()"));
		oDayClick.put("year", new Var("date.getFullYear()"));
		oDayClick.put("date", new Var("date.getDate()"));
		
		
		ClientProxy dayClick = proxy.clone().makeServerRequest(oDayClick, this);
		
		o.put("dayClick", dayClick,"date", "allDay", "jsEvent", "view");
		
		
		JMap oClick = new JMap();
		oClick.put("path", new Var("calEvent.path"));
		oClick.put("eventClick", "true");
		ClientProxy eventClick = proxy.clone().makeServerRequest(oClick, this);
		o.put("eventClick", eventClick,"calEvent", "jsEvent", "view");
		
		JMap oDrop = new JMap();
		oDrop.put("path", new Var("calEvent.path"));
		oDrop.put("drop", "true");
		oDrop.put("dayDelta", new Var("dayDelta"));
		oDrop.put("minuteDelta", new Var("minuteDelta"));
		
		ClientProxy eventDrag = proxy.clone().makeServerRequest(oDrop, this);
		o.put("eventDrop", eventDrag,"calEvent", "dayDelta", "minuteDelta", "allDay", "revertFunc", "jsEvent", "ui", "view" );
		
		
		JMap oRes = new JMap();
		oRes.put("path", new Var("calEvent.path"));
		oRes.put("resize", "true");
		oRes.put("dayDelta", new Var("dayDelta"));
		oRes.put("minuteDelta", new Var("minuteDelta"));
		
		ClientProxy eventResize = proxy.clone().makeServerRequest(oRes, this);
		
		o.put("eventResize", eventResize,"calEvent", "dayDelta", "minuteDelta", "allDay", "revertFunc", "jsEvent", "ui", "view" );
		proxy.addMethod("fullCalendar", o);
		
	//	proxy.getDescendentByName("myAgenda").appendTo(new ClientProxy(".fc-header-right"));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("showMyAgenda")){
			EXAgenda a = new EXAgenda("sdfs");
			EXPanel panel = new EXPanel("as", "My Agenda");
			panel.setStyle("width", "417px");
			panel.setBody(a);
			getAncestorOfType(PopupContainer.class).addPopup(panel.setStyle("z-index", "4000"));
		}
		
		if(request.containsKey("eventClick")){
			String path = request.get("path");
			AgendaEvent event = (AgendaEvent)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			
			EXAgendaEventForm form = new EXAgendaEventForm("ff");
			form.setEvent(event);
			container.getAncestorOfType(PopupContainer.class).addPopup(form);
		}else if(request.containsKey("drop")){
			int minutes = Integer.parseInt(request.get("minuteDelta"));
			int days = Integer.parseInt(request.get("dayDelta"));
			String path = request.get("path");
			AgendaEvent event = (AgendaEvent)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			Calendar start = event.getStartTime();
			Calendar end = event.getEndTime();
			start.add(Calendar.DATE, days);
			start.add(Calendar.MINUTE, minutes);
			end.add(Calendar.DATE, days);
			end.add(Calendar.MINUTE, minutes);
			event.setStartTime(start);
			event.setEndTime(end);
			event.save();
			
		}else if(request.containsKey("resize")){
			int minutes = Integer.parseInt(request.get("minuteDelta"));
			int days = Integer.parseInt(request.get("dayDelta"));
			String path = request.get("path");
			AgendaEvent event = (AgendaEvent)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			Calendar end = event.getEndTime();
			end.add(Calendar.DATE, days);
			end.add(Calendar.MINUTE, minutes);
			event.setEndTime(end);
			event.save();
		}else if(request.containsKey("dayClick")){
			int month = Integer.parseInt(request.get("month"));
			int year = Integer.parseInt(request.get("year"));
			int date = Integer.parseInt(request.get("date"));
			int hour = Integer.parseInt(request.get("hour"));
			int minute = Integer.parseInt(request.get("minute"));
			EXAgendaEventForm form = new EXAgendaEventForm("ff");
			
			Calendar cs = Calendar.getInstance();
			cs.set(Calendar.YEAR, year);
			cs.set(Calendar.MONTH, month);
			cs.set(Calendar.DATE, date);
			cs.set(Calendar.HOUR, hour);
			cs.set(Calendar.MINUTE, minute);
			Date st = cs.getTime();
			
			Calendar ce = Calendar.getInstance();
			ce.setTime(st);
			ce.add(Calendar.MINUTE	,120);
			Date[] dates = new Date[]{st,ce.getTime()};
			form.getDescendentOfType(EXTimeRangerPicker.class).setValue(dates);
			container.getAncestorOfType(PopupContainer.class).addPopup(form);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	

}
