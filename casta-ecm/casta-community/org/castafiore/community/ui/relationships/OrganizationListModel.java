package org.castafiore.community.ui.relationships;

import org.castafiore.ui.ex.form.table.TableModel;


/**
 * list of organizations
 * @author acer
 *
 */
public class OrganizationListModel implements TableModel{
	
	private String[] labels = new String[]{"Company name", "e-Mail"};

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		return labels.length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return labels[index];
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
