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
package org.castafiore.ui.ex.tab;

import org.castafiore.JQContants;
import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.tab.TabContentDecorator;
import org.castafiore.ui.tab.TabModel;
import org.castafiore.ui.tab.TabPanel;
import org.castafiore.ui.tab.TabRenderer;
import org.castafiore.utils.ComponentUtil;

/**
 * This class represents a tab panel.<br>
 * It is lazy loaded. Meaning that the content are instantiated and loaded on
 * browser only when it is opened.<br>
 * Once content is loaded, it is cached in memory and browser.<br>
 * The content of the panel is delegated to the {@link TabModel}<br>
 * The layout of the tab header is handled to the {@link TabRenderer}. A default
 * implementation of JQuery UI is provided.
 * 
 * 
 * @author arossaye
 * 
 */
public class UITabPanel extends EXContainer implements JQContants, TabPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected TabModel model;

	private TabRenderer tabRenderer = new JQTabRenderer();

	private TabContentDecorator tabContentDecorator = new JQTabContentDecorator();

	public UITabPanel(String name) {
		super(name, "div");
		setAttribute("class", TABS_STYLE);
	}

	public UITabPanel(String name, TabModel model) {
		this(name);
		setModel(model);

	}

	public TabModel getModel() {
		return model;
	}

	/**
	 * Recreates the Panel based on the new tab model
	 * 
	 * @param model
	 *            the model
	 */
	public void setModel(TabModel model) {

		this.model = model;

		this.getChildren().clear();
		this.setRendered(false);
		if (model == null) {
			return;
		}

		// create tab header
		Container tabs = ComponentUtil.getContainer("tabs", "ul", null, null);
		tabs.setAttribute("class", TABS_HEADER_STYLE);
		addChild(tabs);

		int selectedTab = model.getSelectedTab();
		for (int i = 0; i < model.size(); i++) {

			// add tab items
			// String label = model.getTabLabelAt(this, i, i == selectedTab);
			Container tab = tabRenderer.getComponentAt(this, model, i);

			tabs.addChild(tab);
			tab.setAttribute("t", i + "");
			tab.setAttribute("init", "false");
			tab.addEvent(EVT_SHOW_TAB, Event.CLICK);

			// content.setAttribute(name, value)

			// add content container
			Container content = ComponentUtil.getContainer("c-" + i, "div", "",
					null);
			content.setAttribute("init", "false");
			// content.setAttribute("class", TABS_SELECTED_TAB_CONTENT_STYLE);
			content.setDisplay(false);
			addChild(content);
			if (tabContentDecorator != null)
				tabContentDecorator.decorateContent(content);

			// initialise default selected tab
			if (i == selectedTab) {
				tabRenderer.onSelect(this, model, i, tab);
				// content.setAttribute("class",
				// TABS_SELECTED_TAB_CONTENT_STYLE);
				content.setDisplay(true);
				content.addChild(model.getTabContentAt(this, i));
				content.setAttribute("init", "true");
				tab.setAttribute("init", "true");
			}

		}

	}

	/**
	 * 
	 * @return the {@link TabContentDecorator}
	 */
	public TabContentDecorator getTabContentDecorator() {
		return tabContentDecorator;
	}

	/**
	 * sets the {@link TabContentDecorator}
	 * 
	 * @param tabContentDecorator
	 *            The tab content decorator
	 */
	public void setTabContentDecorator(TabContentDecorator tabContentDecorator) {
		this.tabContentDecorator = tabContentDecorator;
		setModel(model);
	}

	/**
	 * @return the {@link TabRenderer}
	 */
	public TabRenderer getTabRenderer() {
		return tabRenderer;
	}

	/**
	 * sets the {@link TabRenderer}
	 * 
	 * @param tabRenderer
	 *            The {@link TabRenderer}
	 */
	public void setTabRenderer(TabRenderer tabRenderer) {
		this.tabRenderer = tabRenderer;
		setModel(model);
	}

	/**
	 * JQuery UI style {@link TabRenderer}
	 * 
	 * @author arossaye
	 * 
	 */
	public static class JQTabRenderer implements TabRenderer {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Container getComponentAt(TabPanel pane, TabModel model, int index) {
			Container tab = ComponentUtil.getContainer("tt", "a", "", null);
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

		public void onDeselect(TabPanel pane, TabModel model, int index,
				Container tab) {
			tab.setAttribute("class", TABS_INACTIVE_TAB_STYLE);
		}

	}

	/**
	 * Jquery UI style {@link TabContentDecorator}
	 * 
	 * @author arossaye
	 * 
	 */
	public static class JQTabContentDecorator implements TabContentDecorator {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;


		public void decorateContent(Container contentContainer) {
			contentContainer.setAttribute("class",
					TABS_SELECTED_TAB_CONTENT_STYLE);

		}

	}
}
