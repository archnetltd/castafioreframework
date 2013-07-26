package org.castafiore.inventory;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;

public abstract class AbstractModel implements TableModel{
	
	protected int count = -1;
	List<File> iter = null;

	
	
	
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
			count = SpringUtil.getRepositoryService().countRows(params, Util.getRemoteUser());
		}
		return count;
	}
	
	public abstract QueryParameters getParams();

	@Override
	public int getRowsPerPage() {
		return 20;
	}
	
	
	@Override
	public Object getValueAt(int col, int row, int page) {
		
		if(iter == null){
			iter = SpringUtil.getRepositoryService().executeQuery(getParams(), Util.getRemoteUser());
		}
		int rrow = row + (page*getRowsPerPage());
		return iter.get(rrow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	public void refresh(){
		count=-1;
		iter= null;
	}

}
