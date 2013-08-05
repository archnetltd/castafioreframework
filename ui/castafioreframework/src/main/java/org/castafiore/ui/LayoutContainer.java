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
 * This class represents a container where we can add and retrieve children
 * according a to a layout. Children are added by passing a layout data as
 * parameter as well. The implementations of this interface should manage the
 * addition of children properly according to the layout data
 * 
 * This helps to create advanced layout, and delegates layout to implementation,
 * thus relieving API user from the concern of how to create a defined layout
 * 
 * 
 * @author kureem
 * 
 */
public interface LayoutContainer extends Container {

	/**
	 * adds a container to this {@link LayoutContainer}, according to the layout
	 * data. Each implementation should know how to handle the rendering of the
	 * child base on the layout data. It is highly recommended to properly
	 * document the meaning of each layout data, and make different layout data
	 * known to API user via static variables or enumerations or whatever
	 * 
	 * @param child
	 *            The {@link Container} to add to this {@link LayoutContainer}
	 * @param layoutData
	 *            The layout Data to use when adding this component
	 */
	public void addChild(Container child, String layoutData);

	/**
	 * returns all children in the {@link LayoutContainer} that has the
	 * specified layoutData
	 * 
	 * @param layoutData
	 *            The layout data
	 * @return All {@link Container} having the specified layout data
	 */
	public List<Container> getChildren(String layoutData);

	/**
	 * returns a child with the specified name in the section of the layout
	 * specified by the layoutData
	 * 
	 * @param name
	 *            The name of the child
	 * @param layoutData
	 *            The layout data to search
	 * @return a child with the specified name in the section of the layout
	 *         specified by the layoutData
	 */
	public Container getChild(String name, String layoutData);

	/**
	 * returns the first descendant of the specified class type in the section
	 * of this {@link LayoutContainer} specified by the layoutdata
	 * 
	 * @param type
	 * @param layoutData
	 * @return the first descendant of the specified class type in the section
	 *         of this {@link LayoutContainer} specified by the layoutdata
	 */
	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData);

	/**
	 * returns the first descendant with the specified name in the section of
	 * this {@link LayoutContainer} specified by the layout data
	 * 
	 * @param name
	 *            The name of the descendant to search
	 * @param layoutData
	 *            The layout data to search
	 * @return the first descendant with the specified name in the section of
	 *         this {@link LayoutContainer} specified by the layout data
	 * 
	 */
	public Container getDescendentByName(String name, String layoutData);

	/**
	 * returns the first descendant with the specified id in the section of this
	 * {@link LayoutContainer} specified by the layout data
	 * 
	 * @param id
	 *            The id of the descendant to search
	 * @param layoutData
	 *            The layout data to search
	 * @return the first descendant with the specified id in the section of this
	 *         {@link LayoutContainer} specified by the layout data
	 */
	public Container getDescendentById(String id, String layoutData);

	/**
	 * validates the layout data. Checks to see if the layout data is a valid
	 * one. If not, an {@link InvalidLayoutDataException} is thrown
	 * 
	 * @param layoutData
	 *            The layout data to check
	 * @throws InvalidLayoutDataException
	 *             If the layout data is not valid for the implementation of
	 *             {@link LayoutContainer}
	 */
	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException;

	/**
	 * 
	 * 
	 * @return All sections of the layout at runtime i.e. It only returns the
	 *         current droppable sections. Not possible droppable sections
	 */
	public List<DroppableSection> getSections();

	/**
	 * returns the section of the container with the specified layout data
	 * 
	 * @param layoutData
	 *            The layout data to search
	 * @return The section of the {@link LayoutContainer} where
	 *         {@link Container} with specified layout data is added
	 */
	public Container getContainer(String layoutData);

	/**
	 * removes a child with the specified id
	 * 
	 * @param id
	 *            The id of the child to remove
	 */
	public void removeChildFromLayout(String id);

}
