package org.castafiore.inventory.orders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.form.table.TableModel;

public class BOrderEntryTable extends EXTable implements TableModel{

	
	private String labels[] = new String[]{"Code", "Title", "Price", "Qty", "Total"};
	
	private List<Map<String, String>> data = new ArrayList<Map<String,String>>();
	
	
	public BOrderEntryTable(String name) {
		super(name, null);
		setModel(this);
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
		return data.size();
	}

	@Override
	public int getRowsPerPage() {
		return data.size();
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return data.get(row).get(labels[col]);
	}
	
	public void addRow(String code, String title, String price, String qty, String total){
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", code);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
