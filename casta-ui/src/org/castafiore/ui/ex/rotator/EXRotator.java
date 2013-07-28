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
 package org.castafiore.ui.ex.rotator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;

public class EXRotator extends EXContainer{

	private RotatorHandler rotatorHandler;
	
	private ViewModel<? extends EXContainer> model;
	
	private RotatorContainerRenderer rotatorContainerRenderer;
	
	
	public EXRotator(String name, RotatorHandler rotatorHandler, ViewModel<? extends EXContainer> viewModel, RotatorContainerRenderer rotatorContainerRenderer) {
		super(name, "div");
		this.rotatorHandler = rotatorHandler;
		this.model = viewModel;
		this.rotatorContainerRenderer = rotatorContainerRenderer;
		init();
	}

	public void init()
	{	
		RotatorContainer rotatorContainer = rotatorContainerRenderer.getRotatorContainer(this);
		int pageSize = model.bufferSize();
		int modelSize= model.size();
		
		int size = pageSize;
		if(pageSize >= modelSize)
		{
			size = modelSize;
		}
		
		for(int i =0; i < size; i ++)
		{
			EXContainer item = model.getComponentAt(i, this);
			
			rotatorContainer.addItem(item);
			/*item.setHeight(new Dimension(rotatorHandler.getHeight(i,i ,this, item))) ;
			item.setWidth(new Dimension(rotatorHandler.getWidth(i,i, this, item))) ;
			item.setStyle("top", rotatorHandler.getTop(i, i,this, item) + "px");
			item.setStyle("left", rotatorHandler.getLeft(i,i,this, item) + "px");
			item.setStyle("opacity", rotatorHandler.getOpacity(i,i, this, item) + "");
			item.setStyle("position", "absolute");*/
			initItem(item, i, i);
			
		}
		addChild(rotatorContainer);
		
		
	}
	
	private void initItem(EXContainer item, int currentPosition, int newPosition)
	{
		item.setAttribute("position", currentPosition + "");
		item.setHeight(new Dimension(rotatorHandler.getHeight(currentPosition,newPosition ,this, item))) ;
		item.setWidth(new Dimension(rotatorHandler.getWidth(currentPosition,newPosition, this, item))) ;
		item.setStyle("top", rotatorHandler.getTop(currentPosition,newPosition,this, item) + "px");
		item.setStyle("left", rotatorHandler.getLeft(currentPosition,newPosition,this, item) + "px");
		item.setStyle("opacity", rotatorHandler.getOpacity(currentPosition,newPosition, this, item) + "");
		item.setStyle("position", "absolute");
	}
	
	public Map<String, Integer> rotate(int step)
	{
		RotatorContainer rotatorContainer = getDescendentOfType(RotatorContainer.class);
		int childrenSize = rotatorContainer.getItems().size();
		int missing = 0;
		int modelSize = model.size();
		if((childrenSize + step) < modelSize)
		{
			if(step > 0)
				missing = step;
		}
		
		for(int i = 0; i < missing; i ++)
		{
			EXContainer item = model.getComponentAt(childrenSize + i, this);
			rotatorContainer.addItem(item);
			int previousContainerPosition = Integer.parseInt(rotatorContainer.getItems().get(rotatorContainer.getItems().size() -2).getAttribute("position"));
			
			int currentPosition = previousContainerPosition + 1;
			int newPosition = currentPosition + step;
			if(newPosition >= modelSize)
			{
				newPosition = newPosition - modelSize;
			}
			else if(newPosition < 0)
			{
				newPosition = modelSize + newPosition;
			}
			initItem(item, currentPosition, newPosition);
			
		}
		
		Map<String, Integer> result = new HashMap<String, Integer>();
		List<Container> updatedChildren = rotatorContainer.getItems();
		for(Container child : updatedChildren)
		{
			int currentPosition = Integer.parseInt(child.getAttribute("position"));
			result.put(child.getId(), currentPosition);
			int newPosition = currentPosition + step;
			if(newPosition >= modelSize)
			{
				newPosition = newPosition - modelSize;
			}
			else if(newPosition < 0)
			{
				newPosition = modelSize + newPosition;
			}
			child.setAttribute("position",newPosition + "");
		}
		return result;
	}

	


	public RotatorHandler getRotatorHandler() {
		return rotatorHandler;
	}

	public void setRotatorHandler(RotatorHandler rotatorHandler) {
		this.rotatorHandler = rotatorHandler;
	}

	public ViewModel<? extends EXContainer> getModel() {
		return model;
	}


	public void setModel(ViewModel<? extends EXContainer> model) {
		this.model = model;
	}

	public RotatorContainerRenderer getRotatorContainerRenderer() {
		return rotatorContainerRenderer;
	}

	public void setRotatorContainerRenderer(
			RotatorContainerRenderer rotatorContainerRenderer) {
		this.rotatorContainerRenderer = rotatorContainerRenderer;
	}

	
	
}
