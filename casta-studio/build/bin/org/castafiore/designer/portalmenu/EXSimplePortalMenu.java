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
 package org.castafiore.designer.portalmenu;

import java.util.List;

import org.castafiore.designer.Studio;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;

public class EXSimplePortalMenu extends EXContainer implements PortalMenu {
	
	private NavigationDTO navigation = null;

	public EXSimplePortalMenu(String name) {
		super("Menu", "ul");
		addClass("ui-widget-header ui-corner-all ui-jquery-simple-menu");
		setText("");
		NavigationDTO root = new NavigationDTO();
		for(int i = 0; i < 5; i ++){
			NavigationDTO menuItem = new NavigationDTO();
			menuItem.setLabel("Menu Item " + i);
			menuItem.setName("MenuItem" + i);
			menuItem.setPageReference("Page Item " + i);
			menuItem.setUri("#");
			root.getChildren().add(menuItem);
		}
		setNavitation(root);
	}

	public NavigationDTO getNavitation() {
		return navigation;
	}

	public void setNavitation(NavigationDTO navigation) {
		try{
			setAttribute("navigation", NavigationDTO.getJSON(navigation).toString());
			this.getChildren().clear();
			this.setRendered(false);
			this.navigation = navigation;
			List<NavigationDTO> children = navigation.getChildren();
			for(NavigationDTO nav : children){
				Container li = ComponentUtil.getContainer("", "li", null, null);
				//li.addClass("ui-corner-all ui-state-default");
				Container a = ComponentUtil.getContainer("", "a", null, null);
				//a.setAttribute("href", "#");
				a.setText("<span>" +nav.getLabel() + "</span>");
				a.setAttribute("pagename", nav.getPageReference());
				a.addEvent(Studio.CHANGE_PAGE_EVENT, Event.CLICK);
				li.addChild(a);
				a.setStyle("cursor", "pointer");
				addChild(li);
				
			}
			
		}catch(Exception e){
			throw new UIException(e);
		}
	}
	
	public void saveCurrentPages(String portalPath)throws Exception{
		
		for(Container c : getChildren()){
			String pagename = c.getChildren().get(0).getAttribute("pagename");
			DesignableUtil.createSamplePage(pagename, portalPath);
		}
		
	}
	
	
	
	
	

}
