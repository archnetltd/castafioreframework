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
 package org.castafiore.designer.config.ui;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;


public class ConfigPanelTabModel implements TabModel{
	
	private Container container;
	
	//private Map<String, DesignerInput> advancedConfigs = null;
	
	private String[] requiredAttributes;

	private final static String[] labels = new String[]{"Properties", "Styles", "Events"};
	
	private ConfigValues pValues;
	
	public ConfigPanelTabModel(Container container, String[] requiredAttributes, ConfigValues vals){
		this.container = container;
		//this.advancedConfigs = advancedConfigs;
		this.requiredAttributes = requiredAttributes;
		this.pValues = vals;
	}
	
	public int getSelectedTab() {
		
		return 0;
	}

	public EXContainer getTabContentAt(TabPanel pane, int index) {
		if(index == 0){
			EXPropertiesConfigForm form = new EXPropertiesConfigForm();
			form.setContainer(container, requiredAttributes, pValues);
			return form;
		}else if(index == 1){
			EXStyleConfigForm form = new EXStyleConfigForm();
			form.setContainer(container, requiredAttributes, pValues);
			return form;
			
		}else{
			EXEventConfigForm form = new EXEventConfigForm();
			form.setContainer(container, requiredAttributes, pValues);
			return form;
		}
	}

	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}

	public int size() {
		return labels.length;
	}

}
