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
import org.castafiore.ui.Draggable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.js.JMap;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class AdvancedDraggableEvent implements Event {
	
	private Draggable draggable;

	public AdvancedDraggableEvent(Draggable draggable) {
		super();
		this.draggable = draggable;
	}

	
	public void ClientAction(ClientProxy application) {
		JMap options = draggable.getDraggableOptions();
		if(options == null)
		{
			options = new JMap();
		}
		
		options.put("top", application.getStyle("top"));
		options.put("left", application.getStyle("left"));
	
		
		
		List<Event> lstonStartDragEvents =draggable.getEvents().get(Event.START_DRAG);
		
		if(lstonStartDragEvents != null && lstonStartDragEvents.size() > 0)
		{
			Event onStartDragEvent = lstonStartDragEvents.get(0);
			if(onStartDragEvent != null)
			{
				ClientProxy onStart = application.clone();
				onStartDragEvent.ClientAction(onStart);
				options.put("start", onStart);
			}
			
		}
		
		
		
		List<Event> lstonDragEvents =draggable.getEvents().get(Event.DRAG);
		
		if(lstonDragEvents != null && lstonDragEvents.size() > 0)
		{
			Event onDragEvent = lstonDragEvents.get(0);
			if(onDragEvent != null)
			{
				ClientProxy onDrag = application.clone();
				onDragEvent.ClientAction(onDrag);
				options.put("drag", onDrag);
			}
		}
		
		
		List<Event> lstonEndDragEvents =draggable.getEvents().get(Event.END_DRAG);
		
		if(lstonEndDragEvents != null && lstonEndDragEvents.size() > 0)
		{
			Event onEndDragEvent = lstonEndDragEvents.get(0);
			if(onEndDragEvent != null)
			{
				ClientProxy onEnd = application.clone();
				onEndDragEvent.ClientAction(onEnd);
				options.put("stop", onEnd);
			}
		}
		options.put("zIndex","20" );
		application.draggable(options);
		
	}

	
	public void Success(ClientProxy component,
			Map<String, String> requestParameters) throws UIException {
		
	}

	
	public boolean ServerAction(Container component,
			Map<String, String> requestParameters) throws UIException {		
		return false;
	}

}
