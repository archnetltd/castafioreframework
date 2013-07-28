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
 * If a component implements this interface, it automatically becomes a droppable.
 * 
 * Furtermore, components implementing this interface can use event of types DND_ACTIVATE, DND_DEACTIVATE, DND_OVER, DND_OUT, DND_DROP
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public interface Droppable extends Container {
	
	/**
	 * return all the styleclasses that this droppable accepts.
	 * @return
	 */
	public String[] getAcceptClasses();
	
	public JMap getDroppableOptions();

}
