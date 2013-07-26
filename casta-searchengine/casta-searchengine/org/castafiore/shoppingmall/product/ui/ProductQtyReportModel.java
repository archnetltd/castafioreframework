package org.castafiore.shoppingmall.product.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.OrderEntry;
import org.castafiore.ui.ex.form.table.TableModel;

public class ProductQtyReportModel implements TableModel{
	
	private final static String[] labels = new String[]{"Date", "Inv No.", "Qty"};
	
	private List<Order> orders = new ArrayList<Order>();
	
	private String code;
	
	private Date startDate;
	 private Date endDate;
	 
	 

	public ProductQtyReportModel(String code, Date startDate, Date endDate) {
		super();
		this.code = code;
		this.startDate = startDate;
		this.endDate = endDate;
		orders = MallUtil.getCurrentMerchant().getManager().getProductReport(startDate, endDate, code);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
	}

	@Override
	public int getRowCount() {
		return orders.size();
	}

	@Override
	public int getRowsPerPage() {
		return orders.size();
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		Order order = orders.get(row);
		if(col == 0){
			return new SimpleDateFormat("dd/MMM/yyyy").format(order.getDateOfTransaction());
		}else if(col == 1){
			return order.getCode();
		}else if(col == 2){
			List<OrderEntry> entries = order.getEntries();
			for(OrderEntry entry : entries){
				if(entry.getCode().equals(code)){
					return entry.getQuantity().toPlainString();
				}
			}
			return "";
			//return order.getEntries()
		}else{
			return "";
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
