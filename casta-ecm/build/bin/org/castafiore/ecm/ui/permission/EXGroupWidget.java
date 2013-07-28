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
 package org.castafiore.ecm.ui.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.security.Role;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.utils.ComponentUtil;

public class EXGroupWidget extends EXGrid {

	public EXGroupWidget(String name) throws Exception{
		super(name, 2, 0);
		addClass("ui-state-active");
		setStyle("border-collapse", "collapse");
		setStyle("margin-left", "2px");
		
		SecurityService service = SpringUtil.getBeanOfType(SecurityService.class);
		List<Role> roles = service.getRoles();
		//int index = 0;
		for(Role g : roles){
			String gname = g.getName();
			int id= g.getId();
			
			EXRow row = addRow();
			row.setName(g.getName());
			EXCheckBox cb = new EXCheckBox("");
			cb.setAttribute("rId", id + "");
			row.addInCell(0,cb);
			cb.addEvent(new Event(){

				
				public void ClientAction(ClientProxy container) {
					container.makeServerRequest(this);
					
				}

				
				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					
					
					EXIconButton ic = container.getAncestorOfType(PermissionButton.class).getDescendentOfType(EXIconButton.class);
					ic.setAttribute("isdirty", "true");
					String text = ic.getLabel();
					if(!text.endsWith("*")){
						ic.setLabel(text + " *");
					}
					
					return true;
				}

				
				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.CHANGE);
			
			Container uiName = ComponentUtil.getContainer("label", "span", gname, null);
			row.addInCell(1,uiName);
			row.getChildByIndex(0).setAttribute("valign", "top");
			row.getChildByIndex(1).setAttribute("valign", "top");
			row.getChildByIndex(0).setAttribute("width", "16px");
			
			//index++;
		}
	}
	
	public List<String> getSelectedGroups(){
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < getRows(); i ++){
			boolean checked = getCell(0, i).getDescendentOfType(EXCheckBox.class).isChecked();
			if(checked){
				result.add(getCell(1, i).getDescendentByName("label").getText());
			}
		}
		
		return result;
	}
	
	
	public Container getRoleRow(String name){
		return getChild(name);
	}

}
