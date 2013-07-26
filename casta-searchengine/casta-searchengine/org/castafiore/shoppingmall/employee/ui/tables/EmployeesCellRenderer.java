package org.castafiore.shoppingmall.employee.ui.tables;

import java.util.Map;

import org.castafiore.shoppingmall.employee.Employee;
import org.castafiore.shoppingmall.employee.ui.EXEmployeePanel;
import org.castafiore.shoppingmall.user.ui.settings.EXEmployeeForm;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;

public class EmployeesCellRenderer implements CellRenderer, Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Employee val = (Employee)model.getValueAt(column, row, page);
		
		if(column == 1){
			return new EXContainer("aa", "a").setAttribute("path", val.getAbsolutePath()).setAttribute("href", "#d").setText(val.getFirstName() + " " + val.getLastName()).addEvent(this, CLICK);
		}else if(column == 0){
			return new EXContainer("", "span").setText(val.getSubscriber());
		}else
			return new EXContainer("", "span").setText(val.getBasicSalary().toPlainString());
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Employee val = (Employee)model.getValueAt(column, row, page);
		
		if(column == 1){
			 component.setAttribute("path", val.getAbsolutePath()).setAttribute("href", "#d").setText(val.getFirstName() + " " + val.getLastName());
		}else if(column == 0){
			component.setText(val.getSubscriber());
		}else
			component.setText(val.getBasicSalary().toPlainString());
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String path = container.getAttribute("path");
		Employee emp = (Employee)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		EXEmployeePanel panel = new EXEmployeePanel("emp", emp);
		container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
