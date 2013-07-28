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
 package org.castafiore.designer.wizard.layout;

import java.util.ArrayList;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.layout.EXDroppableXHTMLLayoutContainer;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.designer.marshalling.DesignableUtil;
import org.castafiore.designer.portal.EXDesignablePageContainer;
import org.castafiore.designer.portal.EXDesignablePortalContainer;
import org.castafiore.designer.portal.EXPageContainerDesignableFactory;
import org.castafiore.designer.wizard.layout.EXDimensionColorInput.DimensionColor;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.list.DataModel;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXLayoutGenerator extends EXXHTMLFragment{
	
	private EXSelect pageAlignment;
	
	private EXColorPicker mainContentBackground;
	
	private EXDimensionColorInput leftColumn;
	
	private EXCheckBox includeLeftColumn;
	
	private EXDimensionColorInput rightColumn;
	
	private EXCheckBox includeRightColumn;
	
	private EXCheckBox includeHeader;
	
	private EXDimensionColorInput header;
	
	private EXCheckBox includeNavigation;
	
	private EXDimensionColorInput navigation;
	
	private EXCheckBox includeFooter;
	
	private EXDimensionColorInput footer;
	
	public EXLayoutGenerator() {
		super("EXLayoutGenerator", ResourceUtil.getDownloadURL("classpath", "org/castafiore/designer/wizard/layout/EXLayoutGenerator.xhtml"));
		addClass("ui-widget");
		init();
	}
	
	public void init(){
		
		//init page alignment
		
		DataModel paModel = new DefaultDataModel(new ArrayList<Object>())
		.addItem(new SimpleKeyValuePair("0", "Full page"));
		//.addItem(new SimpleKeyValuePair("1", "Centered page"))
		//.addItem(new SimpleKeyValuePair("2", "Left alignment"));
		pageAlignment = new EXSelect("pageAlignment",paModel);
		pageAlignment.addEvent(CHANGE_ALIGNMENT_EVENT, Event.CHANGE);
		addChild(pageAlignment);
		
		
		//init main content
		mainContentBackground = new EXColorPicker("mainContentBackground");
		addChild(mainContentBackground);
		mainContentBackground.setAttribute("size", "8");
		
		
		//init left column
		includeLeftColumn = new EXCheckBox("includeLeftColumn");
		includeLeftColumn.addEvent(SHOW_HIDE_DS_EVENT, Event.CHANGE);
		addChild(includeLeftColumn);
		leftColumn = new EXDimensionColorInput("leftColumn", new EXDimensionColorInput.DimensionColor("15", "#BBBBBB", "%"), "%", "Width :");
		addChild(leftColumn);
		
		
		
		//init right column
		includeRightColumn = new EXCheckBox("includeRightColumn");
		includeRightColumn.addEvent(SHOW_HIDE_DS_EVENT, Event.CHANGE);
		addChild(includeRightColumn);
		rightColumn = new EXDimensionColorInput("rightColumn", new EXDimensionColorInput.DimensionColor("15", "#FFBBAA", "%"), "%", "Width :");
		addChild(rightColumn);
		
		
		//init header
		includeHeader = new EXCheckBox("includeHeader");
		includeHeader.addEvent(SHOW_HIDE_DS_EVENT, Event.CHANGE);
		addChild(includeHeader);
		header = new EXDimensionColorInput("header", new EXDimensionColorInput.DimensionColor("100", "#FFBBAA", "px"), "px");
		addChild(header);

		
		
		//init navigation
		includeNavigation = new EXCheckBox("includeNavigation");
		includeNavigation.addEvent(SHOW_HIDE_DS_EVENT, Event.CHANGE);
		addChild(includeNavigation);
		navigation = new EXDimensionColorInput("navigation", new EXDimensionColorInput.DimensionColor("25", "#FFBBAA", "px"), "px");
		addChild(navigation);
		
		
		//init footer
		includeFooter = new EXCheckBox("includeFooter");
		includeFooter.addEvent(SHOW_HIDE_DS_EVENT, Event.CHANGE);
		addChild(includeFooter);
		footer = new EXDimensionColorInput("footer", new EXDimensionColorInput.DimensionColor("25", "#FFBBAA", "px"), "px");
		addChild(footer);
		
		
	}
	
	
	public PortalContainer getGeneratedLayout(){
		 SimpleKeyValuePair kv = (SimpleKeyValuePair)pageAlignment.getValue();
		 
		 boolean hasLeft = this.includeLeftColumn.isChecked();
		 boolean hasRight = this.includeRightColumn.isChecked();
		 boolean hasHeader = this.includeHeader.isChecked();
		 boolean hasNavigation = this.includeNavigation.isChecked();
		 boolean hasFooter = this.includeFooter.isChecked();
		 
		 //PortalContainer wrapper = new EXDesignablePortalContainer("wrapper", "div");
		 PortalContainer wrapper =(PortalContainer)DesignableUtil.getInstance("portal:portalcontainer");
		 wrapper.setName("wrapper");
		 wrapper.setAttribute("Text", "");
		 wrapper.addClass("casta-portal-wrapper");
		 wrapper.setWidth(Dimension.parse("100%"));
		// wrapper.setAttribute("des-id", "portal:portalcontainer");
		 if(hasHeader){
			 //EXDroppableXYLayoutContainer header = new EXDroppableXYLayoutContainer("header", "div");
			 EXDroppableXYLayoutContainer header = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("core:xylayout"); //new EXDroppableXYLayoutContainer("header", "div");
			 header.setAttribute("Text", "");
			 header.addClass("casta-portal-header");
			 wrapper.addChild(header);
			 
			 DimensionColor dc =  (DimensionColor)this.header.getValue();
			 header.setHeight(dc.getDimensionObject());
			 header.setStyle("background-color", dc.getColor());
			 header.setWidth(Dimension.parse("100%"));
			 //header.setAttribute("des-id", "core:xylayout");
		 }
		 
		 if(hasNavigation){
			 //EXDroppableXYLayoutContainer navigation = new EXDroppableXYLayoutContainer("navigation", "div");
			 EXDroppableXYLayoutContainer navigation = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("core:xylayout");
			 navigation.setName("navigation");
			 navigation.addClass("casta-portal-navigation");
			 wrapper.addChild(navigation);
			 
			 DimensionColor dc =  (DimensionColor)this.navigation.getValue();
			 navigation.setHeight(dc.getDimensionObject());
			 navigation.setStyle("background-color", dc.getColor());
			 navigation.setWidth(Dimension.parse("100%"));
			// navigation.setAttribute("des-id", "core:xylayout");
		 }
		 
		 if(hasLeft){
			// EXDroppableXYLayoutContainer left = new EXDroppableXYLayoutContainer("left", "div");
			 EXDroppableXYLayoutContainer left = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("core:xylayout");
			 left.setName("left");
			 left.setAttribute("Text", "");
			 left.addClass("casta-portal-left");
			 wrapper.addChild(left);
			 DimensionColor dc =  (DimensionColor)this.leftColumn.getValue();
			 left.setWidth(dc.getDimensionObject());
			 left.setStyle("background-color", dc.getColor());
			 //left.setAttribute("des-id", "core:xylayout");
		 }
		 
		 //EXDroppableXYLayoutContainer content = new EXDesignablePageContainer("content", "div");
		 EXDroppableXYLayoutContainer content = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("portal:pagecontainer");
		 content.setName("content");
		 content.addClass("casta-portal-content");
		 content.setStyle("background-color", this.mainContentBackground.getValue().toString());
		// content.setAttribute("des-id", "portal:pagecontainer");
		 wrapper.addChild(content);
		 content.setAttribute("Text", "");
		 
		 if(hasRight){
			 //EXDroppableXYLayoutContainer right = new EXDroppableXYLayoutContainer("right", "div");
			 EXDroppableXYLayoutContainer right = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("core:xylayout");
			 right.setName("right");
			 right.addClass("casta-portal-right");
			 wrapper.addChild(right);
			 DimensionColor dc =  (DimensionColor)this.rightColumn.getValue();
			 right.setWidth(dc.getDimensionObject());
			 right.setStyle("background-color", dc.getColor());
			 //right.setAttribute("des-id", "core:xylayout");
			 right.setAttribute("Text", "");
		 }
		 
		 int rightColWidth = Integer.parseInt(((DimensionColor)this.rightColumn.getValue()).getDimension());
		 int leftColWidth = Integer.parseInt(((DimensionColor)this.leftColumn.getValue()).getDimension());
		 int contentWidth = 100 - rightColWidth - leftColWidth;
		 content.setWidth(new Dimension("%",contentWidth));
		 
		 
		 if(hasFooter){
			 //EXDroppableXYLayoutContainer footer = new EXDroppableXYLayoutContainer("footer", "div");
			 EXDroppableXYLayoutContainer footer = (EXDroppableXYLayoutContainer)DesignableUtil.getInstance("core:xylayout");
			 footer.setName("footer");
			 footer.addClass("casta-portal-footer");
			 wrapper.addChild(footer);
			 DimensionColor dc =  (DimensionColor)this.footer.getValue();
			 footer.setHeight(dc.getDimensionObject());
			 footer.setStyle("background-color", dc.getColor());
			 footer.setWidth(Dimension.parse("100%"));
			 //footer.setAttribute("des-id", "core:xylayout");
			 footer.setAttribute("Text", "");
		 }
		 return wrapper;
		 
	}
	

	
	/***********************events for this form**************************************/
	
	private final static Event SHOW_HIDE_DS_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
		
			container.makeServerRequest(this);
			
		}
		public boolean ServerAction(Container container,Map<String, String> request) throws UIException {
			
			String name = container.getName();
			 String tmp = name.replace("include", "");
			 String fl = tmp.substring(0, 1).toLowerCase();
			 tmp = fl + tmp.substring(1);
			 
			
			EXCheckBox cb = (EXCheckBox)container;
			container.getParent().getChild(tmp).setDisplay(cb.isChecked());
			
			return true;
		}
		public void Success(ClientProxy container, Map<String, String> request)	throws UIException {}
		
	};
	
	
	private final static Event CHANGE_ALIGNMENT_EVENT = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			EXSelect select =(EXSelect)container;
			
			SimpleKeyValuePair kv =  (SimpleKeyValuePair)select.getValue();
			String key = kv.getKey();
			
			String unit = "px";
			if(key.equalsIgnoreCase("0")){
				unit = "%";
			}
			container.getParent().getChild("leftColumn").getDescendentByName("unit").setText(unit);
			container.getParent().getChild("rightColumn").getDescendentByName("unit").setText(unit);
			return true;
			
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

}
