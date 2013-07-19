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
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;

public class EXPanel extends EXContainer implements JQContants, Panel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXPanel(String name, String title) {
		super(name, "div");
		setStyle("visibility", "hidden");
		setAttribute("class", DIALOG_STYLE);
		setStyle("z-index", "1000");
		
		Container titleBar = ComponentUtil.getContainer("titleBar", "div",null, DIALOG_TITLE_BAR_STYLE);
		addChild(titleBar);
		titleBar.setStyle("-moz-user-select", "none");
		 
		Container uiTitle = ComponentUtil.getContainer("title", "span", title, DIALOG_TITLE_STYLE);
		titleBar.addChild(uiTitle);
		uiTitle.setStyle("-moz-user-select", "none");
		
		Container closeButton = ComponentUtil.getContainer("closeButton", "a", null,DIALOG_CLOSE_BUTTON_STYLE);
		closeButton.setAttribute("href", "#").setStyle("border", "none");
		titleBar.addChild(closeButton);
		closeButton.setStyle("-moz-user-select", "none");
		closeButton.addEvent(CLOSE_EVENT, Event.CLICK);
		
		Container uiCloseIcon = ComponentUtil.getContainer("closeIcon", "span", null, DIALOG_CLOSE_ICON_STYLE);
		closeButton.addChild(uiCloseIcon);
		uiCloseIcon.setStyle("-moz-user-select", "none");
		
		
		Container content = ComponentUtil.getContainer("content", "div", null, DIALOG_CONTENT_STYLE);
		content.setStyle("height", "auto").setStyle("min-height", "61px").setStyle("width", "auto");
		
		addChild(content);
			
		Container footer = ComponentUtil.getContainer("panelFooter", "div", null, DIALOG_FOOTER_STYLE);
		addChild(footer);
		footer.setDisplay(false);
		setDraggable(true);
	}
	
	
	public void setCloseButtonEvent(Event event){
		getDescendentByName("closeButton").getEvents().get(Event.CLICK).clear();
		getDescendentByName("closeButton").addEvent(event, Event.CLICK);
	}
	
	public EXPanel(String name){
		this(name, null);
	}

	public Container getBody() {
		List<Container> childBody = getBodyContainer().getChildren();
		if(childBody.size() > 0){
			return childBody.get(0);
		}else{
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
		try{
			getDescendentByName("titleBar").getDescendentByName("title").setText(title);
			return this;
		}catch(Exception e){
			getDescendentByName("titleBar").setText(title);
		}
		
		return this;
		
	}

	public void addPopup(Container popup){
		addChild(popup);
	}
	
	public Panel setShowFooter(boolean display){
		getChild("panelFooter").setDisplay(display);
		return this;
	}
	
	protected Container getFooterContainer(){
		return getChild("panelFooter");
	}
	protected Container getBodyContainer(){
		return getChild("content");
	}
	
	public Container setDraggable(boolean draggable)
	{
		if(draggable)
		{
			JMap options = new JMap().put("opacity", 0.35).put("handle", "#" +getDescendentByName("titleBar").getId());
			options.put("containment", "document");
			setDraggable(true, options);
			setStyle("position", "absolute");
			setStyle("top", "10%");
			setStyle("left", "10%");
		}
		else
		{
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
