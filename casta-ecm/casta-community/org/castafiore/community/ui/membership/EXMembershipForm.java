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
 package org.castafiore.community.ui.membership;

import org.castafiore.community.ui.CommunityEvents;
import org.castafiore.community.ui.groups.GroupsTableModel;
import org.castafiore.security.Role;
import org.castafiore.security.api.SecurityService;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXMembershipForm extends EXDynaformPanel implements CommunityEvents{

	public EXMembershipForm() {
		super("membershipForm", "Membership");
		addField("Name :*", new EXInput("name"));
		addField("Description :*", (EXTextArea)new EXTextArea("description").setWidth(Dimension.parse("270px")).setHeight(Dimension.parse("90px")));
		addButton(new EXButton("Save", "Save"));
		addButton(new EXButton("Cancel", "Cancel"));
		
		getDescendentByName("Save").addEvent(SAVE_MEMBERSHIP_EVENT, Event.CLICK);
		getDescendentByName("Cancel").addEvent(CLOSE_EVENT, Event.CLICK);
		setWidth(Dimension.parse("400px"));
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "270px");
	}
	
	
	public void setMembership(String name)throws Exception{
		if(name != null){
			getField("name").setValue(name);
			((EXInput)getField("name")).setEnabled(false);
			
			Role role = BaseSpringUtil.getBeanOfType(SecurityService.class).getRole(name);
			getField("description").setValue(role.getDescription());
		}
	}
	
	public Role save()throws Exception{
		String name = getField("name").getValue().toString();
		String description = getField("description").getValue().toString();
		if(!StringUtil.isNotEmpty(name)){
			getField("name").addClass("ui-state-error");
		}else{
			Role result = BaseSpringUtil.getBeanOfType(SecurityService.class).saveOrUpdateRole(name, description, Util.getLoggedOrganization());
			this.remove();
			
			
			EXTable table = (EXTable) getAncestorOfType(PopupContainer.class).getDescendentByName("membershipList");
			table.setModel(new MembershipTableModel());
			table.getAncestorOfType(EXPagineableTable.class).refresh();
			
			return result;
			
		}
		return null;
		
	}

}
