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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.Corners;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.js.JMap;

public  class PermissionButton extends EXContainer {
	
	private SavePermissionTemplate saveTemplate;

	public PermissionButton(String name, String btnTitle, SavePermissionTemplate template) {
		this(name,btnTitle);
		this.saveTemplate = template;
	}
	
	public PermissionButton(String name, String btnTitle) {
		super(name, "div");
		EXIconButton writePermission = new EXIconButton("button", btnTitle, Icons.ICON_TRIANGLE_1_E);
		writePermission.setStyle("padding-left", "0");
		writePermission.setAttribute("isOpen", "false");
		writePermission.setIconPosition(2);
		writePermission.setWidth(Dimension.parse("134px"));
		setAttribute("isdirty", "false");
		writePermission.setCorners(Corners.TOP);

		writePermission.addEvent(new Event(){

		
			public void ClientAction(ClientProxy container) {
				//container.makeServerRequest(this);
				
				ClientProxy whenDirty = container.clone().makeServerRequest(new JMap().put("saveAndClose", true),this,"Do you want to save?");
				ClientProxy whenClean = container.clone().makeServerRequest(this);
				ClientProxy whenOpen = container.clone().IF(container.getAttribute("isdirty").equal("true"), whenDirty, whenClean);
				ClientProxy whenClose = container.clone().makeServerRequest(this);
				container.IF(container.getAttribute("isOpen").equal("false"), whenClose, whenOpen);
				
				
				
			}

			
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				
				try{
					EXRoleWidget widget = container.getParent().getDescendentOfType(EXRoleWidget.class);
					if("false".equalsIgnoreCase(container.getAttribute("isOpen"))){
						
						if(widget == null){
							widget = new EXRoleWidget("");
							widget.setStyle("float", "left");
							container.getParent().addChild(widget);
						}
						
						request.put("idToSlide", widget.getId());
						container.setAttribute("isOpen", "true");
						((EXIconButton)container).setIcon(Icons.ICON_TRIANGLE_1_S);
					}else{
						container.setAttribute("isOpen", "false");
						request.put("idToSlideUp", widget.getId());
						((EXIconButton)container).setIcon(Icons.ICON_TRIANGLE_1_E);
						
						
						String saveAndClose = request.get("saveAndClose");
						if("true".equalsIgnoreCase(saveAndClose)){
							//add piece of code to physically save permission
							savePermissions(getDescendentOfType(EXRoleWidget.class).getPermissions());
							
							//reset state to clean
							container.setAttribute("isdirty", "false");
							((EXIconButton)container).setLabel(((EXIconButton)container).getLabel().replace(" *", ""));
							
						}
						
					}
					
					
				}catch(Exception e){
					throw new UIException(e);
				}
				
				return true;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				ClientProxy p = new ClientProxy("#" + request.get("idToSlide")).setStyle("display", "none").slideDown(200);
				ClientProxy p1 = new ClientProxy("#" + request.get("idToSlideUp")).setStyle("display", "block").slideUp(200);
				
				container.mergeCommand(p).mergeCommand(p1);
				
			}
			
		}, Event.CLICK);
		
		addChild(writePermission);
	}
	
	protected void savePermissions(List<String> permissions){
		if(saveTemplate != null){
			saveTemplate.savePermission(permissions, this);
		}
	}
	
	
	
	public SavePermissionTemplate getSaveTemplate() {
		return saveTemplate;
	}

	public void setSaveTemplate(SavePermissionTemplate saveTemplate) {
		this.saveTemplate = saveTemplate;
	}



	public interface SavePermissionTemplate extends Serializable{
		public void savePermission(List<String> permissions, PermissionButton This);
	}

}
