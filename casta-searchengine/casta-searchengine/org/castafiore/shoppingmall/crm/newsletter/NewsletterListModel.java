package org.castafiore.shoppingmall.crm.newsletter;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.ex.form.table.TableModel;

public class NewsletterListModel implements TableModel {
	
	private List<Newsletter> newsletters = new ArrayList<Newsletter>();

	private String[] Labels = new String[]{"Title",  "Type","Status", "Actions"};
	
	public NewsletterListModel(){
		newsletters = MallUtil.getCurrentMerchant().getNewsletters().toList();
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return Labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return Labels[index];
	}

	@Override
	public int getRowCount() {
		return newsletters.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return newsletters.get((getRowsPerPage()*page) + row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
