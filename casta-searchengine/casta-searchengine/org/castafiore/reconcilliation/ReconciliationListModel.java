package org.castafiore.reconcilliation;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.types.Directory;

public class ReconciliationListModel implements TableModel{

	private static String[] labels = new String[]{"Date", "Download", "Actions"};
	List<Directory> dirs = new ArrayList<Directory>();
	
	
	
	public ReconciliationListModel() {
		super();
		String user = MallUtil.getCurrentMerchant().getUsername();
		
		try{
		Directory rec = SpringUtil.getRepositoryService().getDirectory("/root/users/"+ user + "/reconciliation", user);
		dirs =rec.getFiles(Directory.class).toList();
		}catch(Exception e){
			
		}
		
		//root/users/user/reconc
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
		return dirs.size();
	}

	@Override
	public int getRowsPerPage() {
		return 20;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (page*getRowsPerPage() ) + row;
		return dirs.get(realRow);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

}
