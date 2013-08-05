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

package org.castafiore.ui;

import org.castafiore.ui.js.JMap;

/**
 * If a component implements this interface, it automatically becomes a
 * droppable.<br>
 * 
 * Furtermore, components implementing this interface can use event of types
 * <table>
 * <th>
 * <tr>
 * <td><b>Event</b></td>
 * <td><b>Description</b></td>
 * <tr>
 * <th>
 * <tbody>
 * <tr>
 * <td>Event.DND_ACTIVATE</td>
 * <td>Executed when droppable activates</td>
 * </tr>
 * <tr>
 * <td>Event.DND_DEACTIVATE</td>
 * <td>Executed when droppable de-activates</td>
 * </tr>
 * <tr>
 * <td>Event.DND_OVER</td>
 * <td>Executed when draggable is over the droppable</td>
 * </tr>
 * <tr>
 * <td>Event.DND_OUT</td>
 * <td>Executed when draggable gets out of droppable</td>
 * </tr>
 * <tr>
 * <td>Event.DND_DROP</td>
 * <td>Executed when draggable is dropped on droppable</td>
 * </tr>
 * </tbody>
 * 
 * </table>
 * <code>
 * asdas
 * </code>
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public interface Droppable extends Container {

	/**
	 * 
	 * @return all the styleclasses that this droppable accepts.
	 */
	public String[] getAcceptClasses();

	/**
	 * The options to be applied on the droppable component.<br>
	 * <a href="http://jqueryui.com/">jquery ui</a> has been used to implement
	 * the droppable feature under the hood<br>
	 * Please refer to this <a
	 * href="http://api.jqueryui.com/droppable/">http://api
	 * .jqueryui.com/droppable/</a> for a list of available options
	 * 
	 * @return The options to be applied on this droppable component
	 */
	public JMap getDroppableOptions();

}
