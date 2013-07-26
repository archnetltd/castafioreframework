package org.castafiore.agenda;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXAgendaDay extends EXContainer{

	public EXAgendaDay(String name, Calendar date, List<AgendaEvent> events) {
		super(name, "div");
		setStyle("overflow-y", "scroll");
		setStyle("height", "400px");
		setStyle("width", "393px");
		setStyle("float", "left");
		Container timerow =new EXContainer("", "div").addClass("timerow").setStyle("width", "375px").setStyle("height", "170px"); 
		addChild(timerow);
		
		Container day = new EXContainer("day", "div").setStyle("border", "none").setStyle("margin", "8px").addClass("time ui-state-default").setStyle("height", "150px").setStyle("width", "125px");
		day.addChild(new EXContainer("date", "h1").setAttribute("style", "color: silver; margin: 0 0 15px; font-size: 85px; padding: 5px 0pt 0pt 12px"));
		day.addChild(new EXContainer("complete", "label").setStyle("padding-left", "12px").setStyle("display", "block"));

		day.addChild(new EXContainer("year", "label").setStyle("padding-left", "12px"));
		
		timerow.addChild(day);
		
		
		
		
		timerow.addChild(new EXAgendaCalendar("calendar", date).setStyle("margin", "8px 0px"));
		
		EXContainer list = new EXContainer("list", "ul"); 
		addChild(list);
		
		list.setStyle("margin", "20px").setStyle("border", "solid 1px gray").setStyle("float", "left").setStyle("height", "186px").setStyle("width", "305px").setStyle("overflow-y", "scroll");
		
		
		setDate(date,events);
	}
	
	public void addEvent(AgendaEvent event){
		
		if(event == null){
			event = new AgendaEvent();
			event.setTitle("Le Boss");
			event.setLocation("Home");
			event.setAlert("15 Minutes before");
			event.setSecondAlert("5 Minutes before");
			event.setSummary("Le boss programme on MBC 1");
			event.setStartTime(Calendar.getInstance());
			event.setEndTime(Calendar.getInstance());
			
		}
		Container list = getDescendentByName("list");
		
		
		Container li = new EXContainer("evt", "li").setStyle("border-bottom", "solid 1px gray");
		
		EXContainer div = new EXXHTMLFragment("div", "templates/agenda/Event.xhtml");
		
		li.addChild(div);
		
		div.addChild(new EXContainer("title", "h5").setStyle("font-size", "14px").setStyle("margin-bottom", "6px").setText(event.getTitle()));
		
		String format = new SimpleDateFormat("hh:mm").format(event.getStartTime().getTime()) + " to " + new SimpleDateFormat("hh:mm").format(event.getEndTime().getTime());
		div.addChild(new EXContainer("time", "label").setStyle("color", "#616161").setText(format));
		div.addChild(new EXContainer("location", "label").setStyle("color", "#616161").setText(event.getLocation()));
		div.addChild(new EXContainer("alert", "label").setStyle("color", "#616161").setStyle("display", "block").setText(event.getAlert()));
		div.addChild(new EXContainer("secondAlert", "label").setStyle("color", "#616161").setStyle("display", "block").setText(event.getSecondAlert()));
		div.addChild(new EXContainer("notes", "p").setText(event.getSummary()));
		
		
		
		
		list.addChild(li);
	}
	
	public void setDate(Calendar calendar, List<AgendaEvent> events){
		SimpleDateFormat form = new SimpleDateFormat("dd");
		getDescendentByName("date").setText(form.format(calendar.getTime()));
		
		form = new SimpleDateFormat("yyyy");
		getDescendentByName("year").setText(form.format(calendar.getTime()));
		
		form = new SimpleDateFormat("EEEE MMM dd");
		getDescendentByName("complete").setText(form.format(calendar.getTime()));
		
		Container list = getDescendentByName("list");
		list.getChildren().clear();
		list.setRendered(false);
		
		
		for(AgendaEvent event : events){
			addEvent(event);
		}
		
	}
	
	

}
