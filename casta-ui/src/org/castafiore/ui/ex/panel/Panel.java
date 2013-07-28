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
 package org.castafiore.ui.ex.panel;

import org.castafiore.JQContants;
import org.castafiore.ui.Container;


/**
 * 
 * A common interface for different implementation of a panel.
 * A good idea to orient implementation towards interface design instead of concrete implementation.
 * 
 * User can have his own implementation of a panel and plug into existing components.
 * @author Kureem Rossaye
 *
 */
public interface Panel extends Container, JQContants {
	
	
	
	
	/**
	 * Sets the title of the panel
	 * @param title
	 */
	public Panel setTitle(String title);
	
	
	
	/**
	 * sets the body of the panel
	 * @param container
	 */
	public Panel setBody(Container container);
	
	/**
	 * returns the body of the panel
	 * @return
	 */
	public Container getBody();
	
	/**
	 * specifies if the header is to be show or not
	 * @param showHeader
	 */
	public Panel setShowHeader(boolean showHeader);
	
	public Panel setShowFooter(boolean display);
	
	/**
	 * specifies if the close button to be shown
	 * @param b
	 */
	public Panel setShowCloseButton(boolean b);
	
	
	
	
	

}
