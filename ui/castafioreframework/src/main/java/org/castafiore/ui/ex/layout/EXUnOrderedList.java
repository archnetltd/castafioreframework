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
import org.castafiore.ui.LayoutContainer;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class EXUnOrderedList extends EXContainer implements LayoutContainer {

	public EXUnOrderedList(String name) {
		super(name, "ul");
		
	}

	public void addChild(Container child, String layoutData) {
		
		int iLayoutData = 0;
		
		if(StringUtil.isNotEmpty(layoutData))
			iLayoutData = Integer.parseInt(layoutData);
		else
			iLayoutData = getChildren().size();
		if(getChildren().size() >= iLayoutData){
			for(int i = getChildren().size()-1; i < iLayoutData; i ++){
				Container c = new EXContainer("", "li");
				if("horizontal".equalsIgnoreCase(getAttribute("direction")))
					c.setStyle("float", "left");
				addChild(c);
			}	
		}
		getContainer(layoutData).addChild(child);
		
	}
	
	protected Container getCell(String layoutData)
	{	int iLayoutData = Integer.parseInt(layoutData);
		if(iLayoutData == getChildren().size()){
			EXContainer li = new EXContainer("", "li");
			addChild(li);
		}
		
		return getChildByIndex(iLayoutData);
	}

	public Container getChild(String name, String layoutData) {
		return getContainer(layoutData).getChild(name);
	}

	public List<Container> getChildren(String layoutData) {
		return getContainer(layoutData).getChildren();
	}

	public Container getContainer(String layoutData) {
		if(StringUtil.isNotEmpty(layoutData))
			return getChildByIndex(Integer.parseInt(layoutData));
		else
			return getChildByIndex(getChildren().size()-1);
	}

	public Container getDescendentById(String id, String layoutData) {
		return getContainer(layoutData).getDescendentById(id);
	}

	public Container getDescendentByName(String name, String layoutData) {
		return getContainer(layoutData).getDescendentByName(name);
	}

	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getContainer(layoutData).getDescendentOfType(type);
	}

	public List<DroppableSection> getSections() {
		List<DroppableSection> result = new ArrayList<DroppableSection>();
		for(int i = 0; i <getChildren().size(); i++)	
		{
			try{
			Container item = getCell(i + "").getChildByIndex(0);
			DroppableSection section = new DroppableSection(item.getId(), item.getName(),i);
			result.add(section);
			}catch(IndexOutOfBoundsException ib){
				
			}
		}
		return result;
	}

	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		
		
	}

	public void removeChildFromLayout(String id) {
		getDescendentById(id).getParent().remove();
		//getDescendentById(id).remove();
		setRendered(false);
		
	}
	

	
	

}
