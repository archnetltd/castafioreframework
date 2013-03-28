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

package org.castafiore.ui.ex.layout;

import java.io.Serializable;

import org.castafiore.ui.Container;
/**
 * This class when applied to a component, automatically executes the doStyling() on all the children of the application
 * All children further added will automatically pass through the doStyling() method during rendering
 * 
 * This class is primarily intended to use for layout
 * 2 Simple implementations are provided. The columnLayout and RowLayout. 
 * More advanced layout can be created so suit need
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 * @deprecated
 */
public interface Layout extends Serializable{
	
	public final static Layout COLUMN_LAYOUT = new ColumnLayout();
	
	public final static Layout ROW_LAYOUT = new RowLayout();
	
	
	public void doStyling(Container child, Container container);

}
