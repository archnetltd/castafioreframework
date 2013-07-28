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
 package org.castafiore.ui.ex.selectable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.js.JMap;

public class EXSelectable extends EXContainer implements Selectable {
	
	private JMap options = new JMap();
	
	private SelectableViewModel<Container> model;
	
	private Set<String> selectItemIds = new HashSet<String>();
	
	private SelectableDecorator selectableDecorator = new DefaultSelectableDecorator();
	
	public EXSelectable(String name, String tagName) {
		super(name, tagName);
	}
	

	public ViewModel<Container> getModel() {
		return model;
	}

	public void setModel(SelectableViewModel<Container> model) {
		this.model = model;
		refresh();
	}

	public SelectableDecorator getSelectableDecorator() {
		return selectableDecorator;
	}

	public void setSelectableDecorator(SelectableDecorator selectableDecorator) {
		this.selectableDecorator = selectableDecorator;
	}

	

	public JMap getSelectableOptions() {
		return options;
	}
	
	public void refresh(){
		this.getChildren().clear();
		this.setRendered(false);
		this.selectItemIds.clear();
		if(this.getEvents().containsKey(Event.SELECTABLE_SELECTED)){
			this.getEvents().get(Event.SELECTABLE_SELECTED).clear();
		}
		addEvent(SELECT_ITEM, Event.SELECTABLE_SELECTED);
		if(model != null){
			int size = model.size();
			for(int i = 0; i < size; i ++){
				Container c = model.getComponentAt(i, this);
				c.setAttribute("ctrlbtn", "false");
				c.addClass("ctrl_sensitive");
				addChild(c);
				if(model.isSelected(i, this)){
					selectableDecorator.decorateSelected(c, this);
					this.selectItemIds.add(c.getId());
				}else{
					selectableDecorator.decorateUnSelected(c, this);
				}
				c.addEvent(CLICK_ITEM, Event.MOUSE_DOWN);
			}
		}
	}
	
	public void refreshSelected(){
		for(Container c : getChildren()){
			if(this.selectItemIds.contains(c.getId())){
				this.selectableDecorator.decorateSelected(c, this);
			}else{
				this.selectableDecorator.decorateUnSelected(c, this);
			}
		}
	}
	
	public List<Container> getSelectedItems(){
		List<Container> result = new ArrayList<Container>(selectItemIds.size());
		for(String id :selectItemIds){
			result.add(getDescendentById(id));
		}
		return result;
	}
	
	/************************event****************************/
	
	
	private final static Event CLICK_ITEM = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask();
			container.makeServerRequest(new JMap().put("ctrlbtn", container.getAttribute("ctrlbtn")) ,this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			EXSelectable select = container.getAncestorOfType(EXSelectable.class);
			if("false".equals(request.get("ctrlbtn")))
				select.selectItemIds.clear();
			
			
			if(select.selectItemIds.contains(container.getId())){
				select.selectItemIds.remove(container.getId());
			}else{
				select.selectItemIds.add(container.getId());
			}
			select.refreshSelected();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	
	private final static Event SELECT_ITEM = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(new JMap().put("itemSelected", ClientProxy.getSelectedItemId()),this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			EXSelectable selectable = container.getAncestorOfType(EXSelectable.class);
			String source = request.get("itemSelected");
			selectable.selectItemIds.add(source);
			selectable.refreshSelected();
			return true;
		
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	
	
	
	/**************************************************/

}
