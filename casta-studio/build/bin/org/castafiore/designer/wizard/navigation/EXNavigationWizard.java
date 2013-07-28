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
 package org.castafiore.designer.wizard.navigation;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.model.Module;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;

public class EXNavigationWizard extends EXContainer {

	public EXNavigationWizard(String name) {
		super(name, "div");
		addClass("ui-widget-content ui-corner-all");
		setWidth(Dimension.parse("98%"));
		setHeight(Dimension.parse("100%")); 
		setStyle("margin", "auto");
		List<Module> modules = BaseSpringUtil.getBean("portalModules");

		for (Module mod : modules) {
			addChild(new Row(mod));
		}

	}
	
	public List<String> getSelectedModules(){
		List<Container> result = new ArrayList<Container>();
		List<String> modules = new ArrayList<String>();
		ComponentUtil.getDescendentsOfType(this, result, EXCheckBox.class);
		
		for(Container c : result){
			EXCheckBox cb = (EXCheckBox)c;
			if(cb.isChecked()){
				modules.add(cb.getAttribute("module-title"));
			}
		}
		
		return modules;
	}

	public class Row extends EXXHTMLFragment {

		public Row(Module module) {
			super("Row", ResourceUtil.getDownloadURL("classpath",
					"org/castafiore/designer/wizard/navigation/Row.xhtml"));
			addClass("ex-row");
			addClass("ui-state-highlight");
			addClass("ui-corner-all");

			addChild(ComponentUtil.getContainer("title", "a", module
					.getTitle(), null));
			addChild(ComponentUtil.getContainer("description", "a", module
					.getDescription(), null));
			EXCheckBox cb = new EXCheckBox("selected");
			cb.setAttribute("module-title", module.getTitle());
			addChild(cb);
		}
	}
	
	
	

}
