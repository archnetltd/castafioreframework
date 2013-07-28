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
 package org.castafiore.designer.designable;

import java.util.Map;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.designer.portalmenu.EXSimplePortalMenu;
import org.castafiore.designer.portalmenu.PortalMenu;
import org.castafiore.designer.portalmenu.config.EXPortalMenuConfiguration;
import org.castafiore.ui.Container;
import org.json.JSONObject;

public class EXPortalMenuDesignableFactory extends AbstractDesignableFactory {

	public EXPortalMenuDesignableFactory() {
		super("EXPortalMenuDesignableFactory");
		setText("Simple menu");
	}

	@Override
	public String getCategory() {
		return "Portal";
	}

	@Override
	public Container getInstance() {
		EXSimplePortalMenu menu = new EXSimplePortalMenu("Menu");
		return menu;
	}

	public String getUniqueId() {
		
		return "portal:simple-menu";
	}

	@Override
	public Map<String, ConfigForm> getAdvancedConfigs() {
		
		Map<String, ConfigForm> forms = super.getAdvancedConfigs();
		forms.put("Navigation", new EXPortalMenuConfiguration());
		return forms;
	}

	@Override
	public void applyAttribute(Container c, String attributeName,String attributeValue) {
		if(attributeName.equalsIgnoreCase("navigation")){
			String navigation =attributeValue;
			try{
				
				JSONObject obj = new JSONObject(navigation);
				NavigationDTO nav = NavigationDTO.getObject(obj);
				((PortalMenu)c).setNavitation(nav);
			}catch(Exception e){
				//throw new UIException(e);
				e.printStackTrace();
			}
		}
	}

	@Override
	public String[] getRequiredAttributes() {
		return null;
	}
}
