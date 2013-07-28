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
 package org.castafiore.designer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designer.help.EXDesignerHelp;
import org.castafiore.designer.toolbar.menu.MenuListener;
import org.castafiore.designer.toolbar.menu.MenuTreeNode;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.menu.EXMenu;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.EventUtil;
import org.castafiore.wfs.Util;



public class EXDesignableFactoryToolBar extends EXContainer {
	
	private Map<String, List<DesignableFactory>> factoryMap = new HashMap<String, List<DesignableFactory>>();
	
	public EXDesignableFactoryToolBar() {
		super("EXVerticalBar", "div");
		Map<String, DesignableFactory> designables = BaseSpringUtil.getApplicationContext().getBeansOfType(DesignableFactory.class);
		Iterator<String> iter = designables.keySet().iterator();
		
		while(iter.hasNext()){
			DesignableFactory des = designables.get(iter.next());
			
			String category = des.getCategory();
			
			if(factoryMap.containsKey(category)){
				factoryMap.get(category).add(des);
			}else{
				factoryMap.put(category, new ArrayList<DesignableFactory>());
				factoryMap.get(category).add(des);
			}
		}
		//Object[] categories = factoryMap.keySet().toArray();
		Set<String> sCategories = factoryMap.keySet();
		
		List<String> lstCategories = new ArrayList<String>(sCategories);
		Collections.sort(lstCategories, SpringUtil.getBeanOfType(DesignableCategoryComparator.class));
		generateCategoryBar(lstCategories.toArray());
	}
	
	public EXMenu getMenu(){
		EXMenu menu = new EXMenu("menu", new MenuTreeNode());
		menu.setMenuStyle(EXMenu.STYLE_IPOD);
		menu.addMenuListener(new MenuListener());
		menu.addClass("designerStart");
		return menu;
	}
	public void generateCategoryBar(Object[] categories){
		EXToolBar toolbar = new EXToolBar("digner") ;
		toolbar.addItem(getMenu());
		
		for(Object o : categories){
			EXIconButton btn = new EXIconButton("", o.toString());
			btn.setAttribute("category", o.toString());
			btn.setAttribute("method", "showDesignableList");
			btn.setAttribute("ancestor", getClass().getName());
			btn.addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
			btn.setStyle("margin-top", "13px");
			toolbar.addItem(btn);
		}
		EXIconButton closeButton = (EXIconButton)new EXIconButton("close", Icons.ICON_CLOSE);
		closeButton.addEvent(CLOSE_EVENT, Event.CLICK);
		toolbar.addItem(closeButton);
		closeButton.setStyle("float", "right");
		
		
		EXIconButton help = new EXIconButton("help", Icons.ICON_HELP);
		toolbar.addItem(help);
		help.setStyle("float", "right");
		help.addEvent(SHOW_HELP, Event.CLICK);
		
		EXIconButton preview = new EXIconButton("preview", Icons.ICON_ARROWTHICK_1_NE);
		preview.setAttribute("title", "Execute Portal in new Window");
		toolbar.addItem(preview);
		preview.setStyle("float", "right");
		preview.setAttribute("href", "portal.jsp?username=" + Util.getRemoteUser());
		preview.setAttribute("target", "_blank");
		//help.addEvent(SHOW_HELP, Event.CLICK);
		
		addChild(toolbar);
		EXToolBar designablesToolbar = new EXToolBar("desToolbar");
		String firstCategory = categories[0].toString();
		List<DesignableFactory> factories = this.factoryMap.get(firstCategory);
		for(DesignableFactory factory : factories){
			designablesToolbar.addItem(factory);
		}
		designablesToolbar.setAttribute("currentcategory", firstCategory);
		addChild(designablesToolbar);
	}
	
	public void showDesignableList(Container caller){
		String category =caller.getAttribute("category");
		EXToolBar tb = (EXToolBar)caller.getAncestorOfType(EXDesignableFactoryToolBar.class).getDescendentByName("desToolbar");
		if(true){
			tb.setAttribute("currentcategory", category);
			List<DesignableFactory> factories = this.factoryMap.get(category);
			tb.getChildren().clear();
			tb.setRendered(false);
			for(DesignableFactory des : factories){
				tb.addItem(des);
			}
			tb.setRendered(false);
		}
	}
	
	
	public DesignableFactory getDesignable(String uniqueIdentifier){
		List<Container> containers =  new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, containers,DesignableFactory.class );
		for(Container c : containers){
			
			DesignableFactory d = (DesignableFactory)c;
			if(d.getUniqueId().equals(uniqueIdentifier)  ){
				return (DesignableFactory)c;
			}
		}
		
		return null;
	}
	
	
	
	
	public final static Event SHOW_HELP = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXDesigner.class));
			container.makeServerRequest(this);
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			
			EXDesignerHelp help = container.getAncestorOfType(EXDesigner.class).getDescendentOfType(EXDesignerHelp.class);
			if(help == null){
				help = new EXDesignerHelp();
				container.getAncestorOfType(EXDesigner.class).addPopup(help);
				
			}else{
				help.setDisplay(true);
			}
			help.setStyle("left", "14%").setStyle("top", "20px");
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			//container.getAncestorOfType(EXDesigner.class).getDescendentOfType(EXDesignerHelp.class).appendTo("body");
			
		}
		
	};
	
	public final static Event CLOSE_EVENT = new Event(){

	
		public void ClientAction(ClientProxy container) {

			container.getAncestorOfType(EXDesigner.class).fadeOut(100, new ClientProxy(".dock").fadeIn(100, container.clone().makeServerRequest(this)));//.animate(new JMap().put("width", "0px").put("height", "0px").put("top", "0px").put("left", "0px") , "100", null);
			
		}

		
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXDesigner.class).remove();
			Application app = container.getRoot();
			try{
				app.getDescendentByName("EXWebOSMenu").setDisplay(true);
			}catch(Exception e){
				
			}
			return true;
		}

		
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
}
