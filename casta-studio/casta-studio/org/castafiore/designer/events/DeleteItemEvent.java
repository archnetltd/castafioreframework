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
 package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.ui.Container;
import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class DeleteItemEvent implements Event {
	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container,
			Map<String, String> request) throws UIException {
		
		Container cont = container.getAncestorOfType(EXConfigItem.class).getContainer();
		if(cont instanceof LayoutContainer){
			cont.getParent().getAncestorOfType(LayoutContainer.class).removeChildFromLayout(cont.getId());
		}else{
			cont.getAncestorOfType(LayoutContainer.class).removeChildFromLayout(cont.getId());
		}
		//container.getAncestorOfType(ConfigItem.class).container.remove();
		
		container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).getParent().setRendered(false);
		container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).remove();
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
}
