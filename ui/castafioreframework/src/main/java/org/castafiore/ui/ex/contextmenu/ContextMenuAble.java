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
 package org.castafiore.ui.ex.contextmenu;

import org.castafiore.ui.Container;
/**
 * This interface is used to create a context menu to a component by right clicking on it
 * 
 * Simple make your component implements this interface and implement the required method
 * 
 * {@link ContextMenuAble}
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Dec 15, 2008
 */
public interface ContextMenuAble extends Container {
	
	/**
	 * return the context menu model
	 * @return
	 */
	public ContextMenuModel getContextMenuModel();

}
