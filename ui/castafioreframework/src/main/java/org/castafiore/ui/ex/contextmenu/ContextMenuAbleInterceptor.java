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
 package org.castafiore.ui.ex.contextmenu;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.interceptors.Interceptor;
import org.castafiore.utils.ResourceUtil;

public class ContextMenuAbleInterceptor implements Interceptor {

	public Interceptor next() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean onRender(Container container) 
	{
		if (container instanceof ContextMenuAble) 
		{
			//container.addScript("http://localhost:8080/Castafiore/jquery/jquery.contextmenu.r2.js");
			
			ContextMenuAble cma = (ContextMenuAble) container;

			List<Event> events = container.getEvents().get(Event.READY);
			boolean hasContextmenu = false;
			if (events != null) 
			{
				for (Event e : events) 
				{
					if (e instanceof ContextMenuEvent) 
					{
						hasContextmenu = true;
						break;
					}
				}
			}

			if (!hasContextmenu) 
			{
				ContextMenuModel model = cma.getContextMenuModel();
				EXContextMenu contextMenu = new EXContextMenu(container.getName()+ "_contextMenu", model);
				contextMenu.addScript(ResourceUtil.getJavascriptURL("jquery/contextmenu.js"));
				cma.addChild(contextMenu);
				container.addEvent(new ContextMenuEvent(), Event.READY);
				
			}
			else
			{
				// if ever all children were removed, but not events
				if(cma.getDescendentOfType(EXContextMenu.class) == null)
				{
					ContextMenuModel model = cma.getContextMenuModel();
					EXContextMenu contextMenu = new EXContextMenu(container.getName()+ "_contextMenu", model);
					cma.addChild(contextMenu);
				}
			}

		}
		return true;
	}

	public boolean onServerAction(Container component,
			Map<String, String> parameters) {
		// TODO Auto-generated method stub
		return false;
	}

}
