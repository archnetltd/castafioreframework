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
 package org.castafiore.ecm.ui.fileexplorer.accordeon;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.castafiore.ecm.ui.fileexplorer.Explorer;
import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ecm.ui.fileexplorer.events.CreateDirEvent;
import org.castafiore.ecm.ui.fileexplorer.events.OpenFileEvent;
import org.castafiore.ecm.ui.fileexplorer.events.UploadFileEvent;
import org.castafiore.ecm.ui.permission.PermissionButton;
import org.castafiore.ecm.ui.permission.PermissionButton.SavePermissionTemplate;
import org.castafiore.security.Group;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Drive;
import org.castafiore.wfs.types.File;

public class FileExplorerAccordeonModel implements TabModel{
	
	//private final static String[] headers = new String[]{"Actions", "Shared", "Collaborators"};
	private List<String> groups =new ArrayList<String>();
	
	public FileExplorerAccordeonModel()throws Exception{
		String remoteUser = Util.getRemoteUser();
		List<Group> grps = SpringUtil.getSecurityService().getGroups(remoteUser, "manager");
		for(Group g : grps){
			groups.add(g.getName());
		}
	}

	private EXContainer getActionsContainer()
	{
		EXContainer container = new EXContainer("", "div");
		
		EXButtonWithLabel addFolder = new EXButtonWithLabel("" , "New folder", "AddFolder.gif", "medium");
		addFolder.addEvent(new CreateDirEvent(), Event.CLICK);
		container.addChild(addFolder);
		
		EXButtonWithLabel upload = new EXButtonWithLabel("" , "Upload", "UploadFile.gif", "medium");
		upload.addEvent(new UploadFileEvent(), Event.CLICK);
		container.addChild(upload);
		
		container.setStyle("text-align", "left");
		container.setWidth(Dimension.parse("100%"));
		return container;
	}


	public int getSelectedTab() {
		return 0;
	}


	public Container getTabContentAt(TabPanel pane, int index) {
		try{
			if(index == 0){
				return  getActionsContainer();
			}else if(index == 1){
				return new EXSharedContainer("shared");
			}else {
				String grp = groups.get(index-2);
				
					List<User> employees = SpringUtil.getSecurityService().getUsers("employee:" + grp, Util.getLoggedOrganization());
					List<User> clients = SpringUtil.getSecurityService().getUsers("client:" + grp, Util.getLoggedOrganization());
					return new EXUsersListContainer("", employees, clients);
				
			}
		}catch(Exception e){
			throw new UIException(e);
		}
	}


	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		
		if(index == 0){
			return "Actions";
		}else if(index == 1){
			return "Shared";
		}else{
			return groups.get(index-2);
		}
			
		
		
	}


	public int size() {
		return groups.size() + 2;
	}

}
