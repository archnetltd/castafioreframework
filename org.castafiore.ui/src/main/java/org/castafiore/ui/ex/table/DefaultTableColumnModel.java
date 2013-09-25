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
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.table.Table;
import org.castafiore.ui.table.TableColumnModel;
import org.castafiore.ui.table.TableModel;

/**
 * Default {@link TableColumnModel} implementation<br>
 * Simly creates a nice header jquery-ui styled
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class DefaultTableColumnModel implements TableColumnModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Container getColumnAt(int index, Table table, TableModel model) {

		EXContainer column = new EXContainer("" + index, "th");
		column.addClass("ui-widget-header");
		column.setText(model.getColumnNameAt(index));
		return column;
	}

}
