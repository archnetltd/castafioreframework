package org.castafiore.inventory.product;

import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.inventory.customers.BCustomerForm;
import org.castafiore.inventory.customers.Customer;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.Util;

public class ProductCellRenderer implements CellRenderer, Event{

	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Product p = (Product)model.getValueAt(column, row, page);
		Container result = new  EXContainer("span", "span");
		if(column == 1){
			return result.setText(p.getCode());
		}else if(column == 2){
			result = new EXContainer("span", "a").setAttribute("href", "#").setAttribute("path", p.getAbsolutePath()).addEvent(this, Event.CLICK);
			return result.setText(p.getTitle());
		}else if(column == 3){
			return result.setText(p.getTotalPrice()!=null? p.getTotalPrice().toString():"0");
		}else if(column == 4){
			return result.setText( p.getCurrentQty()!=null? p.getCurrentQty().toString():"0");
		}else if(column == 0){
			return new EXCheckBox("span").setAttribute("path", p.getAbsolutePath());
		}
		
		return result;
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		
		Product p = (Product)model.getValueAt(column, row, page);
		
		if(column == 1){
			component.setText(p.getCode());
		}else if(column == 2){
			component.setText(p.getTitle()).setAttribute("path", p.getAbsolutePath());
		}else if(column == 3){
			component.setText(p.getTotalPrice()!=null? p.getTotalPrice().toString():"0");
		}else if(column == 4){
			component.setText(p.getCurrentQty()!=null? p.getCurrentQty().toString():"0");
		}else if(column == 0){
			component.setAttribute("path", p.getAbsolutePath());
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
			BProductForm form = new BProductForm("productForm");
			form.setProduct((Product)SpringUtil.getRepositoryService().getFile(container.getAttribute("path"), Util.getRemoteUser()));
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
