package org.castafiore.shoppingmall.checkout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.searchengine.EventDispatcher;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.shoppingmall.ui.EXSearch;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.RowDecorator;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public class EXOrdersList extends EXContainer implements EventDispatcher, RowDecorator {

	public EXOrdersList(String name) {
		super(name, "div");
		addChild(new EXSearch(""));
		EXTable table = new EXTable("table",new OrdersListTableModel(null,null,null,null,false), new OrdersListCellRenderer());
		table.setRowDecorator(this);
		EXPagineableTable stable = new EXPagineableTable("", table);
		addChild(stable);
	}
	
	public void filtreByState(int state){
		QueryParameters params = null;
		
		if(state != -1){
			
			OrderInfo ex = new OrderInfo();
			ex.setStatus(state);
			
			
			setOrders(ex, null, null, true, null);
		}else{
			setOrders(null, null, null, true, null);
		}
		
		
	}
	
	public void setOrders(OrderInfo ex, Date from, Date to, boolean bydatecreated, List status){
		getDescendentOfType(EXTable.class).setModel(new OrdersListTableModel(ex, from, to, status, bydatecreated));
		getDescendentOfType(EXPagineableTable.class).refresh();
	}

	@Override
	public void executeAction(Container source) {

		
	}

	@Override
	public void decorateRow(int rowCount, EXContainer row, EXTable table,
			TableModel model) {
		
		int status = Integer.parseInt(row.getDescendentByName("td_status").getAttribute("status"));
		row.setStyle("background-color", SpringUtil.getBeanOfType(OrdersWorkflow.class).getColor(status));
		row.setStyle("border-bottom", "outset 2px white");
		for(Container c : row.getChildren()){
			c.setStyle("border", "none");
		}
	}

}
