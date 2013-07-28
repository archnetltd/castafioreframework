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
 package org.castafiore.webwizard.forms.impl;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.model.Module;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.navigation.AccordeonModel;
import org.castafiore.ui.navigation.EXAccordeon;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.webwizard.WebWizardService;

public class EXSelectModule extends EXAccordeon implements AccordeonModel {
	
	
	private List<Module> modules;

	public EXSelectModule(String name) {
		super(name);
		modules = SpringUtil.getBeanOfType(WebWizardService.class).getModules();
		setModel(this);
		setHeight(Dimension.parse("350px"));
		setStyle("overflow-y","scroll");
		setStyle("overflow-x", "auto");
	}


	public int getSelectedTab() {
		
		return -1;
	}


	public Container getTabContentAt(TabPanel pane, int index) {
		return new EXModuleContainer("", modules.get(index));
	}


	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return modules.get(index).getName();
	}


	public int size() {
		return modules.size();
	}

	public List<String> getSelectedModules(){
		List<String> result = new ArrayList<String>();
		List<Container> descs =new ArrayList<Container>(); 
		ComponentUtil.getDescendentsOfType(this, descs, EXCheckBox.class);
		for(Container c : descs){
			EXCheckBox cb =(EXCheckBox)c;
			if(cb.isChecked()){
				
				result.add(cb.getName());
			}
		}
		
		return result;
	}
}
