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
 package org.castafiore.ui.ex.contextmenu.lazy;

import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.interceptors.Interceptor;

public class LazyContextMenuAbleInterceptor implements Interceptor {

	private final static int APPLY_ON = Event.MOUSE_DOWN;
	
	public Interceptor next() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean onRender(Container container) {
		if (container instanceof LazyContextMenuAble) 
		{
			//container.addScript("http://localhost:8080/Castafiore/jquery/jquery.contextmenu.r2.js");
			
			

			List<Event> events = container.getEvents().get(APPLY_ON);
			boolean hasContextmenu = false;
			if (events != null) 
			{
				for (Event e : events) 
				{
					if (e instanceof LazyContextMenuEvent) 
					{
						hasContextmenu = true;
						break;
					}
				}
			}
			if(!hasContextmenu){
				container.setAttribute("isinit", "false");
				container.addEvent(new LazyContextMenuEvent(), APPLY_ON);
			}
		}
		
		return true;
	}

}
