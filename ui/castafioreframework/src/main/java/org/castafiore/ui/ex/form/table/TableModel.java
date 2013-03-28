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

package org.castafiore.ui.ex.form.table;

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
	
	public int getColumnCount();
	
	
	public int getRowsPerPage();
	
	
	public String getColumnNameAt(int index);
	
	public Object getValueAt(int col, int row, int page);
	
	
	
	public Class<?> getColumnClass(int columnIndex);
	
	public boolean isCellEditable(int rowIndex, int columnIndex);
	
	

	
	

}
