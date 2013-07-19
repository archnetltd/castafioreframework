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

import org.castafiore.ui.Container;
/**
 * 
 * Layout of each cell of a table is delegated to implementations of this interface
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public interface CellRenderer extends Serializable {
	
	/**
	 * Returns a new {@link Container} for the specified parameters
	 * @param row The row of the table
	 * @param column The column of the table
	 * @param page The page of the table if the table is paginable
	 * @param model The model applied on the table
	 * @param table The table itself
	 * @return The container to display in the table for the specified row, column and page
	 */
	public Container getComponentAt(int row, int column,int page, TableModel model, EXTable table);
	
	/**
	 * Changes the content of anything of the container for the specified cell when a page is changed
	 * @param component
	 * @param row
	 * @param column
	 * @param page
	 * @param model
	 * @param table
	 */
	public void onChangePage(Container component,  int row,	int column,int page, TableModel model, EXTable table);

}
