package org.castafiore.google.docs.ui;

import java.util.List;

import org.castafiore.ui.ex.form.table.TableModel;

import com.google.gdata.data.docs.DocumentListEntry;

public class SimpleGoogleDocTableModel implements TableModel{
	
	private List<DocumentListEntry> entries;

	public SimpleGoogleDocTableModel(List<DocumentListEntry> entries) {
		super();
		this.entries = entries;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public Object getValueAt(int col, int row, int page) {
		DocumentListEntry entry = entries.get((page*10) +row);
		return entry;
	}
	
	@Override
	public int getRowsPerPage() {
		return 10;
	}
	
	@Override
	public int getRowCount() {
		return entries.size();
	}
	
	@Override
	public String getColumnNameAt(int index) {
		return "Google Docs";
	}
	
	@Override
	public int getColumnCount() {
		return 1;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO Auto-generated method stub
		return String.class;
	}
}
