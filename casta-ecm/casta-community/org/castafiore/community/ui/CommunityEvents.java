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
 package org.castafiore.community.ui;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.community.ui.groups.EXGroupsForm;
import org.castafiore.community.ui.groups.GroupsTableModel;
import org.castafiore.community.ui.membership.EXMembershipForm;
import org.castafiore.community.ui.membership.MembershipTableModel;
import org.castafiore.community.ui.users.EXUserForm;
import org.castafiore.security.Group;
import org.castafiore.security.Role;
import org.castafiore.security.User;
import org.castafiore.security.api.SecurityService;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.BaseSpringUtil;

public interface CommunityEvents {
	
	public final static Event DELETE_USER_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to delete this user?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String username = container.getAttribute("username");
			try{
				BaseSpringUtil.getBeanOfType(SecurityService.class).deleteUser(username);
				container.getAncestorOfType(EXUsersTab.class).ServerAction(new EXContainer("refresh", "div"), new HashMap<String,String>());
			}catch(Exception e){
				throw new UIException(e);
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
		}
		
	};
	
	public final static Event DELETE_GROUP_EVENT = new Event(){
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to delete this group?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String name = container.getAttribute("groupname");
			try{
				BaseSpringUtil.getBeanOfType(SecurityService.class).deleteGroup(name);
			}catch(Exception e){
				throw new UIException(e);
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
		}
	};
	
	public final static Event DELETE_MEMBERSHIP_EVENT = new Event(){
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this, "Do you really want to delete this membership?");
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String name = container.getAttribute("rolename");
			try{
				BaseSpringUtil.getBeanOfType(SecurityService.class).deleteRole(name);
			}catch(Exception e){
				throw new UIException(e);
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
		}
	};
	
	
	public final static Event SHOW_MEMBERSHIP_FORM_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXTable.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				String name = container.getAttribute("rolename");
				
				EXMembershipForm form = new EXMembershipForm();
				form.setMembership(name);
				container.getAncestorOfType(PopupContainer.class).addPopup(form.setStyle("z-index", "4000"));
			}catch(Exception e){
				throw new UIException(e);
			}
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
		}
		
	};
	
	public final static Event SHOW_GROUP_FORM_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXPanel.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				String name = container.getAttribute("groupname");
				
				EXGroupsForm form = new EXGroupsForm();
				form.setGroup(name);
				container.getAncestorOfType(PopupContainer.class).addPopup(form.setStyle("z-index", "4000"));
			}catch(Exception e){
				throw new UIException(e);
			}
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
		}
		
	};
	
	public final static Event SHOW_USER_FORM_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXPanel.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				String name = container.getAttribute("username");
				User u = BaseSpringUtil.getBeanOfType(SecurityService.class).loadUserByUsername(name);
				EXUserForm form = new EXUserForm(u);
				//form.setGroup(name);
				container.getAncestorOfType(PopupContainer.class).addPopup(form.setStyle("z-index", "4000"));
			}catch(Exception e){
				throw new UIException(e);
			}
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
		}
		
	};

	
	public final static Event SAVE_GROUP_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXPanel.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				Group group = container.getAncestorOfType(EXGroupsForm.class).save();
				EXTable table = (EXTable)container.getAncestorOfType(EXCommunity.class).getDescendentByName("groupsList");
				GroupsTableModel  model = (GroupsTableModel)table.getModel();
				Group g = model.getGroup(group.getName());
				if(g != null){
					g.setDescription(group.getDescription());
					table.changePage(table.getCurrentPage());
					request.put("success", "true");
				}
	
				
			}catch(Exception e){
				throw new UIException("There was an error saving the group", e);
			}
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			if(request.containsKey("success"))
				container.getAncestorOfType(EXPanel.class).fadeOut(100);
		}
		
	};
	
	public final static Event SAVE_MEMBERSHIP_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXPanel.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			try{
				//membershipList
				
				Role role = container.getAncestorOfType(EXMembershipForm.class).save();
				EXTable table =(EXTable)container.getAncestorOfType(EXCommunity.class).getDescendentByName("membershipList");
				MembershipTableModel model = (MembershipTableModel)table.getModel();
				
				Role nRole = model.getRole(role.getName());
				if(nRole != null){
					nRole.setDescription(role.getDescription());
					table.changePage(table.getCurrentPage());
					request.put("success", "true");
				}
				
				
				
			}catch(Exception e){
				throw new UIException(e);
			}
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			if(request.containsKey("success"))
				container.getAncestorOfType(EXPanel.class).fadeOut(100);
		}
		
	};
	
	
	public final static Event GENERIC_FORM_METHOD_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXPanel.class));
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			try{
				String methodName = container.getAttribute("method");
				EXDynaformPanel panel = container.getAncestorOfType(EXDynaformPanel.class);
				panel.getClass().getMethod(methodName, null).invoke(panel, null);
			}catch(Exception e){
				throw new UIException(e);
			}
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
