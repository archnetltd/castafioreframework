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
 package org.castafiore.designer.wizard.menu;

import java.util.List;
import java.util.Map;

import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.designer.portalmenu.EXSimplePortalMenu;
import org.castafiore.designer.wizard.EXWizard;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.utils.BaseSpringUtil;

public class EXPortalMenuSelector extends EXGrid {

	public EXPortalMenuSelector(String name) {
		super(name, 2, getMenuStyles().size());
		setAttribute("cellspacing", "10");
		setAttribute("width", "100%");
		addClass("ui-widget");
		addClass("ui-widget-content");
		List<String> menus = getMenuStyles();
		
		for(int i = 0; i < menus.size(); i ++){
			String s = menus.get(i);
			EXSimplePortalMenu pMenu = new EXSimplePortalMenu(s);
			addInCell(0, i, pMenu);
			
			EXIconButton button = new EXIconButton(s + "_button", Icons.ICON_CHECK);
			button.addEvent(SELECT_MENU_EVEN, Event.CLICK);
			button.setAttribute("menu-class", s);
			addInCell(1, i, button);
			pMenu.setNavitation(getDummyNav());
			
		}
		// TODO Auto-generated constructor stub
	}
	
	
	private static List<String> getMenuStyles(){
		return BaseSpringUtil.getBean("portalMenu");
	}
	
	public static NavigationDTO getDummyNav(){
		
		NavigationDTO result = new NavigationDTO();
		String[] labels = new String[]{"Home", "Products", "About us", "Contact us"};
		
		for(String s :labels){
			NavigationDTO na = new NavigationDTO();
			na.setLabel(s);
			na.setUri(s);
			na.setPageReference(s);
			result.getChildren().add(na);
		}
		return result;
	}
	
	
	private final static Event SELECT_MENU_EVEN = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String menuClass = container.getAttribute("menu-class");
			
			EXSimplePortalMenu pMenu = (EXSimplePortalMenu)DesignableUtil.getInstance("portal:simple-menu");
			pMenu.setStyleClass(menuClass);
			pMenu.setNavitation(getDummyNav());
			//pMenu.setAttribute("des-id", "portal:simple-menu");
			
			container.getAncestorOfType(EXWizard.class).setLayoutMenu(pMenu);
			container.getAncestorOfType(EXWizard.class).nextWindow();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
