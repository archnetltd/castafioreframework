package org.castafiore.shoppingmall.employee.ui.tables;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.shoppingmall.employee.TimesheetEntry;
import org.castafiore.shoppingmall.employee.ui.EXNewEntryPanel;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXTimesheetEntry extends EXContainer implements Event{

	private TimesheetEntry entry_;
	
	private String userid_;
	
	public EXTimesheetEntry(String name, TimesheetEntry entry, String userid) {
		super(name, "div");
		setEntry(entry);
		addEvent(this, CLICK)	;
		setStyle("width", "22px").setStyle("text-align", "center");
		this.userid_ = userid;
	}	
	
	
	public void setEntry(TimesheetEntry entr){
		if(entr != null && entr.getStartTime() != null){
		String ss = MyTimesheetCellRenderer.hhmm.format(entr.getStartTime().getTime()) + " - " + MyTimesheetCellRenderer.hhmm.format(entr.getEndTime().getTime());
		setText(ss);
		}else{
			setText("| - |");
		}
		this.entry_ = entr;
	}
	
	
	public void setStartEndTime(Calendar start, Calendar end){
		if(entry_ != null){
			entry_.setStartTime(start);
			entry_.setEndTime(end);
			entry_.save();
		}else{
			
		}
	}
	
	public void save( Calendar cfrom, Calendar cto){
		try{
			Merchant m = MallUtil.getCurrentMerchant();
			Employee emp=m.getEmployee(userid_);
			if(emp == null){
				User u = new User();
				u.setUsername(userid_);
				u.setPassword(userid_);
				u.setOrganization(m.getUsername());
				
				SpringUtil.getSecurityService().saveOrUpdateUser(u);
				emp  = m.hireUser(u, "", "", "", "", BigDecimal.ZERO, "", "", "");
			}
			Calendar date_ = null;
			date_ = Calendar.getInstance();
			date_.setTimeInMillis(Long.parseLong(getAttribute("time")));
			TimesheetEntry tentry = emp.saveOrUpdateEntry(date_, cfrom, cto, cfrom, cto,getAttribute("project"));
			setEntry(tentry);
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	
	
	public EXTimesheetEntry getNextEntry(){
		Container td = getParent();
		Container tr = td.getParent();
		
		int index = tr.getChildren().indexOf(td) + 1;
		
		if(index < tr.getChildren().size()){
			return tr.getChildByIndex(index).getDescendentOfType(EXTimesheetEntry.class);
		}else{
			return null;
			
			
		}
		
	}


	public TimesheetEntry getEntry() {
		return entry_;
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXNewEntryPanel panel = new EXNewEntryPanel("", this, userid_);
		panel.setStyle("width", "500px").setStyle("z-index", "6000");
		container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		return true;
	}
	
	
	


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
