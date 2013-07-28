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
import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ecm.ui.permission.EXRoleWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.navigation.EXAccordeon;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.File;

public class SelectFileEvent implements Event {

	public void ClientAction(ClientProxy component) {
		
		component.mergeCommand(new ClientProxy(".feicon, .casta-node").removeClass("ui-state-active")).addClass("ui-state-active");
		
		component.makeServerRequest(this);
		
	}

	public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
		// TODO Auto-generated method stub
		
	}

	public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
		
		
		File f = ((ICon)component).getFile();
		component.getAncestorOfType(Explorer.class).setSelectedFiles(new String[]{f.getAbsolutePath()});
		EXAccordeon acc = component.getAncestorOfType(Explorer.class).getDescendentOfType(EXAccordeon.class);
		updateInfoContainer(acc,f);
		
		return true;
		
	}
	
	
	private void updateInfoContainer(EXAccordeon c, File file){
		String name = file.getName();
		String owner = file.getOwner();
		String clazz = file.getClazz();
		try{
		((EXCheckBox)c.getDescendentByName("checkOut")).setChecked(file.isLocked());
		Container infoContainer = c.getDescendentByName("infoContainer");
		infoContainer.getDescendentByName("fileName").setText("Name : " + name);
		infoContainer.getDescendentByName("fileOwner").setText("Owner : " + owner);
		String[] parts = StringUtil.split(clazz, ".");
		String type = parts[parts.length-1];
		infoContainer.getDescendentByName("fileType").setText("File type : " + type);
		}catch(Exception e){
			
		}
		
//		 EXRoleWidget roleRWidget = c.getDescendentByName("readPermissionWidget").getDescendentOfType(EXRoleWidget.class);
//		 c.getDescendentByName("readPermissionWidget").setAttribute("fileId", file.getAbsolutePath() +"");
//		 if(roleRWidget != null){
//			 roleRWidget.setPermissions(file.getReadPermissions());
//		 }
//		 
//		 EXRoleWidget roleWWidget = (EXRoleWidget)c.getDescendentByName("writePermissionWidget").getDescendentOfType(EXRoleWidget.class);
//		 c.getDescendentByName("writePermissionWidget").setAttribute("fileId", file.getAbsolutePath() +"");
//		 if(roleWWidget != null){
//			 roleWWidget.setPermissions(file.getEditPermissions());
//		 }
	}

}
