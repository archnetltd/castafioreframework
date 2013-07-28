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
 package org.castafiore.beans.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.ui.ex.form.table.TableModel;

public class BeansTableModel implements TableModel {
	
	Map<String, IBeanInfo> infos;
	
	private List<IBeanInfo> lstInfos = new ArrayList<IBeanInfo>();
	
	private final static String[] COLUMN_NAMES = new String[]{"Unique id", "Description"};
	
	public BeansTableModel(Map<String, IBeanInfo> infos){
		this.infos = infos;
		
		Iterator<String> iters = infos.keySet().iterator();
		while(iters.hasNext()){
			IBeanInfo info = infos.get(iters.next());
			lstInfos.add(info);
		}
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	public int getColumnCount() {
		return COLUMN_NAMES.length;
	}

	public String getColumnNameAt(int index) {
		return COLUMN_NAMES[index];
	}

	public int getRowCount() {
		return lstInfos.size();
	}

	public int getRowsPerPage() {
		return 10;
	}

	public Object getValueAt(int col, int row, int page) {
	
		int index = (page* getRowsPerPage()) + row;
		IBeanInfo info = lstInfos.get(index);
		if(col == 0){
			return info.getSupportedUniqueId();
		}else if(col == 1){
			return info.getInfoAttribute("description");
		}
		return "";
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

}
