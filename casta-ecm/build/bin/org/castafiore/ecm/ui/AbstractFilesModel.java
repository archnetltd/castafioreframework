package org.castafiore.ecm.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public abstract class AbstractFilesModel implements TableModel{
	
protected int currentPage = -1;
	
	protected int count = -1;
	
	protected List buffer = new ArrayList(10);
	
	
	
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
	public abstract String[] getColumns();

	@Override
	public int getColumnCount() {
		return getColumns().length;
	}

	@Override
	public String getColumnNameAt(int index) {
		return getColumns()[index];
	}

	@Override
	public int getRowCount() {
		if(count == -1){
			QueryParameters params = getParams();
			params.getOrders().clear();
			count = SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser()); 
		}
		return count;
	}
	
	public abstract QueryParameters getParams();

	@Override
	public int getRowsPerPage() {
		return 10;
	}
	
	public void fill(int page){
		if(page != currentPage){
			QueryParameters params = getParams();
			params.setFirstResult(page*getRowsPerPage()).setMaxResults(getRowsPerPage());
			List files = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
			buffer.clear();
			buffer.addAll(files);
		}
	}
	@Override
	public Object getValueAt(int col, int row, int page) {
		fill(page);
		return buffer.get(row);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}


}
