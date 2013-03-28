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

package org.castafiore.ui.tabbedpane;

import java.io.Serializable;

import org.castafiore.ui.Container;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public interface TabRenderer extends Serializable{
	
	public Container getComponentAt(TabPanel pane, TabModel model, int index);
	
	
	public void onSelect(TabPanel pane, TabModel model, int index, Container tab);
	
	
	public void onDeselect(TabPanel pane, TabModel model, int index, Container tab);
	
	
	

}
