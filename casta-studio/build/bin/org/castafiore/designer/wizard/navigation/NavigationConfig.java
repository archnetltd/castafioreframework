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

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.tabbedpane.EXTabPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;

public class NavigationConfig extends EXContainer {

	public NavigationConfig(String name) {
		super(name, "div");
		//addClass("ui-widget-content");
		setStyle("float", "left");
		//setStyle("margin-left", "5px");
		//setWidth(Dimension.parse("545px"));
		//setHeight(Dimension.parse("500px"));
		
		EXTabPanel panel = new EXTabPanel("NavConfigTab",new NavConfigTabModel());
		
		panel.setWidth(Dimension.parse("542px"));
		panel.setHeight(Dimension.parse("495px"));
		panel.setStyle("margin", "0px 0px 0px 5px");
		panel.setStyle("float", "left");
		panel.removeClass("ui-corner-all");
		
		addChild(panel);
		
		
	}
	
	
	
	public static class NavConfigTabModel implements TabModel{
		
		private static String[] labels = new String[]{"General", "Action", "Listeners"};

		public int getSelectedTab() {
			
			return 0;
		}

		public Container getTabContentAt(TabPanel pane, int index) {
			if(index == 0)
				return new EXGeneralTab("na");
			else {
				EXTabPanel panel = new EXTabPanel("actions", new ActionsTabModel());
				panel.setStyle("width", "100%");
				return panel;
			}
		}

		public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
			return labels[index];
			
		}

		public int size() {
			
			return labels.length;
		}
		
	}
	
	

}
