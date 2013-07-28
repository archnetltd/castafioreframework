package org.castafiore.community.ui.timetable;

import java.util.Map;

import org.castafiore.persistence.Dao;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class TimeTableCellRenderer implements CellRenderer, Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Object value = model.getValueAt(column, row, page);
		if(value == null){
			value = "";
		}
		String grp = model.getValueAt(0, row, page).toString();
		if(column == 0){
			return new EXContainer("gp", "label").setText(value.toString());
		}else{
			return new EXContainer("", "div").setAttribute("column", column+"").setAttribute("group", grp).setStyle("width", "60px").setStyle("height", "20px").setText(value.toString()).addEvent(this, CLICK);
		}
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		int column = Integer.parseInt(container.getAttribute("column"));
		String group = container.getAttribute("group");
		
		if(!(container instanceof StatefullComponent)){
			EXInput input = new EXInput("");
			input.setAttribute("column", column + "").setAttribute("group", group).setStyle("width", "60px");
			input.setValue(container.getText(false));
			container.setText("").addChild(input.addEvent(this, BLUR));
			container.getEvents().clear();
		}else{
			String value = ((StatefullComponent)container).getValue().toString();
			Criteria crit = SpringUtil.getBeanOfType(Dao.class).getReadOnlySession().createCriteria(TimeTable.class);
			TimeTable t = (TimeTable)crit.add(Restrictions.eq("grp", group)).add(Restrictions.eq("organization", Util.getLoggedOrganization())).uniqueResult();
			if(column ==1){
				t.setMon(value);
			}else if(column == 2){
				t.setTue(value);
			}else if(column == 3){
				t.setWed(value);
			}else if(column == 4){
				t.setThur(value);
			}else if(column == 5){
				t.setFri(value);
			}else if(column == 6){
				t.setSat(value);
			}else if(column == 7){
				t.setSun(value);
			}
			
			SpringUtil.getBeanOfType(Dao.class).getSession().saveOrUpdate(t);
			
			
			Container parent = container.getParent();
			container.remove();
			parent.setText(value);
			parent.addEvent(this, CLICK);
		}
		return true;
		
		
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
