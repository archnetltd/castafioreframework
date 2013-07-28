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
 package org.castafiore.designer.designable.table;

import org.castafiore.ui.ex.form.table.TableModel;

public class DefaultDesignableTableModel implements TableModel{
	
	private int columnCount = 5;
	
	private int rowsPerPage = 10;
	
	private String[] columnNames = new String[]{"Field 1", "Field 2", "Field 3", "Field 4", "Field 5"};
	
	private Class[] columnClasses = new Class[]{String.class, String.class, String.class, String.class, String.class};
	
	private boolean[] editables = new boolean[]{false,false,false,false,false};

	public Class<?> getColumnClass(int columnIndex) {
		return columnClasses[columnIndex];
	}

	public int getColumnCount() {
		return columnCount;
	}

	public String getColumnNameAt(int index) {
		return columnNames[index];
	}

	public int getRowCount() {
		return 9;
	}

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public Object getValueAt(int col, int row, int page) {
		return " ";
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return editables[columnIndex];
	}

	

}
