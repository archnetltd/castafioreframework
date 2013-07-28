package org.castafiore.shoppingmall.pos;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.orders.OrdersWorkflow;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.CellRenderer;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.RowDecorator;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.workflow.FlexibleWorkflow;
import org.castafiore.workflow.Workflow;

public class EXPurchasesList extends EXContainer implements TableModel,CellRenderer,Event,RowDecorator{

private List<Order> orders = new ArrayList<Order>();
	
	static String[] fields = new String[]{"Order. No"	,"Date","Supplier","Status","Total"};
	
	private OrdersWorkflow workflow;// new FlexibleWorkflow("/root/users/erevolution/workflow");
	
	public EXPurchasesList(String name, OrdersWorkflow workflow) {
		super(name, "div");
		
		this.workflow = workflow;
		
		addChild(new EXSearchPurchases("searchPurchases", workflow));
		EXTable t = new EXTable("tb", this, this);
		t.setRowDecorator(this);
		EXPagineableTable ptable = new EXPagineableTable("pp", t);
		addChild(ptable);
		
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return fields.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return fields[index];
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int rrow = row + (page*getRowsPerPage());
		return orders.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}
	
	
	public void setOrders(List<Order> orders){
		this.orders = orders;
		getDescendentOfType(EXPagineableTable.class).refresh();
	}
	
	@Override
	public Container getComponentAt(int row, int column, int page,
			TableModel model, EXTable table) {
		Order order = (Order)model.getValueAt(column, row, page);
		Merchant bi = MallUtil.getMerchant(order.getOrderedFrom());
		Container c = new EXContainer("", "div");
		if(column == 0){
			c= new EXContainer("td_inv", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("inv","a").setAttribute("path", order.getAbsolutePath()).addEvent(this, CLICK).setAttribute("href", "#").setText(order.getCode()).setStyle("font-weight", "bold").setStyle("color", "#111"));
		}else if(column == 1){
			c= new EXContainer("td_date", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("date","div").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction())));
		}else if(column == 2){
			c= new EXContainer("td_customer", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("customer", "label").setStyle("font-weight", "bold").setStyle("color", "#111").setText(bi==null?"":bi.getCompanyName()));
		}else if(column == 3){
			c= new EXContainer("td_status", "div").setAttribute("status", order.getStatus() + "").setStyle("vertical-align","top" ).addChild(new EXContainer("status", "div").setStyle("font-weight", "bold").setStyle("color", "#111").setText(workflow.getStatus(order.getStatus())));
		}else if(column == 4){
			c= new EXContainer("td_total", "div").setStyle("vertical-align","top" ).addChild(new EXContainer("total","div").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTotal())));
		}
		
		
		
		return c;
		
	}

	@Override
	public void onChangePage(Container component, int row, int column,
			int page, TableModel model, EXTable table) {
		Order order = (Order)model.getValueAt(column, row, page);
		Merchant bi = MallUtil.getMerchant(order.getOrderedFrom());
		Container c = component;
		if(column == 0){
			component.getDescendentByName("inv").setText(order.getCode()).setAttribute("path", order.getAbsolutePath());
			
		}else if(column == 1){
			component.getDescendentByName("date").setText(new SimpleDateFormat("dd/MM/yyyy").format(order.getDateOfTransaction()));
		}else if(column == 2){
			component.getDescendentByName("customer").setText(bi.getCompanyName());
		}else if(column == 3){
			component.setAttribute("status", order.getStatus() + "");
			component.getDescendentByName("status").setText(workflow.getStatus(order.getStatus()));
		}else if(column == 4){
			component.getDescendentByName("total").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),order.getTotal()));
		}
		
		
		c.setStyle("background-color", workflow.getColor(order.getStatus()));
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	public Order getItem(Container c) {
		Order order = (Order)SpringUtil.getRepositoryService().getFile(c.getAttribute("path"), Util.getRemoteUser());
		
		return order;
	}
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("customer")){
			//go to customer card
		}else if(container.getName().equalsIgnoreCase("inv")){
			EXPurchaseOrdersPanel panel =container. getAncestorOfType(EXPurchaseOrdersPanel.class);
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

	@Override
	public void decorateRow(int rowCount, EXContainer row, EXTable table,
			TableModel model) {
		
		int status = Integer.parseInt(row.getDescendentByName("td_status").getAttribute("status"));
		row.setStyle("background-color", workflow.getColor(status));
		row.setStyle("border-bottom", "outset 2px white");
		for(Container c : row.getChildren()){
			c.setStyle("border", "none");
		}
	}

}
