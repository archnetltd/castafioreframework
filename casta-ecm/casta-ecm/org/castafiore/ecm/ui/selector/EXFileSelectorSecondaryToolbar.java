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
 package org.castafiore.ecm.ui.selector;

import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.FileExplorerUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.msgbox.EXPrompt;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.futil;

import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;


public class EXFileSelectorSecondaryToolbar extends EXFileSelectorToolBar {

	public EXFileSelectorSecondaryToolbar() {
		super("EXFileSelectorSecondaryToolbar");
		setStyle("margin-bottom", (String)null);
		removeClass("ui-corner-top");
		addClass("ui-corner-bottom");
		addItem(new EXButtonToolbarItem("uploadButton", "ui-icon-arrowreturnthick-1-n"));
		getDescendentByName("uploadButton").addEvent(SHOW_UPLOAD, Event.CLICK);
		addItem(new EXButtonToolbarItem("newFolderButton", "ui-icon-folder-collapsed"));
		getDescendentByName("newFolderButton").addEvent(SHOW_NEWFOLDER_EVENT, Event.CLICK);
		addItem(new EXButtonToolbarItem("createTemplate", "ui-icon-note"));
		getDescendentByName("createTemplate").addEvent(SHOW_CREATE_TEXT_FORM, Event.CLICK);
	}
	
	
	
	public final static Event SHOW_CREATE_TEXT_FORM = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXPanel app = container.getAncestorOfType(EXPanel.class);
			EXButton button = new EXButton("save", "Ok");
			
			button.addEvent(SAVE_TEXT_FILE_EVENT, Event.CLICK);
			
			EXDynaformPanel panel = new EXDynaformPanel("TextDocument", "Create a text document");
			EXButton closeButton = new EXButton("close", "Cancel");
			closeButton.addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
			panel.setAttribute("currentDir", container.getAncestorOfType(EXFileSelector.class).getCurrentDir());
			panel.addField("Name :",new EXInput("name"));
			panel.addField("Text :", new EXTextArea("text"));
			panel.getDescendentOfType(EXTextArea.class).setStyle("width", "372px");
			panel.getDescendentOfType(EXTextArea.class).setStyle("height", "300px");
			panel.addButton(button);
			panel.addButton(closeButton);
			panel.setDraggable(true);
			panel.setShowCloseButton(true);
			panel.setWidth(Dimension.parse("420px"));
			app.addPopup(panel);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public final static Event SAVE_TEXT_FILE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try
			{
				String name = ((EXInput)container.getAncestorOfType(EXDynaformPanel.class).getDescendentByName("name")).getValue().toString();
				String text = ((EXTextArea)container.getAncestorOfType(EXDynaformPanel.class).getDescendentByName("text")).getValue().toString();
				Directory currentPath = futil.getDirectory(container.getAncestorOfType(EXPanel.class).getAttribute("currentDir"));
				
				BinaryFile file = currentPath.createFile(name, BinaryFile.class); 
			
				file.write(text.getBytes());
				
				
				
				
				currentPath.save();
				container.getAncestorOfType(EXDynaformPanel.class).getParent().getAncestorOfType(EXPanel.class).getDescendentOfType(EXFileSelector.class).refreshTable();
				container.getAncestorOfType(EXPanel.class).remove();
				return true;
			}
			catch(Exception e)
			{
				throw new UIException(e);
			}
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static Event SHOW_UPLOAD = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXPanel app = container.getAncestorOfType(EXPanel.class);
			EXButton button = new EXButton("save", "Ok");
			button.addEvent(SAVE_UPLOADED_FILE_EVENT, Event.CLICK);
			EXDynaformPanel panel = new EXDynaformPanel("Upload", "Upload file");
			EXButton closeButton = new EXButton("close", "Cancel");
			closeButton.addEvent(EXPanel.CLOSE_EVENT, Event.CLICK);
			panel.setAttribute("currentDir", container.getAncestorOfType(EXFileSelector.class).getCurrentDir());
			panel.addField("Select file",new EXUpload("fileName"));
			panel.addButton(button);
			panel.addButton(closeButton);
			panel.setDraggable(true);
			panel.setShowCloseButton(true);
			panel.setWidth(Dimension.parse("420px"));
			app.addPopup(panel);
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static Event SAVE_UPLOADED_FILE_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try
			{
				EXUpload input = (EXUpload)container.getAncestorOfType(EXDynaformPanel.class).getDescendentByName("fileName");
				String currentPath = container.getAncestorOfType(EXPanel.class).getAttribute("currentDir");
				
				BinaryFile file = (BinaryFile)input.getFile();
				file.putInto(currentPath);
				//file.seta
				
				
				file.save();
				container.getAncestorOfType(EXDynaformPanel.class).getParent().getAncestorOfType(EXPanel.class).getDescendentOfType(EXFileSelector.class).refreshTable();
				container.getAncestorOfType(EXPanel.class).remove();
				return true;
			}
			catch(Exception e)
			{
				throw new UIException(e);
			}
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	public final static Event SHOW_NEWFOLDER_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			final EXFileSelector fileSelector = container.getAncestorOfType(EXFileSelector.class);
			
			EXPrompt prompt = new EXPrompt("newFolder", "Create new folder","Enter the name of the folder:"){

				@Override
				public boolean onOk(Map<String, String> request) {
					String folderName = getInputValue();
					
					if(StringUtil.isNotEmpty(folderName)){
					
					
					
						Directory currentPath = futil.getDirectory(fileSelector.getCurrentDir());
						
						
						//director.setName(folderName);
						
						try
						{
							currentPath.save();
							
						}
						catch(Exception e)
						{
							throw new RuntimeException(e);
						}
						
						fileSelector.refreshTable();
						
						FileExplorerUtil.refreshNodeInTree(fileSelector, currentPath.getAbsolutePath());
					
						return true;
					}
					else
					{
						
						getInput().setStyle("background-color", "red");
						return false;
						
					}
					
				}
				
			};
			fileSelector.getAncestorOfType(EXPanel.class).addPopup(prompt);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
