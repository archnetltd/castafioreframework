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
 package org.castafiore.ecm.ui.fileexplorer.icon.dnd;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.FileExplorerUtil;
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.futil;

import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.Shortcut;

public class DropIconEvent implements Event {

	public void ClientAction(ClientProxy application) {
		JMap option = new JMap();
		option.put("source", application.getDragSourceId());
		application.mask();
		application.makeServerRequest( option, this);
	}

	public void Success(ClientProxy component,
			Map<String, String> requestParameters) throws UIException {
		// TODO Auto-generated method stub
		
	}

	public boolean ServerAction(final Container component,
			Map<String, String> requestParameters) throws UIException {
		try
		{
			String source = requestParameters.get("source");
			//final Container container = component;
			final ICon icon = (ICon)component.getAncestorOfType(Explorer.class).getDescendentById(source);
			
			EXDynaformPanel panel = new EXDropIconPanel("dragndrop",(ICon)component,icon);
			component.getAncestorOfType(PopupContainer.class).addPopup(panel);
		}
		catch(Exception e)
		{
			throw new UIException(e);
		}
		
		
		return true;
	}

}
