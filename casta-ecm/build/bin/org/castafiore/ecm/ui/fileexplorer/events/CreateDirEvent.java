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

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.FileExplorerUtil;
import org.castafiore.ecm.ui.fileexplorer.icon.EXIcon;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.msgbox.EXPrompt;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.futil;
import org.castafiore.wfs.types.Directory;

/**
 * Used in fileExplorerAccordeon
 * @author kureem
 *
 */
public class CreateDirEvent implements Event {
	
	public void ClientAction(ClientProxy application) {
		application.getAncestorOfType(Explorer.class);
		application.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
		
		final Explorer fileExplorer = component.getAncestorOfType(Explorer.class);
		
		EXPrompt prompt = new EXPrompt("newFolder", "Create new folder","Enter the name of the folder:"){

			@Override
			public boolean onOk(Map<String, String> request) {
				String folderName = getInputValue();
				
				if(StringUtil.isNotEmpty(folderName)){
				
				
				
					Directory currentPath = futil.getDirectory(fileExplorer.getCurrentAddress());
					
					Directory director = currentPath.createFile(folderName, Directory.class);

					
					try
					{
						currentPath.save();
						
					}
					catch(Exception e)
					{
						throw new RuntimeException(e);
					}
					
					EXIcon icon = new EXIcon(director.getName(), director);
					fileExplorer.getView().addItem(director);
					
					//FileExplorerUtil.refreshNodeInTree(fileExplorer, currentPath.getAbsolutePath());
					
					return true;
				}
				else
				{
					
					getInput().setStyle("background-color", "red");
					return false;
					
				}
				
			}
			
		};
		prompt.setWidth(Dimension.parse("400px"));
		fileExplorer.addPopup(prompt);
		
		
		
		return true;
	}

	public void Success(ClientProxy component, Map<String, String> requestParameters)
			throws UIException {
		
		
	}		

}
