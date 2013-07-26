package org.castafiore.shoppingmall.checkout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.inventory.orders.OrderService;
import org.castafiore.shoppingmall.crm.OrderInfo;
import org.castafiore.ui.ex.form.table.TableModel;

public class OrdersListTableModel implements TableModel{
	
	private List<OrderInfo> orders = new ArrayList<OrderInfo>();
	
	static String[] fields = new String[]{"Inv. No"	,"Date","Customer","Status","Total"};
	
	private int currentPage =0;
	
	private int rows = -1;
	
	private Date from;
	
	private Date to;
	
	private boolean bydatecreated;
	
	private List statuss;
	
	private OrderInfo example;
	

	public OrdersListTableModel(OrderInfo example, Date from, Date to,List statuss, boolean bydatecreated) {
		super();
		this.example = example;
		this.from = from;
		this.to = to;
		this.statuss = statuss;
		this.bydatecreated = bydatecreated;
		orders = OrderService.searchByExample(example, from, to, bydatecreated,statuss, currentPage, getRowsPerPage());
		if(example == null){
			example = new OrderInfo();
		}
		rows = example.getCount();
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
		return rows;
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		if(page != currentPage){
			currentPage = page;
			orders = new OrderService().searchByExample(example, from, to, bydatecreated, statuss, page, getRowsPerPage());
		}
		
		return orders.get(row);
		
		
		//return orders.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
