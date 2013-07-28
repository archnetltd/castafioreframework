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
 package org.castafiore.designer.explorer.event;

import java.util.Map;

import org.castafiore.ecm.ui.selector.EXFileSelector;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;

public class SelectFolderInTreeEvent implements Event {

	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String path = container.getAttribute("path");
		
		Directory dir =BaseSpringUtil.getBeanOfType(RepositoryService.class).getDirectory(path, Util.getRemoteUser());
		
		container.getAncestorOfType(EXFileSelector.class).setDirectory(dir);
		
		
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
