package org.castafiore.appstore.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.appstore.AppPackage;
import org.castafiore.appstore.AppStoreUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;

public class MyApplicationsModel implements TableModel{
	
	
	private List<AppPackage> apps = new ArrayList<AppPackage>();

	private String[] labels = new String[]{"Title", "Version", "Price", "Downloaded"};
	
	public MyApplicationsModel(){
		apps = AppStoreUtil.getMyApplications(Util.getRemoteUser());
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
		return apps.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		return apps.get((page*getRowsPerPage()) + row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
