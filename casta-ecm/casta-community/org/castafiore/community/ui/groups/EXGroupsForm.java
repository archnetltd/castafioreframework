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
 package org.castafiore.community.ui.groups;



import org.castafiore.community.ui.CommunityEvents;
import org.castafiore.security.Group;
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
import org.castafiore.wfs.Util;

public class EXGroupsForm extends EXDynaformPanel implements CommunityEvents {

	public EXGroupsForm() {
		super("Group", "Group");
		addField("Name :", new EXInput("name"));
		addField("Description", (EXTextArea)new EXTextArea("description").setWidth(Dimension.parse("270px")).setHeight(Dimension.parse("90px")));
		addButton(new EXButton("Save", "Save"));
		addButton(new EXButton("Cancel", "Cancel"));
		
		getDescendentByName("Save").addEvent(SAVE_GROUP_EVENT, Event.CLICK);
		getDescendentByName("Cancel").addEvent(CLOSE_EVENT, Event.CLICK);
		ComponentUtil.applyStyleOnAll(this, EXInput.class, "width", "270px");
		
		setWidth(Dimension.parse("400px"));
	}
	
	
	public void setGroup(String name)throws Exception{
		if(name != null){
			getField("name").setValue(name);
			((EXInput)getField("name")).setEnabled(false);
			
			Group group = BaseSpringUtil.getBeanOfType(SecurityService.class).getGroup(name);
			getField("description").setValue(group.getDescription());
		}
	}
	
	public Group save()throws Exception{
		String name = getField("name").getValue().toString();
		String description = getField("description").getValue().toString();
		Group group = BaseSpringUtil.getBeanOfType(SecurityService.class).saveOrUpdateGroup(name, description, Util.getLoggedOrganization());
		group.setDescription(description);
		this.remove();
		EXTable table = (EXTable) getAncestorOfType(PopupContainer.class).getDescendentByName("groupsList");
		table.setModel(new GroupsTableModel());
		table.getAncestorOfType(EXPagineableTable.class).refresh();
		
		
		return group;
		
	}

}
