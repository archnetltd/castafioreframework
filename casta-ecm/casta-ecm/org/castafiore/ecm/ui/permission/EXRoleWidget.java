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

import org.castafiore.security.Group;
import org.castafiore.security.api.SecurityService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

public class EXRoleWidget extends EXGrid {

	public EXRoleWidget(String name)throws Exception {
		super(name, 3, 0);
		addClass("ui-widget");
		addClass("ui-corner-all");
		setStyle("border-collapse", "collapse");
		setStyle("margin-left", "2px");
		setWidth(Dimension.parse("159px"));
		SecurityService service = SpringUtil.getBeanOfType(SecurityService.class);
		List<Group> groups = service.getGroups();
		//int index = 0;
		for(Group g : groups){
			String gname = g.getName();
			int id= g.getId();
			EXRow row = addRow();
			row.setAttribute("grp-name", g.getName());
			EXCheckBox cb = new EXCheckBox("");
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
					
					List<Container> checkBoxes = new ArrayList<Container>();
					ComponentUtil.getDescendentsOfType(container.getParent().getParent().getDescendentOfType(EXGroupWidget.class),checkBoxes, EXCheckBox.class);
					
					
					for(Container c : checkBoxes){
						((EXCheckBox)c).setChecked(((EXCheckBox)container).isChecked());
					}
					return true;
				}

				
				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
				
				
			}, Event.CHANGE);
			cb.setAttribute("rId", id + "");
			row.addInCell(0,cb);
			
			Container uiName = ComponentUtil.getContainer("label", "span", gname, null);
			row.addInCell(1,uiName);
			
			Container span = ComponentUtil.getContainer("", "span", null, "ui-icon ui-icon-circle-plus");
			span.setAttribute("close", "true");
			span.addEvent(new Event(){

				public void ClientAction(ClientProxy container) {
					
					ClientProxy open = container.clone().setAttribute("close", "false").removeClass("ui-icon-circle-plus").addClass("ui-icon-circle-minus").getParent().getParent().getDescendentByName("childWidget").slideDown(200);
					ClientProxy close = container.clone().setAttribute("close", "true").removeClass("ui-icon-circle-minus").addClass("ui-icon-circle-plus").getParent().getParent().getDescendentByName("childWidget").slideUp(200);
					container.IF(container.getAttribute("close").equal("true"), open, close);
					//container.getParent().getParent().getDescendentByName("childWidget").slideDown(200);
					//container.makeServerRequest(this);
					
				}

				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					return false;
				}

				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.CLICK);
			
			row.addInCell(2,span);
			
			
			row.addClass("ui-widget-content");
			row.getChildByIndex(0).setAttribute("valign", "top");
			row.getChildByIndex(1).setAttribute("valign", "top");
			row.getChildByIndex(2).setAttribute("valign", "top");
			row.getChildByIndex(1).setAttribute("width", "125px");
			
			
			EXContainer gw = new EXContainer("childWidget", "div");
			gw.setStyle("padding-top", "3px");
			gw.setStyle("padding-left", "1px");
			gw.setDisplay(false);
			gw.addClass("roles-class");
			EXGroupWidget gWidget = new EXGroupWidget("");
			gWidget.setWidth(Dimension.parse("100%"));
			gw.addChild(gWidget);
			row.addInCell(1,gw);
			
			//index++;
		}
		
	}
	
	
	public List<String> getPermissions(){
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < getRows(); i ++){
			boolean checked = getCell(0, i).getDescendentOfType(EXCheckBox.class).isChecked();
			if(checked){
				
				List<String> selectedGroups = getCell(1, i).getDescendentOfType(EXGroupWidget.class).getSelectedGroups();
				String role = getCell(1, i).getChild("label").getText();
				
				for(String group : selectedGroups){
					result.add(group + ":" + role );
				}
				
			}
		}
		return result;
	}
	
	
	public void setPermissions(String permissions){
		reset();
		if(!StringUtil.isNotEmpty(permissions)){
			
		}else
			setPermissions(StringUtil.split(permissions, ";"));
	}
	
	public void reset(){
		
		EXIconButton ic = getAncestorOfType(PermissionButton.class).getDescendentOfType(EXIconButton.class);
		ic.setAttribute("isdirty", "false");
		String text = ic.getLabel();
		if(text.endsWith("*")){
			ic.setLabel(text.replace(" *", ""));
		}
		//getAncestorOfType(PermissionButton.class).getDescendentOfType(EXIconButton.class).setAttribute("isdirty", "false");
		List<Container> checkBoxes =new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, checkBoxes,EXCheckBox.class);
		
		for(Container c : checkBoxes){
			((EXCheckBox)c).setChecked(false);
		}
	}
	
	public void setPermissions(String[] permissions){
		
		
		
		for(String permision : permissions){
			if(permision.equals("*")){
				
			}else{
				String[] parts = StringUtil.split(permision, ":");
				String group = parts[1];
				String role = parts[0];
				Container row = getGroupRow(group);
				if(row != null){
					row.getChildByIndex(0).getDescendentOfType(EXCheckBox.class).setChecked(true);
					Container roleRow = getRoleRow(row, role);
					roleRow.getChildByIndex(0).getDescendentOfType(EXCheckBox.class).setChecked(true);
				}
				
			}
		}
	}
	
	private Container getGroupRow(String name){
		for(int i = 0; i < getRows(); i++){
			if(getChildByIndex(i).getAttribute("grp-name").equals(name))
			{
				return getChildByIndex(i);
			}
		}
		return null;
	}
	
	private Container getRoleRow(Container groupRow, String roleName){
		EXGroupWidget widget = groupRow.getDescendentOfType(EXGroupWidget.class);
		return widget.getRoleRow(roleName);
		
		
		
	}
}
