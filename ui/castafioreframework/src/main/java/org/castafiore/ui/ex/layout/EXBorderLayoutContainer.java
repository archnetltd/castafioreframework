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
 package org.castafiore.ui.ex.layout;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ResourceUtil;

public class EXBorderLayoutContainer extends EXXHTMLFragment implements LayoutContainer , PopupContainer {
	
	public final static String TOP = "0";
	
	public final static String LEFT = "1";
	
	public final static String CENTER  = "2";
	
	public final static String RIGHT = "3";
	
	public final static String BOTTOM  = "4";
	
	
	
	public final static String[] CONTENT_NAMES = {"EXBorderLayoutContainertop", "EXBorderLayoutContainerleft","EXBorderLayoutContainercenter", "EXBorderLayoutContainerright", "EXBorderLayoutContainerbottom"};
	
	public EXBorderLayoutContainer() {
		this("Border Layout");
		
		
	}

	public EXBorderLayoutContainer(String name) {
		
		super(name, ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/layout/EXBorderLayoutContainer.xhtml"));
		
		Container c = ComponentUtil.getContainer("popupContainer", "div", null, null);
		c.setStyle("position", "absolute");
		c.setStyle("top", "10%");
		c.setStyle("left", "10%");
		addChild(c);
		
		for(String s : CONTENT_NAMES){
			EXContainer top = new EXContainer(s, "td");
			top.setAttribute("valign", "top");
			addChild(top);
		}
		setWidth(Dimension.parse("100%"));
	}
	
	protected EXBorderLayoutContainer(String name, String template) {
		super(name, template);
		Container c = ComponentUtil.getContainer("popupContainer", "div", null, null);
		c.setStyle("position", "absolute");
		c.setStyle("top", "10%");
		c.setStyle("left", "10%");
		addChild(c);
		
		for(String s : CONTENT_NAMES){
			EXContainer top = new EXContainer(s, "td");
			top.setAttribute("valign", "top");
			addChild(top);
		}
		setWidth(Dimension.parse("100%"));
	}

	public void addChild(Container child, String layoutData) {
		validateLayoutData(layoutData);
		getContainer(Integer.parseInt(layoutData.toString())).addChild(child);
	}
	
	public Container getContainer(int direction)
	{	
		return getChild(CONTENT_NAMES[direction]);
	}
	
	public Container getContainer(String layoutData) {
		validateLayoutData(layoutData);
		return getContainer(Integer.parseInt(layoutData.toString()));
		
	}

	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		try
		{
			if(layoutData != null  && Integer.parseInt(layoutData.toString())>= 0 && Integer.parseInt(layoutData.toString()) < CONTENT_NAMES.length)
			{
				return;
			}
			else
			{
				throw new InvalidLayoutDataException("the layout data "+layoutData+" passed in the layoutContainer " + getClass() + "is invalid");
			}
		}
		catch(Exception e)
		{
			throw new InvalidLayoutDataException("the layout data "+layoutData+" passed in the layoutContainer " + getClass() + "is invalid");
		}
		
	}

	public Container getChild(String name, String layoutData) {
		
		validateLayoutData(layoutData);
		
		int direction = Integer.parseInt(layoutData.toString());
		
		return getContainer(direction).getChild(name);
		
	}

	

	public List<Container> getChildren(String layoutData) {
		validateLayoutData(layoutData);
		
		int direction = Integer.parseInt(layoutData.toString());
		
		return getContainer(direction).getChildren();
	}

	public Container getDescendentById(String id, String layoutData) {
		validateLayoutData(layoutData);
		
		int direction = Integer.parseInt(layoutData.toString());
		
		return getContainer(direction).getDescendentById(id);
	}

	public Container getDescendentByName(String name, String layoutData) {
		validateLayoutData(layoutData);
		
		int direction = Integer.parseInt(layoutData.toString());
		
		return getContainer(direction).getDescendentByName(name);
	}

	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		validateLayoutData(layoutData);
		
		int direction = Integer.parseInt(layoutData.toString());
		
		return getContainer(direction).getDescendentOfType(type);
	}

	public List<DroppableSection> getSections() {
		
		List<DroppableSection> sections = new ArrayList<DroppableSection>(5);
		for(int i = 0; i < CONTENT_NAMES.length; i ++)
		{
			Container c = getContainer(i);
			if(c.getChildren().size() > 0){
				DroppableSection section = new DroppableSection(c.getChildByIndex(0).getId(), c.getChildByIndex(0).getName(), i);
				sections.add(section);
			}
		}
		return sections;
	}

	public void removeChildFromLayout(String id) {
		getDescendentById(id).getParent().setRendered(false);
		getDescendentById(id).remove();
		
	}

	public void addPopup(Container popup) {
		popup.setStyle("position", "absolute");
		popup.setStyle("top", "15%");
		popup.setStyle("left", "15%");
		getChild("popupContainer").addChild(popup);
		
	}

	@Override
	public void onReady(ClientProxy proxy) {
		
		super.onReady(proxy);
		
		proxy.fadeIn(100);
		
	}	
	
	
	
	
	
}
