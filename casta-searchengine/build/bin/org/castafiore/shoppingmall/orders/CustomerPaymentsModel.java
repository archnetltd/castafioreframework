package org.castafiore.shoppingmall.orders;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.accounting.CashBookEntry;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class CustomerPaymentsModel implements TableModel {
	
	
	private List<File> entries = new ArrayList<File>();
	
	private String[] labels = new String[]{"#", "Date", "Amount", "Detail", "Send Message"};

	private String username;
	
	
	public CustomerPaymentsModel(String username) {
		super();
		QueryParameters params = new QueryParameters().setEntity(CashBookEntry.class).addRestriction(Restrictions.eq("accountCode", username)).addOrder(Order.desc("dateOfTransaction"));
		this.username = username;
		
		entries = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
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
		return entries.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (page*getRowsPerPage()) + row;
		CashBookEntry entry = (CashBookEntry)entries.get(realRow);
		if(col == 0){
			return realRow;
		}else if(col == 1){
			return new SimpleDateFormat("dd MMM yyyy").format(entry.getDateOfTransaction());
		}else if(col == 2){
			return StringUtil.toCurrency("MUR", entry.getTotal());
		}else if (col ==3){
			return "Installment number";
		}else{
			return username;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

}
