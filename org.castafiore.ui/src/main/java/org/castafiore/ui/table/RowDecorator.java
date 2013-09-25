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

package org.castafiore.ui.table;

import java.io.Serializable;

import org.castafiore.ui.Container;

/**
 * Allows to decorate rows of a table
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public interface RowDecorator extends Serializable {

	/**
	 * decorates the row
	 * 
	 * @param rowCount
	 *            - The row number
	 * @param row
	 *            The row itself
	 * @param table
	 *            The table in which the row belongs
	 * @param model
	 *            The model of the table
	 */
	public void decorateRow(int rowCount, Container row, Table table,
			TableModel model);

}
