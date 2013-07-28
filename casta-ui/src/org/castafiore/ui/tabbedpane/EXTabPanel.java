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
 package org.castafiore.ui.tabbedpane;

import org.castafiore.JQContants;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ComponentUtil;

public class EXTabPanel extends EXContainer implements JQContants , TabPanel{

	protected TabModel model;
	
	private TabRenderer tabRenderer = new JQTabRenderer();
	
	private TabContentDecorator tabContentDecorator = new JQTabContentDecorator();
	
	public EXTabPanel(String name) {
		super(name, "div");
		setAttribute("class",TABS_STYLE);
	}

	public EXTabPanel(String name, TabModel model) {
		this(name);
		setModel(model);
		
		
	}
	public TabModel getModel() {
		return model;
	}
	
	public void setModel(TabModel model) {
		
		this.model = model;
		
		this.getChildren().clear();
		this.setRendered(false);
		if(model == null){
			return;
		}
		
		//create tab header
		Container tabs = ComponentUtil.getContainer("tabs", "ul", null, null);
		tabs.setAttribute("class",TABS_HEADER_STYLE);
		addChild(tabs);
		
		
		
		
		int selectedTab = model.getSelectedTab();
		for(int i = 0; i < model.size(); i ++){
			
			//add tab items
			//String label = model.getTabLabelAt(this, i, i == selectedTab);
			Container tab = tabRenderer.getComponentAt(this, model, i);
			
			
			
			//tab.getChildByIndex(0).setText(label);
			
			tabs.addChild(tab);
			tab.setAttribute("t", i + "");
			tab.setAttribute("init", "false");
			tab.addEvent(EVT_SHOW_TAB, Event.CLICK);
			
			
			//content.setAttribute(name, value)
	
			//add content container
			Container content = ComponentUtil.getContainer("c-" + i, "div", "", null);
			content.setAttribute("init", "false");
			//content.setAttribute("class", TABS_SELECTED_TAB_CONTENT_STYLE);
			content.setDisplay(false);
			addChild(content);
			if(tabContentDecorator != null)
			tabContentDecorator.decorateContent(content);
			
			
			//initialise default selected tab
			if(i == selectedTab){
				tabRenderer.onSelect(this, model, i, tab);
				//content.setAttribute("class", TABS_SELECTED_TAB_CONTENT_STYLE);
				content.setDisplay(true);
				content.addChild(model.getTabContentAt(this, i));
				content.setAttribute("init", "true");
				tab.setAttribute("init", "true");
			}
			
		}
		
	}
	
	
	public TabContentDecorator getTabContentDecorator() {
		return tabContentDecorator;
	}

	public void setTabContentDecorator(TabContentDecorator tabContentDecorator) {
		this.tabContentDecorator = tabContentDecorator;
		setModel(model);
	}

	public TabRenderer getTabRenderer() {
		return tabRenderer;
	}

	public void setTabRenderer(TabRenderer tabRenderer) {
		this.tabRenderer = tabRenderer;
		setModel(model);
	}


	public static class JQTabRenderer implements TabRenderer{

		public Container getComponentAt(TabPanel pane, TabModel model,
				int index) {
			Container tab = ComponentUtil.getContainer("tt", "a" ,"", null);
			tab.setAttribute("href", "#tabs-" + index);
			tab.setText(model.getTabLabelAt(pane, index, false));
			EXContainer tTab = new EXContainer("", "li");
			tTab.setAttribute("class", TABS_INACTIVE_TAB_STYLE);
			tTab.addChild(tab);
			return tTab;
		}

		public void onSelect(TabPanel pane, TabModel model, int index,
				Container tab) {
			tab.setAttribute("class", TABS_ACTIVE_TAB_STYLE);	
		}

		public void onDeselect(TabPanel pane, TabModel model, int index,Container tab) {
			tab.setAttribute("class", TABS_INACTIVE_TAB_STYLE);	
		}

	}
	
	
	public static class JQTabContentDecorator implements TabContentDecorator{

		@Override
		public void decorateContent(Container contentContainer) {
			contentContainer.setAttribute("class", TABS_SELECTED_TAB_CONTENT_STYLE);
			
		}
		
	}
}
