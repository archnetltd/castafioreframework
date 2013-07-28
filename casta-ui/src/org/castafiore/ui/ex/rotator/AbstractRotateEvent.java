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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;

public abstract class AbstractRotateEvent implements Event {

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		ClientProxy aniContainer = container.getRoot().getDescendentByName(request.get("rotatorName")).getDescendentOfType(RotatorContainer.class);
		Iterator<String> iter = request.keySet().iterator();
		
		List<String> idsToAnimate = new ArrayList<String>();
		while(iter.hasNext())
		{
			String key = iter.next();
			if(key.startsWith("animateditem_"))
			{
				idsToAnimate.add(key);
			}
		}
		
		Collections.sort(idsToAnimate);
		
		for(String key : idsToAnimate)
		{
			String id = StringUtil.split(key, "#")[1];
			String newStyles = request.get(key);
			String[] asStyles = StringUtil.split(newStyles, ";");
			JMap options = new JMap().put("top", asStyles[0]).put("left", asStyles[1]).put("width", asStyles[2]).put("height", asStyles[3]);
			ClientProxy item = aniContainer.getDescendentById(id);
			item.animate(options, "100", null);
		}
			
		
		
		/*for(ClientProxy item : aniContainer.getChildren())
		{
			String id = "item_" + item.getId();
			String newStyles = request.get(id);
			String[] asStyles = StringUtil.split(newStyles, ";");
			JMap options = new JMap().put("top", asStyles[0]).put("left", asStyles[1]).put("width", asStyles[2]).put("height", asStyles[3]);
			item.animate(options, "100", null);
			
		}*/
		
	}

	public abstract void ClientAction(ClientProxy container) ;
		

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(!request.containsKey("steps") )
		{
			throw new UIException("a parameter 'steps' which represents the number of steps to  rotate is not present or is not a valid integer");
		}
		
		if(!request.containsKey("rotatorName"))
		{
			throw new UIException("a parameter 'rotatorName' which represents the name of the rotator to  rotate is not present");
		}
		int steps = Integer.parseInt( request.get("steps"));
		
		String rotatorName = request.get("rotatorName");
		
		Application root = container.getRoot();
		EXRotator rotator = (EXRotator)root.getDescendentByName(rotatorName);
		
		Map<String, Integer> positionsBeforeRotation = rotator.rotate(steps);
		
		List<Container> children = rotator.getDescendentOfType(RotatorContainer.class).getItems();
		int index = 0;
		for(Container child : children)
		{
			int position = Integer.parseInt(child.getAttribute("position"));
			String top = rotator.getRotatorHandler().getTop(positionsBeforeRotation.get(child.getId()), position, rotator, child) + "px";
			String left = rotator.getRotatorHandler().getLeft(positionsBeforeRotation.get(child.getId()),position, rotator, child) + "px";
			String width = rotator.getRotatorHandler().getWidth(positionsBeforeRotation.get(child.getId()),position, rotator, child) + "px";
			String height = rotator.getRotatorHandler().getHeight(positionsBeforeRotation.get(child.getId()),position, rotator, child) + "px";
			String opacity = rotator.getRotatorHandler().getOpacity(positionsBeforeRotation.get(child.getId()),position, rotator, child) + "";
			
			request.put("animateditem_" + getOrderPosition(index, 5) + "#" + child.getId(), top + ";" + left + ";" + width + ";" + height + ";" + opacity);
			index++;
		}
		return true;
	}
	
	
	private String getOrderPosition(Integer index, int size)
	{
		String sIndex = index.toString();
		
		if(sIndex.length() < size)
		{
			while(!(sIndex.length() == size))
			{
				sIndex = "0" + sIndex;
			}
		}
		return sIndex;
	}

}
