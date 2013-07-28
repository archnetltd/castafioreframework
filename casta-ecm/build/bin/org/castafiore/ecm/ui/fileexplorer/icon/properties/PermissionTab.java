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
 package org.castafiore.ecm.ui.fileexplorer.icon.properties;



import java.util.List;
import java.util.Map;

import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.types.File;

public class PermissionTab extends EXDynaformPanel {

	private File file = null ;
	
	String readpermissionListId;
	
	String writepermissionListId;
	
	public PermissionTab(String name, File file) {
		super(name, ResourceUtil.getDownloadURL("classpath", "Edit properties"));
		this.file = file;
		setShowHeader(false);
		init();
		
	}
	
	
	private void initPermissions()
	{
		org.castafiore.wfs.security.SecurityManager manager = SpringUtil.getSecurityManager();
		
		String[] readPermissions = manager.getReadPermissions(file);
		
		PermissionList readPermissionList = new PermissionList("readPermissions", readPermissions);
		addField("Read permissions :", readPermissionList);
		readpermissionListId = readPermissionList.getId();
		
		String[] writePermissions = manager.getWritePermissions(file);
		PermissionList writePermissionList = new PermissionList("writePermissions", writePermissions);
		writepermissionListId = writePermissionList.getId();
		addField("Write permission :", writePermissionList);
		
	}
	
	private void init()
	{
		addField("File name : ", new EXLabel("objectName", file.getAbsolutePath()));
		initPermissions();

		final String freadpermissionListId = readpermissionListId;
		
		final String fwritepermissionListId = writepermissionListId;
		
		EXButton editButton = new EXButton("editReadPermissionButton", "Edit Read");
		editButton.addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				try
				{
					Explorer fileExplorer = container.getAncestorOfType(Explorer.class);
					
					
				
					//Dialog dialog = new Dialog("selectPermission", "Select Permission");
					//dialog.setWidth(Dimension.parse("300px"));
					SelectPermission permissionRead = new SelectPermission("sp"){
						@Override
						public void onCancel() {
							this.setAttribute("display", "none");
							this.remove();
							
							
						}

						@Override
						public void onOk(String role, String group) {
							String sourceId = getAttribute("sourceid");
							//Explorer fileExplorer = getAncestorOfType(Explorer.class);
							Application root = getRoot();
							PermissionList pl = (PermissionList)root.getDescendentById(sourceId);
							
							pl.addItem(role + ":" + group);
							
							
						}
					};
					
					permissionRead.setAttribute("sourceid", freadpermissionListId);
					
					//dialog.addChild(permissionRead);
					//EXPanel panel = new EXPanel("selectPermission");
					//panel.setBody(permissionRead);
					//panel.setDraggable(true);
					permissionRead.setDraggable(true);
					permissionRead.setShowCloseButton(true);
					container.getAncestorOfType(Explorer.class).addPopup(permissionRead);
				}
				catch(Exception e)
				{
					throw new UIException(e);
				}
				return true;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, Event.CLICK);
		addButton(editButton);
		
		EXButton editWriteButton = new EXButton("editwritePermissionButton", "Edit Write");
		editWriteButton.addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				try
				{
					Explorer fileExplorer = container.getAncestorOfType(Explorer.class);
					
					SelectPermission permissionWrite = new SelectPermission("sp"){

						@Override
						public void onCancel() {
							this.setAttribute("display", "none");
							this.remove();
							
							
						}

						@Override
						public void onOk(String role, String group) {
							String sourceId = getAttribute("sourceid");
							Explorer fileExplorer = getAncestorOfType(Explorer.class);
							PermissionList pl = (PermissionList)fileExplorer.getDescendentById(sourceId);
							
							pl.addItem(role + ":" + group);
							
							
						}
						
					};
					permissionWrite.setAttribute("sourceid", fwritepermissionListId);
					
					permissionWrite.setDraggable(true);
					permissionWrite.setShowCloseButton(true);
					
					fileExplorer.addPopup(permissionWrite);
				}
				catch(Exception e)
				{
					throw new UIException(e);
				}
				return true;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, Event.CLICK);
		addButton(editWriteButton);
		
		EXButton saveButton = new EXButton("save", "Save");
		saveButton.addEvent(new savePermissionEvent(), Event.CLICK);
		
		EXButton cancelButton = new EXButton("cancel", "Cancel");
		cancelButton.addEvent(CLOSE_EVENT, Event.CLICK);
		
		
		addButton(saveButton);
		
		
		addButton(cancelButton);
		
		
		
	}
	
	public static class savePermissionEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(container.getAncestorOfType(PermissionTab.class).getId(), this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			PermissionTab tab = container.getAncestorOfType(PermissionTab.class);
			
			PermissionList readPermission = (PermissionList)tab.getDescendentByName("readPermissions");
			List<String> readPermissions = (List<String>)readPermission.getValue();
			tab.file.setReadPermissions(null);
			for(String s : readPermissions )
			{
				SpringUtil.getSecurityManager().grantReadPermission(tab.file,s);
			}
			
			
			PermissionList writePermission = (PermissionList)tab.getDescendentByName("writePermissions");
			List<String> writePermissions = (List<String>)writePermission.getValue();
			tab.file.setEditPermissions(null);
			for(String s : writePermissions )
			{
				SpringUtil.getSecurityManager().grantWritePermission(tab.file,s);
			}
			
			try
			{
				tab.file.save();
			}
			catch(Exception e)
			{
				throw new UIException(e);
			}
			return true;
			
			
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}

}
