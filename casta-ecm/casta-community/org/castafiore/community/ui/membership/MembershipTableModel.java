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
 package org.castafiore.community.ui.membership;

import java.util.List;

import org.castafiore.security.Role;
import org.castafiore.security.api.SecurityService;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.BaseSpringUtil;

public class MembershipTableModel implements TableModel {


	private final static String[] LABELS = new String[]{"Name", "Description", "", ""};
	
	
	private List<Role> roles;
	
	
	
	public MembershipTableModel()throws Exception {
		super();
		SecurityService service = BaseSpringUtil.getBeanOfType(SecurityService.class);
		roles = service.getRoles();
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
		return roles.size();
	}

	public int getRowsPerPage() {
		return 10;
	}

	public Object getValueAt(int col, int row, int page) {
		int index = (page*getRowsPerPage()) + row;
		Role role = roles.get(index);
		if(col == 0){
			return role.getName();
		}else if(col == 1){
			return role.getDescription();
		}else {
			return role.getName();
		}
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
	
	public Role getRole(String name){
		for(Role r : roles){
			if(r.getName().equals(name)){
				return r;
			}
		}
		return null;
	}

}
