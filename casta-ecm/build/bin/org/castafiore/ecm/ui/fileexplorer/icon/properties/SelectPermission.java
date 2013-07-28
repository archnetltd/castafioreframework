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

import org.castafiore.security.Group;
import org.castafiore.security.Role;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.EXSelect;



public abstract class SelectPermission extends EXDynaformPanel{
	
	

	public SelectPermission(String name)throws Exception {
		super(name, "Select permission");		
		init();
	}
	
	public void init()throws Exception
	{
		SecurityService service = SpringUtil.getBean("securityService");
		final List<Role> roles = service.getRoles();
		
		
		final List<Group> groups = service.getGroups();
		
		
		DataModel dRoles = new DataModel(){
			
			

			public int getSize() {
				return roles.size();
			}

			public Object getValue(int index) {
				// TODO Auto-generated method stub
				return roles.get(index);
			}
			
		};
		
		
		DataModel dGroups = new DataModel(){

			public int getSize() {
				return groups.size();
			}

			public Object getValue(int index) {
				return groups.get(index).getName();
			}
			
		};
		
		EXSelect uiGroups = new EXSelect("group",dGroups);
		addField("Group :",uiGroups);
		uiGroups.setWidth(Dimension.parse("200px"));
		
		EXSelect uiRoles = new EXSelect("role", dRoles);
		addField("Roles :", uiRoles);
		uiRoles.setWidth(Dimension.parse("200px"));
		
		EXButton okButton = new EXButton("okButton", "OK");
		addButton(okButton);
		okButton.addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(container.getAncestorOfType(SelectPermission.class).getId(),this );
				
			}

			public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
				SelectPermission ui = container.getAncestorOfType(SelectPermission.class);
				StatefullComponent group = (StatefullComponent)ui.getDescendentByName("group");
				StatefullComponent role = (StatefullComponent)ui.getDescendentByName("role");
				
				String sGroup = group.getValue().toString();
				String sRole = role.getValue().toString();
				ui.onOk(sRole, sGroup);
				
				return true;
				
			}

			public void Success(ClientProxy container, Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, org.castafiore.ui.events.Event.CLICK);
		
		
		EXButton cancelButton = new EXButton("cancelButton", "Cancel");
		cancelButton.addEvent(CLOSE_EVENT , Event.CLICK);
		addButton(cancelButton);
		
	}
	
	public abstract void onOk(String role, String group);
	
	
	public abstract void onCancel();
	
	

}
