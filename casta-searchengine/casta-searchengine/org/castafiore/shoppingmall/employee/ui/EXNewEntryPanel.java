package org.castafiore.shoppingmall.employee.ui;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.shoppingmall.employee.TimesheetEntry;
import org.castafiore.shoppingmall.employee.ui.tables.EXTimesheetEntry;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXTimePicker;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Restrictions;

public class EXNewEntryPanel extends EXDynaformPanel implements Event{

	
	private EXTimesheetEntry entry_;
	
	private String userid_;
	
	public EXNewEntryPanel(String name, EXTimesheetEntry entry, String userid) {
		super(name, "Timesheet Entry");
		this.entry_ = entry;
		this.userid_ = userid;
	
		
	
		addField("From :", new EXTimePicker("from", entry.getEntry()!=null? entry.getEntry().getStartTime().getTime():new Date() ));
		addField("To :", new EXTimePicker("to",entry.getEntry()!=null?entry.getEntry().getEndTime().getTime() :new Date()));
		addField("Title :", new EXInput("title",  entry.getEntry()!=null? entry.getEntry().getTitle() :""));
		addField("Description :", new EXTextArea("description", entry.getEntry ()!=null?entry.getEntry().getSummary() :""));
		
		getDescendentOfType(EXTextArea.class).setStyle("width", "350px").setStyle("height", "75px");
		addButton(new EXButton("save", "Save"));
		addButton(new EXButton("saveNext", "Save And Next"));
		addButton(new EXButton("saveNext10", "Pass for next 10"));
		addButton(new EXButton("cancel", "Cancel"));
		getDescendentByName("save").addEvent(this, Event.CLICK);
		getDescendentByName("saveNext").addEvent(this, Event.CLICK);
		getDescendentByName("saveNext10").addEvent(this, Event.CLICK);
		getDescendentByName("cancel").addEvent(Panel.CLOSE_EVENT, Event.CLICK); 
		
	}
	
	public void setEntry(EXTimesheetEntry entry){
		getField("from").setValue(entry.getEntry()!=null? entry.getEntry().getStartTime().getTime():new Date() );
		getField("to").setValue(entry.getEntry()!=null?entry.getEntry().getEndTime().getTime() :new Date());
		getField("title").setValue(  entry.getEntry()!=null? entry.getEntry().getTitle() :"");
		getField("description").setValue(entry.getEntry ()!=null?entry.getEntry().getSummary() :"");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Date from = (Date)getField("from").getValue();
		Date  to = (Date)getField("to").getValue();
		Calendar cfrom = Calendar.getInstance();
		cfrom.setTime(from);
		Calendar cto = Calendar.getInstance();
		cto.setTime(to);
		if(container.getName().equalsIgnoreCase("save") || container.getName().equalsIgnoreCase("saveNext") || container.getName().equalsIgnoreCase("saveNext10")){
			try{
				
				
				entry_.save(cfrom,cto);
				
			}catch(Exception e){
				throw new UIException(e);
			}
		}if(container.getName().equalsIgnoreCase("saveNext")){
			EXTimesheetEntry next = entry_.getNextEntry();
			if(next != null){
				setEntry(next);
			}else{
				request.put("nomore", "true");
			}
		}else if(container.getName().equalsIgnoreCase("saveNext10")){
			EXTimesheetEntry next = entry_.getNextEntry();
			for(int i = 1; i <= 10; i ++){
				next = next.getNextEntry();
				
				if(next != null){
					next.save(cfrom, cto);
					setEntry(next);
				}else
					break;
						
			}
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("nomore")){
			container.alert("No more entry");
		}
		
	}

}
