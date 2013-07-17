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
 package org.castafiore;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.panel.Panel;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;

/**
 * 
 * Some constants of css classes used by jquery-ui
 * @author Kureem Rossaye
 *
 */
public interface JQContants {

	/**
	 * Style class for accordeon
	 */
	public final static String ACC_STYLE = "ui-accordion ui-widget ui-helper-reset";
	
	/**
	 * Used for accordeon
	 */
	public final static String ACC_HEADER_OPEN_STYLE = "ui-accordion-header ui-helper-reset ui-state-default ui-corner-top";
	
	/**
	 * Used for accordeon
	 */
	public final static String ACC_HEADER_CLOSE_STYLE = "ui-accordion-header ui-helper-reset ui-state-default ui-corner-all";
	
	/**
	 * Used for accordeon
	 */
	public final static String ACC_ARROW_OPEN_STYLE = "ui-icon ui-icon-triangle-1-s";
	
	/**
	 * Used for accordeon
	 */
	public final static String ACC_ARROW_CLOSE_STYLE = "ui-icon ui-icon-triangle-1-e";
	
	/**
	 * Used for accordeon
	 */
	public final static String ACC_CONTENT_STYLE = "ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active";
	
	/**
	 * Used for Tab panel
	 */
	public final static String TABS_STYLE = "ui-tabs ui-widget-content ui-corner-all";

	/**
	 * Used for tab panel
	 */
	public final static String TABS_HEADER_STYLE = "ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all";

	/**
	 * Used for tab panel
	 */
	public final static String TABS_ACTIVE_TAB_STYLE = "ui-state-default ui-corner-top ui-tabs-selected ui-state-active";

	/**
	 * Used for tab panel
	 */
	public final static String TABS_INACTIVE_TAB_STYLE = "ui-state-default ui-corner-top";

	/**
	 * Used for tab panel
	 */
	public final static String TABS_SELECTED_TAB_CONTENT_STYLE = "ui-tabs-panel ui-widget-content ui-corner-bottom";


	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_STYLE = "ui-dialog ui-widget ui-widget-content ui-corner-all";

	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_TITLE_BAR_STYLE = "ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix";

	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_TITLE_STYLE = "ui-dialog-title";

	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_CLOSE_BUTTON_STYLE = "ui-dialog-titlebar-close ui-state-default ui-corner-all";

	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_CLOSE_ICON_STYLE = "ui-icon ui-icon-closethick";
	

	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_CONTENT_STYLE = "ui-dialog-content ui-widget-content";

	/**
	 * Used for dialog panel
	 */
	public final static String DIALOG_FOOTER_STYLE = "ui-dialog-buttonpane ui-widget-content ui-helper-clearfix";

	/**
	 * Used for buttons
	 */
	public final static String BUTTON_STYLE = "ui-state-default ui-corner-all";
	
	/**
	 * Used for icons
	 */
	public final static String ICON_STYLE = "ui-icon";

	/**
	 * Background positions for icons
	 */
	public final static String[] ICONS_BACK_POSITON = new String[] { "-16px 0",
			"-32px 0", "-48px 0", "-64px 0", "-80px 0", "-96px 0", "-112px 0",
			"-128px 0", "-144px 0", "0 -16px", "-16px -16px", "-32px -16px",
			"-48px -16px", "-64px -16px", "-80px -16px", "-96px -16px",
			"-112px -16px", "-128px -16px", "-144px -16px", "0 -32px",
			"-16px -32px", "-32px -32px", "-48px -32px", "-64px -32px",
			"-80px -32px", "-96px -32px", "-112px -32px", "-128px -32px",
			"-144px -32px", "-160px -32px", "-176px -32px", "-192px -32px",
			"-208px -32px", "-224px -32px", "-240px -32px", "0 -48px",
			"-16px -48px", "-32px -48px", "-48px -48px", "-64px -48px",
			"-80px -48px", "-96px -48px", "-112px -48px", "-128px -48px",
			"-144px -48px", "-160px -48px", "-176px -48px", "-192px -48px",
			"-208px -48px", "-224px -48px", "-240px -48px", "0 -64px",
			"-16px -64px", "-32px -64px", "-48px -64px", "-64px -64px",
			"-80px -64px", "-96px -64px", "-112px -64px", "-128px -64px",
			"-144px -64px", "-160px -64px", "-176px -64px", "0 -80px",
			"-16px -80px", "-32px -80px", "-48px -80px", "-64px -80px",
			"-80px -80px", "-96px -80px", "-112px -80px", "0 -96px",
			"-16px -96px", "-32px -96px", "-48px -96px", "-64px -96px",
			"-80px -96px", "-96px -96px", "-112px -96px", "-128px -96px",
			"-144px -96px", "-160px -96px", "-176px -96px", "-192px -96px",
			"-208px -96px", "-224px -96px", "-240px -96px", "0 -112px",
			"-16px -112px", "-32px -112px", "-48px -112px", "-64px -112px",
			"-80px -112px", "-96px -112px", "-112px -112px", "-128px -112px",
			"-144px -112px", "-160px -112px", "-176px -112px", "-192px -112px",
			"-208px -112px", "-224px -112px", "-240px -112px", "0 -128px",
			"-16px -128px", "-32px -128px", "-48px -128px", "-64px -128px",
			"-80px -128px", "-96px -128px", "-112px -128px", "-128px -128px",
			"-144px -128px", "-160px -128px", "-176px -128px", "-192px -128px",
			"-208px -128px", "-224px -128px", "-240px -128px", "0 -144px",
			"-16px -144px", "-32px -144px", "-48px -144px", "-64px -144px",
			"-80px -144px", "-96px -144px", "-112px -144px", "-128px -144px",
			"-144px -144px", "0 -160px", "-16px -160px", "-32px -160px",
			"-48px -160px", "-64px -160px", "-80px -160px", "-96px -160px",
			"-112px -160px", "-128px -160px", "-144px -160px", "0 -176px",
			"-16px -176px", "-32px -176px", "-48px -176px", "-64px -176px",
			"-80px -176px", "-96px -176px", "0 -192px", "-16px -192px",
			"-32px -192px", "-48px -192px", "-64px -192px", "-80px -192px",
			"-96px -192px", "-112px -192px", "-128px -192px", "-144px -192px",
			"-160px -192px", "-176px -192px", "-192px -192px", "-208px -192px",
			"0 -208px", "-16px -208px", "-32px -208px", "-48px -208px",
			"-64px -208px", "-80px -208px", "0 -224px", "-16px -224px",
			"-32px -224px", "-48px -224px", "-64px -224px", "-80px -224px"

	};

	
	/**
	 * Static event to open a tab
	 */
	public final static Event EVT_SHOW_TAB = new Event() {

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			String isInitialised = container.getAttribute("init");
			String index = container.getAttribute("t");
			TabPanel panel = container.getAncestorOfType(TabPanel.class);
			Container panelTabs = panel.getChildByIndex(0);
			TabModel model = panel.getModel();
			for (Container tab : panelTabs.getChildren()) {
				String oTab = tab.getAttribute("t");
				Container content = panel.getChild("c-" + oTab);
				if (index.equals(oTab)) {
					
					if ("false".equalsIgnoreCase(isInitialised)) {
						Container rContent = panel.getModel().getTabContentAt(
								panel, Integer.parseInt(index));
						content.addChild(rContent);
					}
					tab.setAttribute("init", "true");
					content.setDisplay(true);
					panel.getTabRenderer().onSelect(panel, model, Integer.parseInt(oTab), tab);
				} else {
					tab.setAttribute("class", TABS_INACTIVE_TAB_STYLE);
					content.setDisplay(false);
					panel.getTabRenderer().onDeselect(panel, model, Integer.parseInt(oTab), tab);
				}
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
		}

	};

	/**
	 * Close event to close any {@link Panel}
	 */
	public final static Event CLOSE_EVENT = new Event() {

		public void ClientAction(ClientProxy container) {
			container.getAncestorOfType(Panel.class).fadeOut(100,
					container.clone().makeServerRequest(this));

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(org.castafiore.ui.ex.panel.Panel.class)
					.remove();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub

		}

	};
	
	/**
	 * Static event to hide any {@link Panel}
	 */
	public final static Event HIDE_EVENT = new Event() {

		public void ClientAction(ClientProxy container) {
			container.getAncestorOfType(Panel.class).fadeOut(100,
					container.clone().makeServerRequest(this));

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			container.getAncestorOfType(Panel.class).setDisplay(false);
			return false;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {

		}

	};
}
