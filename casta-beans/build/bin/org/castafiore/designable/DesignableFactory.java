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
 package org.castafiore.designable;

import java.util.Map;

import org.castafiore.beans.CategorizeAble;
import org.castafiore.beans.IdentifyAble;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

/**
 * 
 * This interface represents all the aspects of a Designable component
 * A designable component is a component that can be used in the designer.
 * 
 * @see AbstractDesignableFactory
 * @author Kureem Rossaye
 *
 */
public interface DesignableFactory extends Container, Draggable, IdentifyAble, CategorizeAble, ToolBarItem {
	
	/**
	 * should return a new instance each time it is called.
	 * 
	 * @return
	 */
	public Container getInstance();
	
	
	/**
	 * this method should
	 * @param c
	 * @param attributeName
	 * @param attributeValue
	 */
	public void applyAttribute(Container c, String attributeName, String attributeValue);
	
	
	/**
	 * returns minimum required attributes
	 * @return
	 */
	public String[] getRequiredAttributes();
	
	
	public Map<String, ConfigForm> getAdvancedConfigs();
	
	
	/**
	 * Refreshes the container
	 * @param c
	 */
	public void refresh(Container c);
	
	/**
	 * returns the icon representation
	 * @return
	 */
	public String getIcon();
	
}
