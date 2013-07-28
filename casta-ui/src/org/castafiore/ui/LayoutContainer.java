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
 package org.castafiore.ui;

import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.ui.ex.layout.DroppableSection;

/**
 * this class represents a container where we can add and retrieve children according a to a layout
 * 
 * 
 * @author kureem
 *
 */
public interface LayoutContainer extends Container {
	
	/**
	 * adds a container to this {@link LayoutContainer}, according to the layout data;
	 * @param child
	 * @param layoutData
	 */
	public void addChild(Container child, String layoutData);
	
	/**
	 * returns all children in the {@link LayoutContainer} that has the specified layoutData
	 * @param layoutData
	 * @return
	 */
	public List<Container> getChildren(String layoutData);
	
	/**
	 * returns a child with the specified name in the section of the layout specified by the layoutData
	 * @param name
	 * @param layoutData
	 * @return
	 */
	public Container getChild(String name,String layoutData);
	
	/**
	 * returns the first descendent of the specified class type in the section of this {@link LayoutContainer} specified by the layoutdata
	 * @param type
	 * @param layoutData
	 * @return
	 */
	public Container getDescendentOfType(Class<? extends Container> type, String layoutData);
	
	/**
	 * returns the first descendent with the specified name in the section of this {@link LayoutContainer} specified by the layout data
	 * @param name
	 * @param layoutData
	 * @return
	 */
	public Container getDescendentByName(String name, String layoutData);
	
	
	/**
	 * returns the first descendent with the specified id in the section of this {@link LayoutContainer} specified by the layout data
	 * @param id
	 * @param layoutData
	 * @return
	 */
	public Container getDescendentById(String id, String layoutData);
	
	/**
	 * validates the layout data
	 * @param layoutData
	 * @throws InvalidLayoutDataException
	 */
	public void validateLayoutData(String layoutData)throws InvalidLayoutDataException;
	
	/**
	 * return all sections of the layout at runtime
	 * @return
	 */
	public List<DroppableSection> getSections();
	
	/**
	 * returns the section of the container with the specified layout data
	 * @param layoutData
	 * @return
	 */
	public Container getContainer(String layoutData);
	
	/**
	 * removes a child with the specified id
	 * @param id
	 */
	public void removeChildFromLayout(String id);

}
