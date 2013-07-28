package org.castafiore.inventory.suppliers;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;

public class SupplierCellRenderer implements CellRenderer, Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Supplier c = (Supplier)model.getValueAt(column, row, page);
		Container result = new  EXContainer("span", "span");
		if(column == 0){
			return result.setText(c.getCode());
		}else if(column == 1){
			result = new EXContainer("span", "a").setAttribute("href", "#").setAttribute("username", c.getUsername()).addEvent(this, Event.CLICK);
			return result.setText(c.getFirstName() + " " + c.getLastName());
		}else if(column == 2){
			return result.setText("0");
		}
		return result;
	}

	@Override
	public void onChangePage(Container result, int row, int column,
			int page, TableModel model, EXTable table) {
		Supplier c = (Supplier)model.getValueAt(column, row, page);
		
		if(column == 0){
			result.setText(c.getCode());
		}else if(column == 1){
			result.setText(c.getFirstName() + " " + c.getLastName()).setAttribute("username", c.getUsername());
		}else if(column == 2){
			result.setText("0");
		}
		
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			BSupplierForm form = new BSupplierForm("editCustomer");
			form.setSupplier((Supplier)SpringUtil.getSecurityService().loadUserByUsername(container.getAttribute("username")));
			container.getAncestorOfType(EXPanel.class).addChild(form);
		}catch(Exception e){
			throw new UIException(e);
		}
			
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
