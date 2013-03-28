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

import java.io.Serializable;

import org.castafiore.ui.events.Event;
/**
 * This interface is the context menu model used to render the context menu
 * 
 * @see ContextMenuAble
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Dec 15, 2008
 */
public interface ContextMenuModel extends Serializable{
	
	/**
	 * returns the number of items to render in the context menu
	 * @return
	 */
	public int size();
	
	/**
	 * returns the url of the icon to render on the specified context menu item
	 * @param index
	 * @return
	 */
	public String getIconSource(int index);
	
	/**
	 * returns the title of the context menu to render
	 * @param index
	 * @return
	 */
	public String getTitle(int index);
	
	/**
	 * returns the event to be executed upon clicking the specified context menu item
	 * @param index
	 * @return
	 */
	public Event getEventAt(int index);
	
	

}
