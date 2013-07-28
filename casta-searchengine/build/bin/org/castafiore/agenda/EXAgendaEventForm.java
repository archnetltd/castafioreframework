package org.castafiore.agenda;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXSlider;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXTimePicker;
import org.castafiore.ui.ex.form.EXTimeRangerPicker;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXAgendaEventForm extends EXDynaformPanel implements Event{

	public EXAgendaEventForm(String name) {
		super(name, "Ajouter un evenement");
		addField("Title :", new EXInput("title"));
		addField("Location :", new EXInput("location"));
		addField("When :", new EXTimeRangerPicker("when"));
		DefaultDataModel<Object> model = new DefaultDataModel<Object>().addItem("5 minutes before", "15 minutes before", "30 minutes before", "1 hour before", "2 houres before", "1 day before", "2 days before", "On the day of event");
		addField("First Alerte :",new EXSelect("alert", model));
		addField("Second Alerte :",new EXSelect("secondAlert", model));
		
		DefaultDataModel<Object> repeatModel = new DefaultDataModel<Object>().addItem("Never", "Every day", "Every week", "Every two weeks", "Every month", "Every year");
		addField("Repeat :",new EXSelect("repeat", repeatModel));
		
		addField("Color :", new EXColorPicker("color"));
		
		addField("Memo", new EXTextArea("memo"));
		addButton(new EXButton("cancel", "Cancel"));
		addButton(new EXButton("save", "Save"));
		getDescendentByName("cancel").addEvent(CLOSE_EVENT, CLICK);
		getDescendentByName("save").addEvent(this, CLICK);
		setStyle("width", "500px");
		setStyle("z-index", "3000");
		
		ComponentUtil.iterateOverDescendentsOfType(this, StatefullComponent.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				if(!(c instanceof EXSlider)){
					c.setStyle("width", "375px");
					
					if(c.getName().equalsIgnoreCase("memo")){
						c.setStyle("height", "130px");
					}
					
					if(c instanceof EXDatePicker){
						c.setStyle("width", "80px");
					}
					if(c instanceof EXTimePicker){
						c.setStyle("width", "100px");
					}
				}
				
			}
		});
		
	}
	
	
	public void setEvent(AgendaEvent event){
		getField("title").setValue(event.getTitle());
		getField("location").setValue(event.getLocation());
		getField("repeat").setValue(event.getRepeats());
		getField("alert").setValue(event.getAlert());
		getField("secondAlert").setValue(event.getSecondAlert());
		
		Date[] dates = new Date[]{event.getStartTime().getTime(), event.getEndTime().getTime()};
		getField("when").setValue(dates);
		getField("memo").setValue(event.getSummary());
		getField("color").setValue(event.getColor());
		setAttribute("path", event.getAbsolutePath());
		
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		   
		
		String title = getField("title").getValue().toString();
		String location = getField("location").getValue().toString();
		String repeat = getField("repeat").getValue().toString();
		String firstAlert = getField("alert").getValue().toString();
		String secondAlert = getField("secondAlert").getValue().toString();
		
		Date[] dates = (Date[])getField("when").getValue();
		
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(dates[0]);
//		
		Calendar endate = Calendar.getInstance();
		endate.setTime(dates[1]);
		
		String memo = getField("memo").getValue().toString();
		String color = getField("color").getValue().toString();
		
		String path = getAttribute("path");
		AgendaEvent event = null;
		if(StringUtil.isNotEmpty(path)){
			event = (AgendaEvent)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		}else
			event =SpringUtil.getBeanOfType(AgendaService.class).createEvent(startDate,endate);
		
		event.setTitle(title);
		event.setLocation(location);
		event.setSummary(memo);
		event.setAlert(firstAlert);
		event.setSecondAlert(secondAlert);
		event.setRepeats(repeat);
		event.setColor(color);
		event.save();
		event.getParent().save();
		getRoot().getDescendentOfType(EXAgendaNG.class).setRendered(false);
		remove();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
