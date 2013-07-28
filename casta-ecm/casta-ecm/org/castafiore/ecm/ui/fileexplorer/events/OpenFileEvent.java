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
 package org.castafiore.ecm.ui.fileexplorer.events;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.EXFileExplorer;
import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;


/**
 * Abstract open file event opens a file in a smart way.
 * This class can be used in spring
 * 
 * 
 * Usage:
 * The following parameters should be passed in request
 * params - path = 		file to open
 * 			
 * 			componentId(optional) = id of component into which to open editor
 * 			if componentId is not passed in parameter, an {@link EXFileExplorer} will be loaded from spring container.
 * 			Not that, {@link EXFileExplorer} has scope casta-app. Meaning that only 1 instance of this class per application
 * 	
 * 			
 * @author kureem
 * 
 *
 */
public abstract class OpenFileEvent extends AbstractFileEvent  {

	public abstract void ClientAction(ClientProxy application); 
	
	

	public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
		
		checkRequest(request);
		
		String path = request.get("path");
		//String componentId = request.get("componentId");
		
		
		File f = getRepositoryService().getFile(path, Util.getRemoteUser());
		
		Explorer explorer = null;
		
		explorer = container.getAncestorOfType(Explorer.class);
		
		explorer.OnFileSelected(f);
		
		return true;
	}
	
	

}
