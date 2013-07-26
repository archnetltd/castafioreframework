package org.castafiore.shoppingmall.checkout;

import java.text.SimpleDateFormat;
import java.util.Map;

import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.orders.EXOrdersPanel;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;

public class OrdersListCellRenderer implements CellRenderer, Event{


	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		OrderInfo order = (OrderInfo)model.getValueAt(column, row, page);
		Container c = new EXContainer("", "div");
		if(column == 0){
			c= new EXContainer("td_inv", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("inv","a").setAttribute("row", row + "").setAttribute("page", page + "").addEvent(this, CLICK).setAttribute("href", "#").setText(order.getFsCode()).setStyle("font-weight", "bold").setStyle("color", "#111"));
		}else if(column == 1){
			c= new EXContainer("td_date", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("date","div").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction())));
		}else if(column == 2){
			c= new EXContainer("td_customer", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("customer", "label").setStyle("font-weight", "bold").setStyle("color", "#111").setText(order.getFirstName() + " " + order.getLastName()));
		}else if(column == 3){
			c= new EXContainer("td_status", "div").setAttribute("status", order.getStatus() + "").setStyle("vertical-align","top" ).addChild(new EXContainer("status", "div").setStyle("font-weight", "bold").setStyle("color", "#111").setText(SpringUtil.getBeanOfType(OrdersWorkflow.class).getStatus(order.getStatus())));
		}else if(column == 4){
			c= new EXContainer("td_total", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("total","div").setText(StringUtil.toCurrency("MUR",order.getTotal())));
		}
		
		
		
		return c;
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		OrderInfo order = (OrderInfo)model.getValueAt(column, row, page);
		Container c = component;
		if(column == 0){
			component.getDescendentByName("inv").setText(order.getFsCode()).setAttribute("row", row + "").setAttribute("page", page + "");
			
		}else if(column == 1){
			component.getDescendentByName("date").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));
		}else if(column == 2){
			component.getDescendentByName("customer").setText(order.getFirstName() + " " + order.getLastName());
		}else if(column == 3){
			component.setAttribute("status", order.getStatus() + "");
			component.getDescendentByName("status").setText(SpringUtil.getBeanOfType(OrdersWorkflow.class).getStatus(order.getStatus()));
		}else if(column == 4){
			component.getDescendentByName("total").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTotal()));
		}
		
		
		c.setStyle("background-color", SpringUtil.getBeanOfType(OrdersWorkflow.class).getColor(order.getStatus()));
		
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	public OrderInfo getItem(Container c) {
		TableModel model = c.getAncestorOfType(EXTable.class).getModel();
		int page = Integer.parseInt(c.getAttribute("page"));
		int row = Integer.parseInt(c.getAttribute("row"));
		
		
		OrderInfo info = (OrderInfo)model.getValueAt(0, row, page);
		return info;
	}
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("customer")){
			//go to customer card
		}else if(container.getName().equalsIgnoreCase("inv")){
			EXOrdersPanel panel =container. getAncestorOfType(EXOrdersPanel.class);
			if(panel != null){
				panel.showOrderDetail(getItem(container));
			}
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub		
	}

}
