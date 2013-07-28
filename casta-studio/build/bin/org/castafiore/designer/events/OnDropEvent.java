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
 package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designable.DesignableFactory;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.service.DesignableService;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.ui.scripting.TemplateComponent;


/**
 * This event is exected when a Designable is dropped on the {@link DesignableLayoutContainer} in which this event is configured.
 * 
 * The params passed in request is drag-source which is the id of the {@link DesignableFactory} being dropped.
 * 
 * @author kureem
 *
 */
public class OnDropEvent implements Event{

	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(new JMap().put("drag-source", ClientProxy.getDragSourceId()).put("x", ClientProxy.getDragSourcePositionX()),this );
		
	}

	@SuppressWarnings("unchecked")
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String id = request.get("drag-source");
		System.out.println(request.get("x"));
		//the designable is effectively the rectangle that represents the component dropped
		Application root = container.getRoot();
		DesignableFactory DragItem = (DesignableFactory)root.getDescendentById(id);
		if(DragItem == null){
			DesignableService service = SpringUtil.getBeanOfType(DesignableService.class);
			DragItem = service.getDesignable(id);
		}
		// this is the component to be added into
		Container component = DragItem.getInstance();
		//component.setAttribute("des-id", DragItem.getUniqueId());
		
		//container is the container into which the component is being dropped. It should be instance of LayoutContainer
		DesignableLayoutContainer layoutContainer = null;
		if(container instanceof EXConfigVerticalBar){
			//has been dropped on config vertical bar
			Container c = ((EXConfigVerticalBar)container).getComponent();
			if(c instanceof DesignableLayoutContainer){
				layoutContainer = (DesignableLayoutContainer)c;
			}
		}else{
			//has been dropped on container itself
			layoutContainer = (DesignableLayoutContainer)container;
		}
		if(layoutContainer != null){
//			String layoutData = layoutContainer.getPossibleLayoutData(component);
//			component.setAttribute("layoutdata", layoutData);
//			layoutContainer.addChild(component, layoutData);
//			if(layoutContainer instanceof TemplateComponent){
//				layoutContainer.setRendered(false);
//			}
			
			container.getAncestorOfType(EXDesigner.class).addComponent(layoutContainer, component);//.getDescendentOfType(EXConfigVerticalBar.class).getNode(layoutContainer).refresh();
		//	layoutContainer.onAddComponent(component);
			request.put("selid", component.getId());
		//	container.getAncestorOfType(EXDesigner.class).selectItem(component);
		}
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("selid")){
			container.appendJSFragment("remakeSelector('"+request.get("selid")+"');");
		}
	}

}
