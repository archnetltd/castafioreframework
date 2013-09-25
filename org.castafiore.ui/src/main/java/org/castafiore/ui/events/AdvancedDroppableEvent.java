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

package org.castafiore.ui.events;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.JMap;
 
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class AdvancedDroppableEvent implements Event {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Droppable droppable = null;

	public AdvancedDroppableEvent(Droppable droppable) {
		super();
		this.droppable = droppable;
	}

	public void ClientAction(ClientProxy application) {

		String[] methods = { "activate", "deactivate", "over", "out", "drop" };

		Integer[] eventTypes = new Integer[] { 33, 34, 35, 36, 37 };

		String[] acceptClasses = droppable.getAcceptClasses();

		String sAcceptClasses = "";
		if (acceptClasses != null) {
			for (String s : acceptClasses) {
				if (sAcceptClasses.length() > 0) {
					sAcceptClasses = sAcceptClasses + ",";
				}
				sAcceptClasses = sAcceptClasses + "." + s;
			}
		}

		JMap options = droppable.getDroppableOptions();
		if (options == null) {
			options = new JMap();
		}
		int index = 0;
		for (String m : methods) {

			ClientProxy clone = application.clone();
			Integer eventType = eventTypes[index];
			List<Event> lstEvents = droppable.getEvents().get(eventType);
			if (lstEvents != null && lstEvents.size() > 0) {
				Event evt = lstEvents.get(0);
				evt.ClientAction(clone);

				options.put(m, clone, "ev", "ui");
			}
			index++;
		}

		application.droppable(sAcceptClasses, options);

	}

	public void Success(ClientProxy component,
			Map<String, String> requestParameters) throws UIException {

	}

	public boolean ServerAction(Container component,
			Map<String, String> requestParameters) throws UIException {
		return false;

	}

}
