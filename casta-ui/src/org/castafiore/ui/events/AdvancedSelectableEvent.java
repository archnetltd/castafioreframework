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
 package org.castafiore.ui.events;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.selectable.Selectable;
import org.castafiore.ui.js.JMap;

public class AdvancedSelectableEvent implements Event {
	
	private Selectable selectable;
	
	

	public AdvancedSelectableEvent(Selectable selectable) {
		super();
		this.selectable = selectable;
	}

	public void ClientAction(ClientProxy container) {
		JMap options = selectable.getSelectableOptions();
		if(options == null){
			options = new JMap();
		}
		Map<Integer,String> mappings = new HashMap<Integer,String>();
		mappings.put(Event.SELECTABLE_SELECTED, "selected");
		mappings.put(Event.SELECTABLE_SELECTING, "selecting");
		mappings.put(Event.SELECTABLE_START, "start");
		mappings.put(Event.SELECTABLE_STOP, "stop");
		mappings.put(Event.SELECTABLE_UNSELECTED, "unselected");
		mappings.put(Event.SELECTABLE_UNSELECTING, "unselecting");
		
		
		Iterator<Integer> iter = mappings.keySet().iterator();
		while(iter.hasNext()){
			int key = iter.next();
			List<Event> evts =selectable.getEvents().get(key);
			
			if(evts != null && evts.size() > 0)
			{
				ClientProxy clientEvent= container.clone();
				for(Event evt : evts){
					if(evt != null)
					{
						ClientProxy This = container.clone();
						evt.ClientAction(This);
						clientEvent.mergeCommand(This);
					}
				}
				
				options.put(mappings.get(key), clientEvent, "event", "ui");
				
			}
		}
		
		container.addMethod("selectable", options);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
