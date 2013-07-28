package org.castafiore.community.ui.timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.persistence.Dao;
import org.castafiore.security.Group;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.wfs.Util;
import org.hibernate.criterion.Restrictions;

public class EXTimeTableTab extends EXContainer implements Event{

	public EXTimeTableTab(String name) {
		super(name, "div");
		addChild(new EXButton("populate", "Populate").addEvent(this, Event.CLICK));
		EXTable table = new EXTable("tt", new TimeTableModel());
		table.setCellRenderer(new TimeTableCellRenderer());
		addChild(new EXPagineableTable("table" ,table).setStyle("clear", "left").setStyle("padding", "10px 0"));
	}
	 
	private void populate(){
		try{
			
			List<Group> groups = SpringUtil.getSecurityService().getGroups();
			
			List<String> missing = new ArrayList<String>();
			
			List<TimeTable> tables = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createCriteria(TimeTable.class).add(Restrictions.eq("organization", Util.getLoggedOrganization())).list();
			
			for(Group g : groups){
				String name = g.getName();
				boolean present = false;
				for(TimeTable t : tables){
					if(t.getGrp().equals(name)){
						present = true;
						break;
					}
				}
				
				if(!present){
					missing.add(name);
				}
			}
			
			
			for(String mi : missing){
				TimeTable t = new TimeTable();
				t.setGrp(mi);
				t.setOrganization(Util.getLoggedOrganization());
				SpringUtil.getBeanOfType(Dao.class).getSession().save(t);
			}
			
			
			getDescendentOfType(EXTable.class).setModel(new TimeTableModel());
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		populate();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
