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

import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class OnOutEvent implements Event {
	public void ClientAction(ClientProxy container) {
		//container.makeServerRequest(this);
		container.setStyle("opacity", "1");
		
	}

	public boolean ServerAction(Container container,
			Map<String, String> request) throws UIException {
		//System.out.println(request);
		
		Application root = container.getRoot();
		
		String[] dirs = new String[]{"top", "right", "bottom", "left"};
		String[] types = new String[]{"margin", "border", "padding"};
		Double height = 0.0;
		Double width = 0.0;
		for(String type : types){
			
			for(String dir : dirs){
				String style = type + "-" + dir;
				String val = request.get(style);
				if(val != null && val.length() > 0 && val.endsWith("px")){
					Double d = new Double(val.replace("px", ""));
					if(dir.equals("top") || dir.equals("bottom")){
						height = height + d;
					}else{
						width = width + d;
					}
					
				}
			}
		}
		
		
		Double resizerHeight = new Double( request.get("height"));
		Double resizerWith = new Double(request.get("width"));
		
		Double realWidth = resizerWith - width;
		Double realHeight = resizerHeight - height;
		if(request.containsKey("vertical-layout")){
			
			String sourceId = request.get("sourceid");
			Container source = root.getDescendentById(sourceId);
			source.setStyle("width", realWidth + "px");
			source.setStyle("height", realHeight + "px");
			source.setStyle("position", "static");
		}else{
			String sourceId = request.get("sourceid");
			Container source = root.getDescendentById(sourceId);
			source.setStyle("width", realWidth + "px");
			source.setStyle("height", realHeight + "px");
			source.setStyle("position", "absolute");
			source.setStyle("top", source.getStyle("top"));
			
		}
			
		return true;
	}

	public void Success(ClientProxy container,
			Map<String, String> request) throws UIException {
		ClientProxy root = container.getRoot();
		
		String sourceId = request.get("sourceid");
		ClientProxy source = root.getDescendentById(sourceId);
		source.setStyle("position", "static");
		
	}
}
