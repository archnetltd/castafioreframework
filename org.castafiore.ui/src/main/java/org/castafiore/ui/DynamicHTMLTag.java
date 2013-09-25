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

import java.util.List;
import java.util.Map;

import org.castafiore.ui.events.Event;

/**
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com June 27 2008
 */
public interface DynamicHTMLTag extends HTMLTag {

	/**
	 * adds an event if the specified type
	 * 
	 * @param event
	 * @param type
	 */
	public Container addEvent(Event event, int type);

	/**
	 * returns all events
	 * 
	 * @return
	 */
	public Map<Integer, List<Event>> getEvents();

}
