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

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.ex.contextmenu.EXContextMenu;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;

public class LazyContextMenuEvent implements Event {

	public void ClientAction(ClientProxy container) {
		
		
		container.addMethod("bind","contextMenu",container.IF(container.getAttribute("isinit").equal("true"), container.clone(), container.clone().mask().makeServerRequest(this)));
		
		//container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		LazyContextMenuAble cma = (LazyContextMenuAble)container;
		ContextMenuModel model = cma.getContextMenuModel();
		EXContextMenu contextMenu = new EXContextMenu(container.getName()+ "_contextMenu", model);
		contextMenu.addScript(ResourceUtil.getJavascriptURL("jquery/contextmenu.js"));
		cma.addChild(contextMenu);
		container.setAttribute("isinit", "true");
		//container.addEvent(new ContextMenuEvent(), Event.READY);
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		ClientProxy contextMenu = container.getDescendentOfType(EXContextMenu.class);
		if(contextMenu == null)
		{
			throw new RuntimeException("unable to find context menu for component");
		}
		container.addMethod("contextMenu", contextMenu.getId(), new JMap());
		
	}

}
