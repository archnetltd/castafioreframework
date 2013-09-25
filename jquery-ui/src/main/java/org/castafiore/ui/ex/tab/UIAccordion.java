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

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.tab.TabModel;
import org.castafiore.ui.tab.TabPanel;
import org.castafiore.ui.tab.TabRenderer;

/**
 * Simple jquery-ui like accordion. It is actually a {@link TabPanel}
 * @author arossaye
 *
 */
public class UIAccordion extends EXContainer implements TabPanel {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TabModel model;
	
	private JMap options = new JMap();
	
	private JMap icons = null;

	/**
	 * Creates an accordion with the specified name
	 * @param name
	 */
	public UIAccordion(String name) {
		super(name, "div");
		
	}
	
	public UIAccordion setCollapsible(Boolean c){
		options.put("collapsible", c);
		return this;
	}
	
	public UIAccordion setDisabled(Boolean d){
		options.put("disabled", d);
		return this;
	}
	
	public UIAccordion setHeightStyle(HeightStyle heighStyle){
		options.put("heightStyle", heighStyle.getValue());
		return this;
	}
	
	public UIAccordion setHeaderIcon(String iconCls){
		if(icons == null){
			icons = new JMap();
		}
		
		icons.put("header", iconCls);
		return this;
	}
	
	public UIAccordion setActiveHeaderIcon(String iconCls){
		if(icons == null){
			icons = new JMap();
		}
		
		icons.put("activeHeader", iconCls);
		return this;
	}
	
	public UIAccordion setActivateEvent(Event e){
		addEvent(e, Event.MISC);
		ClientProxy proxy = new ClientProxy(this);
		e.ClientAction(proxy);
		options.put("activate", proxy, "event", "ui");
		return this;
	}
	
	public UIAccordion setBeforeActivateEvent(Event e){
		addEvent(e, Event.MISC);
		ClientProxy proxy = new ClientProxy(this);
		e.ClientAction(proxy);
		options.put("beforeActivate", proxy, "event", "ui");
		return this;
	}

	/**
	 * Returns the model of the accordion
	 */
	public TabModel getModel() {
		return model;
	}

	/**
	 * Sets the {@link TabModel} for this accordion
	 * @param model The {@link TabModel} to apply
	 */
	public void setModel(TabModel model) {
		this.model = model;
		refresh();
	}
	
	/**
	 * Recreates the accordion completely
	 */
	public void refresh(){
		
		this.getChildren().clear();
		this.setRendered(false);
		
		if(model != null){
			int size = model.size();
			int selected = model.getSelectedTab();
			options.put("collapsible", true).put("active", selected);
			for(int i = 0; i < size;i++){
				boolean bselected = i==selected;
				Container h = new EXContainer("", "h3").setText(model.getTabLabelAt(this, i, bselected));
				addChild(h);
				Container wrapper = new EXContainer("", "div");
				wrapper.addChild(model.getTabContentAt(this, i));
				addChild(wrapper);
			}
		}
		
	}
	
	
	public void onReady(ClientProxy container){
		if(icons != null){
			options.put("icons", icons);
		}
		
		container.addMethod("accordion", options);
	}

	public TabRenderer getTabRenderer() {
		return null;
	}
	
	public enum HeightStyle {
		AUTO ("auto"), 
		FILL("fill"), 
		CONTENT("content");
		
		private final String value;
		
		HeightStyle (String value){
			this.value=value;
		}
		public String getValue(){
			return value;
		}
	}

	

}
