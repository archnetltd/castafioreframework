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



import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.layout.DroppableSection;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

public class EXDroppableDynaForm extends EXDynaformPanel implements DesignableLayoutContainer{

	public EXDroppableDynaForm() {
		super("Dynamic form", "Create  a dynamic form");
	
		getBodyContainer().setStyle("min-width" , "200px");
		getBodyContainer().setStyle("min-height" , "150px");
		//setLabelWidth(Dimension.parse("200px"));
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
		setAttribute("title", "Create  a dynamic form");
		setDraggable(false);
		setShowCloseButton(false);
	}

	public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

	private StatefullComponent assertStatefull(Container c)throws UIException{
		if(!(c instanceof StatefullComponent)){
			throw new UIException("only StatefullComponents can be added in a field set");
		}else{
			return (StatefullComponent)c;
		}
	}
	public void addChild(Container child, String layoutData) {
		String label = child.getAttribute("label");
		if(!StringUtil.isNotEmpty(label)){
			label = child.getName();
		}
		if(child instanceof StatefullComponent){
			addField(label, (StatefullComponent)child);
		}else if(child instanceof EXButton){
			addButton((EXButton)child);
		}else{
			super.addOtherItem(child);
		}
		
	}

	public Container getChild(String name, String layoutData) {
		return getDescendentByName(name);
	}

	public List<Container> getChildren(String layoutData) {
		List<Container> result  = new ArrayList<Container>(1);
		result.add(getDescendentByName(layoutData));
		
		return result;
	}

	public Container getContainer(String layoutData) {
		return getDescendentByName(layoutData);
	}

	public Container getDescendentById(String id, String layoutData) {
		return getDescendentByName(layoutData);
	}

	public Container getDescendentByName(String name, String layoutData) {
		return getDescendentByName(layoutData);
	}

	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getDescendentByName(layoutData);
	}

	public List<DroppableSection> getSections() {
		List<DroppableSection> sections = new ArrayList<DroppableSection>();
		
		Container body = getBody();
		for(Container tr : body.getChildren()){
			if(tr.getChildren().size() ==2){
				Container stf = tr.getDescendentOfType(StatefullComponent.class);
				sections.add(new DroppableSection(stf.getId(), stf.getName(), stf.getName()));
			}else{
				Container stf = tr.getChildren().get(0).getChildren().get(0);
				sections.add(new DroppableSection(stf.getId(), stf.getName(), stf.getName()));
			}
		}
//		for(StatefullComponent stf : stfs){
//			sections.add(new DroppableSection(stf.getId(), stf.getName(), stf.getName()));
//		}
		
		List<Button> buttons = getButtons();
		for(Button btn : buttons){
			sections.add(new DroppableSection(btn.getId(), btn.getName(), btn.getName()));
		}
		return sections;
	}

	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
	}

	public void removeChildFromLayout(String id) {
		Container c = getDescendentById(id);
		if(c instanceof EXButton){
			c.getParent().getParent().setRendered(false);
			c.getParent().remove();
		}else{
			//c.getAncestorOfType(FormItem.class).getParent().setRendered(false);
			//c.getAncestorOfType(FormItem.class).remove();
			Container parent = c.getAncestorByName("tr");
			parent.remove();

		}
	}

	public String getPossibleLayoutData(Container c) {
		return c.getName();
	}


	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveUp(component.getParent().getParent(), this.getBody());
		setRendered(false);
	}

	@Override
	public void moveDown(Container component) {
		ComponentUtil.moveDown(component.getParent().getParent(), this.getBody());
		setRendered(false);
	}

}
