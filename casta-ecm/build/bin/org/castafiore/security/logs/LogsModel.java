package org.castafiore.security.logs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.ex.form.table.TableModel;

public class LogsModel implements TableModel {

	private String[] labels = new String[]{"ID", "Date", "Type", "User", "IP Address", "Severity"};
	
	private List<Log> logs = new ArrayList<Log>();
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
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
		return logs.size();
	}

	@Override
	public int getRowsPerPage() {
		return 10;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realRow = (page*getRowsPerPage()) + row;
		Log l = logs.get(realRow);
		if(col == 0){
			return l.getId();
		}else if(col == 1){
			return new SimpleDateFormat("dd-MM-yyyy hh mm ss").format(l.getTime().getTime());
		}else if(col == 2){
			return l.getType();
		}else if(col == 3){
			return l.getUser();
		}else if(col == 4){
			return l.getIpaddress();
		}else {
			return l.getSeverity();
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
