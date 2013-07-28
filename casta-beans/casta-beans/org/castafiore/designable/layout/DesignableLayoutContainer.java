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
 package org.castafiore.designable.layout;


import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.LayoutContainer;


/**
 * Upon creation of a layout that can be plugged in the designer, this interface should be implemented
 * 
 * This interface extends {@link LayoutContainer} and {@link Droppable} interface.
 * 
 * When a class implements this layout, the user should at least add the event {@link OnDropEvent} to be able to effectively drop a component into this layout.
 * 
 * Please also implement the class in the following way
 * 
 *  public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}
	
	see {@link EXDroppableVerticalLayoutContainer} as an example
 * @author Kureem Rossaye
 *
 */
public interface DesignableLayoutContainer extends LayoutContainer, Droppable{
	
	
	/**
	 * this method should return a possible layout data when trying to add a component arbitrarily in this {@link LayoutContainer}
	 * this layout data will be used to add the specified container into this DesignableLayoutContainer
	 * @return
	 */
	public String getPossibleLayoutData(Container container);
	
	/**
	 * This method is executed after the component has been dropped on this layout container.<br>
	 * It is used to decorate the component to add features to the component in the designer<br>
	 * e.g. Resizeable feature<br>
	 * Draggable feature
	 * @param component
	 */
	public void onAddComponent(Container component);
	
	/**
	 * This method moves up the component in the LayoutContainer
	 * @param component
	 */
	public void moveUp(Container component);
	
	
	/**
	 * This method moves down the component in this layout container
	 * @param component
	 */
	public void moveDown(Container component);

}
