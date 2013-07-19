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

import org.castafiore.ui.Container;
/**
 * Default implementation of {@link RowDecorator}<br>
 * Simply alternates the color of rows
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class DefaultRowDecorator implements RowDecorator {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * adds the class "ui-state-highlight" when even row
	 */
	public void decorateRow(int rowCount, Container row, Table table,
			TableModel model) {
		
		if ((rowCount % 2) == 0)
		{
			row.setAttribute("class", "ui-state-highlight");
		}
		else
		{
			row.setAttribute("class", "n");
		}
		
	}

	

}
