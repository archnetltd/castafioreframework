package org.castafiore.facebook;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.TableModel;

public class EXFriendsList extends EXContainer implements TableModel{

	public EXFriendsList(String name, String tagName) {
		super(name, tagName);
		// TODO Auto-generated constructor stub
	}

	private String[] cols = new String[]{"Friends", "Select"};
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return cols.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return cols[index];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getRowsPerPage() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
