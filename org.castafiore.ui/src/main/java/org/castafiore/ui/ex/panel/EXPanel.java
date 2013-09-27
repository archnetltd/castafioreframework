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
package org.castafiore.ui.ex.panel;

import java.util.List;

import org.castafiore.JQContants;
import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.panel.Panel;
import org.castafiore.utils.ComponentUtil;

public class EXPanel extends EXContainer implements JQContants, Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXPanel(String name, String title) {
		super(name, "div");
		setStyle("visibility", "hidden");
		addClass("ui-dialog").addClass("ui-widget").addClass("ui-widget-content").addClass("ui-corner-all").addClass("ui-front");
		setAttribute("tabindex", "-1").setAttribute("role", "dialog").setAttribute("aria-describedby", "dialog");

		
		Container titleBar = new EXContainer("titleBar", "div").addClass("ui-dialog-titlebar").addClass("ui-widget-header").addClass("ui-corner-all").addClass("ui-helper").addClass("ui-helper-clearfix");
		addChild(titleBar);
		

		Container uiTitle = new EXContainer("title", "span").addClass("ui-dialog-title").setText(title);
		titleBar.addChild(uiTitle);
		setAttribute("aria-labelledby", uiTitle.getId());
		Container closeButton =new EXContainer("button", "closeButton").addClass("ui-button").addClass("ui-widget").addClass("ui-state-default").addClass("ui-corner-all").addClass("ui-button-icon-only").addClass("ui-dialog-titlebar-close");
		closeButton.setAttribute("role", "button").setAttribute("aria-disabled", "false").setAttribute("title", "close").setStyle("width", "20px").setStyle("height", "19px");
		titleBar.addChild(closeButton);
		closeButton.addEvent(CLOSE_EVENT, Event.CLICK);
		Container uiCloseIcon =  new EXContainer("closeIcon", "span").addClass("ui-button-icon-primary").addClass("ui-icon").addClass("ui-icon-closethick").setStyle("margin", "1px").setStyle("top", "0").setStyle("left", "0");
		closeButton.addChild(uiCloseIcon);
	//	closeButton.addChild(new EXContainer("", "span").addClass("ui-button-text").setText("Close"));

		
		Container content = new EXContainer("content", "div").addClass("ui-dialog-content").addClass("ui-widget-content");
				
		content.setStyle("height", "auto").setStyle("min-height", "105px").setStyle("max-height", "none").setStyle("width", "auto");

		addChild(content);

		Container footer = ComponentUtil.getContainer("panelFooter", "div",
				null, DIALOG_FOOTER_STYLE);
		
		footer.addChild(new EXContainer("footer", "div").addClass("ui-dialog-buttonset"));
		
		addChild(footer);
		footer.setDisplay(false);
		setDraggable(true);
		setResizable(true);
	}

	public EXPanel setPacked(boolean b) {
		if (b) {
			getChild("content").setStyle("padding", "0");
		} else {
			getChild("content").setStyle("padding", "0.5em 1em");
		}

		return this;
	}

	public void setCloseButtonEvent(Event event) {
		getDescendentByName("closeButton").getEvents().get(Event.CLICK).clear();
		getDescendentByName("closeButton").addEvent(event, Event.CLICK);
	}

	public EXPanel(String name) {
		this(name, null);
	}

	public Container getBody() {
		List<Container> childBody = getBodyContainer().getChildren();
		if (childBody.size() > 0) {
			return childBody.get(0);
		} else {
			return null;
		}
	}

	public Panel setBody(Container container) {
		getBodyContainer().getChildren().clear();
		getBodyContainer().setRendered(false);
		getBodyContainer().addChild(container);
		return this;
	}

	public Panel setShowCloseButton(boolean b) {
		getDescendentByName("closeButton").setDisplay(b);
		return this;
	}

	public Panel setShowHeader(boolean showHeader) {
		getDescendentByName("titleBar").setDisplay(showHeader);
		return this;

	}

	public Panel setTitle(String title) {
		try {
			getDescendentByName("titleBar").getDescendentByName("title")
					.setText(title);
			return this;
		} catch (Exception e) {
			getDescendentByName("titleBar").setText(title);
		}

		return this;

	}

	public void addPopup(Container popup) {
		addChild(popup);
	}

	public Panel setShowFooter(boolean display) {
		getChild("panelFooter").setDisplay(display);
		return this;
	}

	protected Container getFooterContainer() {
		return getChild("panelFooter").getChild("footer");
	}

	protected Container getBodyContainer() {
		return getChild("content");
	}

	public Container setDraggable(boolean draggable) {
		if (draggable) {
			JMap options = new JMap().put("opacity", 0.35).put("handle",
					"#" + getDescendentByName("titleBar").getId());
			options.put("containment", "document");
			setDraggable(true, options);
			setStyle("position", "absolute");
			setStyle("top", "10%");
			setStyle("left", "10%");
		} else {
			super.setDraggable(false);
			setStyle("position", "static");
		}
		return this;
	}

	public void onReady(ClientProxy proxy) {

		super.onReady(proxy);

		proxy.setStyle("visibility", "visible");

	}
}
