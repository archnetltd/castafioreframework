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
 package org.castafiore.designer.config;

import java.util.List;
import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.InvalidDesignableException;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.designer.events.MoveUpEvent;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.Droppable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.DroppableSection;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.BaseSpringUtil;


public class EXConfigVerticalBar extends EXContainer implements Droppable {

	private Container container = null;
	public EXConfigVerticalBar() {
		super("EXConfigVerticalBar","ul");	
		addClass("item");
		addClass("vertical-bar");
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
		addEvent(new MoveUpEvent(), Event.SELECTABLE_STOP);
		
		
	}
	

	public void setComponent(Container container){
		if(container != null){
			setName(container.getAttribute("__oid") + "_item");
			this.container = container;
			refresh();
		}
		
	}
	
	public Container getComponent(){
		return container;
	}
	
	
	public void open(){
		
		EXConfigVerticalBar bar = getParent().getAncestorOfType(EXConfigVerticalBar.class);
		
		if(bar == null){
			return;
		}
		Container icon = bar.getChild("li").getChild("icon");
		Container li = icon.getParent();
		for(Container child : li.getChildren()){
			if(true){
				child.setDisplay(true);
			}
		}
		icon.setAttribute("istate", "open");
		icon.setStyle("background-image", "url('icons-2/fugue/icons/toggle-small.png')");
//		while(true){
//			bar = bar.getParent().getAncestorOfType(EXConfigVerticalBar.class);
//			if(bar == null){
//				break;
//			}else{
//				Container lli =bar.getChild("li") ;
//				Container licon = lli.getChild("icon");
//				licon.setAttribute("istate", "open");
//				licon.setStyle("background-image", "url('icons-2/fugue/icons/toggle-small.png')");
//			}
//		}
		
		bar.open();
		
	}
	
	public void refresh(){
		super.refresh();
		this.getChildren().clear();
		EXContainer li = new EXContainer("li", "li");
//		if(container.getParent() instanceof PageContainer){
//			li.setStyle("border", "double steelBlue");
//		}
		try{
		if(container.getTag().equalsIgnoreCase("link")){
			return;
		}
		String uniqueId = container.getAttribute("des-id");
		DesignableFactory des = BaseSpringUtil.getBeanOfType(DesignableService.class).getDesignable(uniqueId);
		EXConfigItem item = new EXConfigItem(container);
		Container icon = new EXContainer("icon", "span").addClass("ui-icon").setStyle("float", "left");
		icon.setStyle("background-image", "url('icons-2/fugue/icons/toggle-small-expand.png')").setStyle("background-position", "0 0");
		icon.setAttribute("istate", "close");
		icon.addEvent(new Event(){

			@Override
			public void ClientAction(ClientProxy container) {
				container.makeServerRequest(this);
				
			}

			@Override
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				Container parent = container.getParent();
				String istate = container.getAttribute("istate");
				for(Container child : parent.getChildren()){
					if(child instanceof EXConfigVerticalBar){
						if(istate.equalsIgnoreCase("open"))
							child.setDisplay(false);
						else
							child.setDisplay(true);
					}
				}
				
				if(istate.equalsIgnoreCase("open")){
					container.setAttribute("istate", "close");
					//container.addClass("ui-icon-carat-1-s");
					container.setStyle("background-image", "url('icons-2/fugue/icons/toggle-small-expand.png')");
				}else{
					container.setAttribute("istate", "open");
					//container.removeClass("ui-icon-carat-1-s");
					container.setStyle("background-image", "url('icons-2/fugue/icons/toggle-small.png')");
				}
				return true;
			}

			@Override
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
			
		}, Event.CLICK);
		li.addChild(icon);
		li.addChild(new EXContainer("", "div").addClass("ui-icon").setStyle("background-image", "url('"+des.getIcon()+"')").setStyle("margin", "2px").setStyle("float", "right"));
		li.addChild(item);
		addChild(li); 
		li.addClass("ui-state-default");
		li.addClass("ui-corner-all");
	//	final String dId = container.getId();
		li.addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				//ClientProxy p = new ClientProxy("#" + dId).addClass("x-active");
				container.removeClass("ui-state-default").addClass("ui-state-active");
				
			}
			public boolean ServerAction(Container container,Map<String, String> request) throws UIException {return false;}
			public void Success(ClientProxy container,	Map<String, String> request) throws UIException {}
			
		}, Event.MOUSE_OVER);
		
		li.addEvent(new Event(){

			public void ClientAction(ClientProxy container) {
				//ClientProxy p = new ClientProxy("#" + dId).removeClass("x-active");
				container.removeClass("ui-state-active").addClass("ui-state-default");
				
			}
			public boolean ServerAction(Container container,Map<String, String> request) throws UIException {return false;}
			public void Success(ClientProxy container,	Map<String, String> request) throws UIException {}
			
		}, Event.MOUSE_OUT);
		boolean hasMore = false;
		if(container instanceof DesignableLayoutContainer){
			 try{
				 //EXDesigner de = getAncestorOfType(EXDesigner.class);
				// Container root =de.getContainer(EXDesigner.CENTER);
			List<DroppableSection> sections = ((DesignableLayoutContainer)container).getSections();
				for(DroppableSection d : sections){
					String cId = d.getComponentId();
					
					//Application root = getRoot();
					Container c = container.getDescendentById(cId);
					EXConfigVerticalBar vb = new EXConfigVerticalBar();
					vb.setDisplay(false);
					li.addChild(vb);
					vb.setComponent(c);
					hasMore = true;
				}
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
				
		}
			 
		
		if(!hasMore){
			li.getChild("icon").setDisplay(false);
		}else{
			li.getChild("icon").setDisplay(true);
		}
		}
		catch(InvalidDesignableException iv){
			iv.printStackTrace();
		}
	}
	
	
	public EXConfigVerticalBar getNode(Container c){
		return (EXConfigVerticalBar)getDescendentByName(c.getAttribute("__oid") + "_item");
	}

	public String[] getAcceptClasses() {
		return new String[]{"components", "toolbarcomponent"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

	

}
