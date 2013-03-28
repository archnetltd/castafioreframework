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

package org.castafiore.ui.interceptors;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.events.AdvancedDraggableEvent;
import org.castafiore.ui.events.Event;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class DraggableInterceptor implements Interceptor {

	
	public Interceptor next() {
		
		return null;
	}

	
	public boolean onRender(Container container) {
		handleDraggable((Draggable)container);
		return false;
	}

	
	public boolean onServerAction(Container component,
			Map<String, String> parameters) {
		
		return false;
	}

	
	public  void handleDraggable(Draggable container)
	{
		//container.addScript(ResourceUtil.getJavascriptURL("jquery/ui.draggable.js"));
		List<Event> events = container.getEvents().get(Event.READY);
		boolean hasDraggable = false;
		if(events != null)
		{
			for(Event e : events)
			{
				if(e instanceof AdvancedDraggableEvent)
				{
					hasDraggable = true;
					break;
				}
			}
		}
		
		if(!hasDraggable)
		{
			container.addEvent(new AdvancedDraggableEvent((Draggable)container), Event.READY);
		}
	}
}
