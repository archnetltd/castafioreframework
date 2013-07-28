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

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.designable.widget.EXWidgetConfigForm;
import org.castafiore.designer.layout.EXDroppableWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXWidgetDesignableFactory extends AbstractDesignableFactory {

	public EXWidgetDesignableFactory() {
		super("widgets");
		setText("Widget");
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		EXPanel panel = new EXDroppableWidget();
		panel.setTitle("Simple widget");
		panel.setDraggable(false);
		panel.setStyle("width", "200px");
		panel.setStyle("height","150px");
		setAttribute("title", "Simple widget");
		panel.setDraggable(false);
		panel.setShowCloseButton(false);
		
	
		return panel;
		
	}

	public String getUniqueId() {
		return "core:widget";
	}

	public Map<String, ConfigForm> getAdvancedConfigs(){
		Map<String, ConfigForm> forms = new ListOrderedMap();
		forms.put("Widget Type", new EXWidgetConfigForm("wfs"));
		return forms;
	}
	
	public String[] getRequiredAttributes(){
		return new String[]{"title"};
	}
	
	public void applyAttribute(Container c, String attributeName, String attributeValue){
		if(attributeName.equalsIgnoreCase("title")){
			((EXPanel)c).setTitle(attributeValue);
		}
	}
}
