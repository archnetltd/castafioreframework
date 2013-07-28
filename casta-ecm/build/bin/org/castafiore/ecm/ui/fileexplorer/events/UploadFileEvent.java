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

import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;

public class UploadFileEvent implements Event {

	public void ClientAction(ClientProxy application) {
		application.mask(application.getAncestorOfType(Explorer.class));
		application.makeServerRequest( this);
		
	}

	public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
		
		final Explorer fileExplorer = component.getAncestorOfType(Explorer.class);
		
		
		EXButton button = new EXButton("save", "Ok");
		button.addEvent(new Event(){

			public void ClientAction(ClientProxy application) {
				application.mask(application.getAncestorOfType(Explorer.class));
				application.makeServerRequest(this);
				
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				try
				{
					EXUpload input = (EXUpload)component.getAncestorOfType(EXDynaformPanel.class).getDescendentByName("fileName");
					
					String currentPath = fileExplorer.getCurrentAddress();
					
					Object value = input.getValue();
					if(value != null && value instanceof BinaryFile){
					
						
						BinaryFile file = (BinaryFile)input.getFile(); 
						file.putInto(currentPath);
						
						//file.save();
						file.getParent().save();
					}else if(value != null && value instanceof List){
						List bfs = (List)value;
						Directory parent = null;
						for(Object o : bfs){
							BinaryFile file = (BinaryFile)o;
							file.putInto(currentPath);
							file.getParent().save();
						}
						
						
					}
					
					fileExplorer.getView().refreshView(currentPath);
					
					component.getAncestorOfType(EXPanel.class).remove();
					return true;
				}
				catch(Exception e)
				{
					throw new UIException(e);
				}
			}
			
		}, Event.CLICK);
		
		EXDynaformPanel panel = new EXDynaformPanel("Upload", "Upload file");
		EXButton closeButton = new EXButton("close", "Cancel");
		closeButton.addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
		panel.addField("Select file",new EXUpload("fileName"));
		panel.addButton(button);
		panel.addButton(closeButton);
		panel.setDraggable(true);
		panel.setShowCloseButton(true);
		
		fileExplorer.addPopup(panel);
		panel.setWidth(Dimension.parse("500px"));
		
		
		
		return true;
	}

	public void Success(ClientProxy component, Map<String, String> requestParameters)
			throws UIException {
		// TODO Auto-generated method stub
		
	}		
	

}
