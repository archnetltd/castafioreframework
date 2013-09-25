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
package org.castafiore.ui.table;

import java.io.Serializable;

import org.castafiore.ui.Container;

/**
 * Interface exposing methods to create and interact with a table
 * 
 * @author Kureem Rossaye
 * 
 */
public interface Table extends Container, Serializable {

	/**
	 * Sets the {@link TableModel} of the table<br>
	 * The {@link TableModel} is responsible for holding the content of the
	 * table
	 * 
	 * @param model
	 */
	public void setModel(TableModel model);

	/**
	 * 
	 * @return The table model of the table
	 */
	public TableModel getModel();

	/**
	 * Returns the number of pages expected to be created in the table.<br>
	 * If the table is not expected to be pagineable, return 1
	 * 
	 * @return
	 * 
	 */
	public int getPages();

	/**
	 * The current page with starting index = 0
	 * 
	 * @return The current page
	 */
	public int getCurrentPage();

	/**
	 * Change page to the specified value
	 * 
	 * @param page
	 *            The page to change
	 */
	public void changePage(int page);

}
