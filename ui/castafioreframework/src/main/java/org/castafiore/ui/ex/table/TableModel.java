/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.ex.table;

import java.io.Serializable;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public interface TableModel extends Serializable {
	
	/**
     * Returns the number of rows in the model. A
     * <code>EXTable</code> uses this method to determine how many rows it
     * should display.  This method should be quick, as it
     * is called frequently during rendering.
     *
     * @return the number of rows in the model
     * @see #getColumnCount
     */
	public int getRowCount();
	
	/**
	 * Returns the number of columns in the table
	 * @return
	 */
	public int getColumnCount();
	
	
	/**
	 * 
	 * @return The number of pages in the table
	 */
	public int getRowsPerPage();
	
	
	/**
	 * The name of the column for the specified index
	 * @param index The index of the column
	 * @return
	 */
	public String getColumnNameAt(int index);
	
	/**
	 * Returns the  value for the specified co-ordinate of the table
	 * @param col the column of the table
	 * @param row The row of the table
	 * @param page The page of the table
	 * @return The value to place in the specified cell. This value will be passed via the {@link CellRenderer} 
	 */
	public Object getValueAt(int col, int row, int page);
	
	
	/**
	 * The default class for values in the specified column.<br> Can be used as an indicated to choose the correct container to provide in the {@link CellRenderer}
	 * @param columnIndex 
	 * @return The type of the values in the specified column
	 */
	public Class<?> getColumnClass(int columnIndex);
	
	/**
	 * States whether the specified row and column is editable<br>Can be used as an indicated to choose the correct container to provide in the {@link CellRenderer}
	 * @param rowIndex The row
	 * @param columnIndex The column
	 * @return is editable or not.
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex);
	
	

	
	

}
