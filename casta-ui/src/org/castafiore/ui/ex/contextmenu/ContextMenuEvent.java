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

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;

public class ContextMenuEvent implements Event{

	public void ClientAction(ClientProxy application) {
		ClientProxy contextMenu = application.getDescendentOfType(EXContextMenu.class);
		if(contextMenu == null)
		{
			throw new RuntimeException("unable to find context menu for component");
		}
		application.addMethod("contextMenu", contextMenu.getId(), new JMap());
		
	}

	public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
		// TODO Auto-generated method stub
		
	}

	public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

}
