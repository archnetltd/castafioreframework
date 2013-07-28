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
 package org.castafiore.designer.layout;

import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.layout.EXXYLayoutContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.utils.ComponentUtil;

public class EXDroppableXYLayoutContainer extends EXXYLayoutContainer implements DesignableLayoutContainer {

	public EXDroppableXYLayoutContainer(String name, String tageName){
		super(name,tageName);
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
		//setStyle("position", "relative");
	} 
	
	public EXDroppableXYLayoutContainer(){
		super();
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
		//setStyle("position", "relative");
	}

	public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

	public String getPossibleLayoutData(Container container) {
		return container.getName();
	}

	
	
	

	

	public void onAddComponent(Container component) {

		
		if(component.getStyle("position").equals(""))
			component.setStyle("position", "absolute");
		if(component.getStyle("top").equals(""))
			component.setStyle("top", "0px");
		if(component.getStyle("left").equals(""))
			component.setStyle("left", "0px");
		//component.setStyle("wid", value)
		
			ClientProxy pComponent= new ClientProxy("#" + component.getId());
			ClientProxy proxy = new ClientProxy(this,new ListOrderedMap());
			
			final JMap editMode = new JMap();
			editMode.put("top", new Var("$('#"+component.getId()+"').parent().css('top')", ""));
			editMode.put("left", new Var("$('#"+component.getId()+"').parent().css('left')", ""));
			editMode.put("sourceid", component.getId());
			
			editMode.put("width", new Var("$('#"+component.getId()+"').parent().width()"));
			editMode.put("height", new Var("$('#"+component.getId()+"').parent().height()"));
			
			editMode.put("padding-top", pComponent.getStyle("padding-top"));
			editMode.put("padding-bottom", pComponent.getStyle("padding-bottom"));
			editMode.put("padding-left", pComponent.getStyle("padding-left"));
			editMode.put("padding-right", pComponent.getStyle("padding-right"));
			
			editMode.put("margin-top", pComponent.getStyle("margin-top"));
			editMode.put("margin-bottom", pComponent.getStyle("margin-bottom"));
			editMode.put("margin-left", pComponent.getStyle("margin-left"));
			editMode.put("margin-right", pComponent.getStyle("margin-right"));
			
			editMode.put("border-top", pComponent.getStyle("border-top"));
			editMode.put("border-bottom", pComponent.getStyle("border-bottom"));
			editMode.put("border-left", pComponent.getStyle("border-left"));
			editMode.put("border-right", pComponent.getStyle("border-right"));
			
			
			proxy.makeServerRequest(editMode,getEvents().get(Event.DND_OVER).get(0));
			editMode.put("stopdrag", proxy);
			editMode.put("containment", "#" + getId());
			ClientProxy resproxy = new ClientProxy(this,new ListOrderedMap());
			resproxy.makeServerRequest(editMode,getEvents().get(Event.DND_OUT).get(0));
			editMode.put("stopresize", resproxy);
			component.addEvent(new Event(){

				
				public void ClientAction(ClientProxy container) {
					
					container.setTimeout(container.clone().addMethod("editMode", editMode), 1000);
				}

				
				public boolean ServerAction(Container container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					return false;
				}

				
				public void Success(ClientProxy container,
						Map<String, String> request) throws UIException {
					// TODO Auto-generated method stub
					
				}
				
			}, Event.READY);
	}

	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveUp(component, this);
	}

	@Override
	public void moveDown(Container component) {
		ComponentUtil.moveDown(component, this);
	}
}
