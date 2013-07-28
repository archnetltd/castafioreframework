/*
 * Copyright (C) 2007-2010 Castafiore
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
 package org.castafiore.community.ui.users;

import java.util.List;

import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.Util;

public class UsersTableModel implements TableModel {
	
	private final static String[] LABELS = new String[]{"Username", "First name", "Last name", "Email", "", ""};
	
	private List<User> users  = null;
	
	

	public UsersTableModel() throws Exception{
		SecurityService service = BaseSpringUtil.getBeanOfType(SecurityService.class);
		users = service.getUsersForOrganization(Util.getLoggedOrganization());
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return LABELS.length;
	}

	public String getColumnNameAt(int index) {
		return LABELS[index];
	}

	public int getRowCount() {
		return users.size();
		
	}

	public int getRowsPerPage() {
		return 10;
	}

	public Object getValueAt(int col, int row, int page) {
		int index = (page*getRowsPerPage()) + row;
		User u = users.get(index);
		if(col == 0){
			return u.getUsername();
		}else if(col == 1){
			return u.getFirstName();
		}else if(col == 2){
			return u.getLastName();
		}else if( col == 3){
			return u.getEmail();
		}else{
			return u.getUsername();
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
