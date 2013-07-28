package org.castafiore.shoppingmall.crm;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.ex.form.table.TableModel;

public class SubscribersTableModel implements TableModel {
	
	private String[] Labels = new String[]{"List of subscribers"};
	
	private List<OrderInfo> subscribers = new ArrayList<OrderInfo>();
	
	
	

	public SubscribersTableModel(String cat) {
		super();
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return 3;
		//return Labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return "";
		//return Labels[index];
	}

	@Override
	public int getRowCount() {
		double remainder = Math.IEEEremainder(subscribers.size(), 3);
		
		
		int rows = new Double( (subscribers.size()-remainder)/3).intValue() + 1 ;
		
		return rows;
	}

	@Override
	public int getRowsPerPage() {
		return 4;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		
		int realRow = (page* getRowsPerPage()) + row;
		
		int index = (realRow * 3) + col;
		if(subscribers.size() > index){
			return  subscribers.get(index);
		}return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	public void filter(List<OrderInfo> subs){
		
		//subscribers = MallUtil.getCurrentMerchant().searchSubscriptions(text);
		this.subscribers = subs;
		
	}

}
