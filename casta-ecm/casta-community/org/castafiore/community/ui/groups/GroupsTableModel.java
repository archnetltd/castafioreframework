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
 package org.castafiore.community.ui.groups;


import java.util.List;

import org.castafiore.security.Group;
import org.castafiore.security.api.SecurityService;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.BaseSpringUtil;

public class GroupsTableModel implements TableModel {


	private final static String[] LABELS = new String[]{"Name", "Description", "", ""};
	
	
	private List<Group> groups;
	
	
	
	public GroupsTableModel()throws Exception {
		super();
		SecurityService service = BaseSpringUtil.getBeanOfType(SecurityService.class);
		groups = service.getGroups();
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
		return groups.size();
	}

	public int getRowsPerPage() {
		return 10;
	}

	public Object getValueAt(int col, int row, int page) {
		int index = (page*getRowsPerPage()) + row;
		Group role = groups.get(index);
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

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	public Group getGroup(String name){
		for(Group g : groups){
			if(g.getName().equals(name)){
				return g;
			}
		}
		return null;
	}

}
