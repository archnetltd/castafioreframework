package org.castafiore.security.sessions;

import java.util.Collection;

import org.castafiore.security.api.SessionManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;

public class SessionsManagerModel implements TableModel{

	private String[] labels = new String[]{"username", "Ip address", "Time Login", "Session Id", "Action"};
	
	private Collection<LoggedSession> sessions = SpringUtil.getBeanOfType(SessionManager.class).getLoggedSessions();
	
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
		return sessions.size();
	}

	@Override
	public int getRowsPerPage() {
		return 15;
	}

	@Override
	public Object getValueAt(int col, int row, int page) {
		int realValue = row + (getRowsPerPage()*page);
		return sessions.toArray()[realValue];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
