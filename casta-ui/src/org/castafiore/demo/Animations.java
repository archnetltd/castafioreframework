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
 package org.castafiore.demo;

import java.util.Map;
import java.util.Random;

import org.castafiore.ui.Container;
import org.castafiore.ui.DescriptibleApplication;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;

public class Animations extends EXApplication implements DescriptibleApplication{
	
	private static Random rand = new Random();
	
	 

	public Animations() {
		super("animations");
		
		for(int i = 0; i < 10; i ++){
			EXContainer div = new EXContainer("toanimate-" + i, "div");
			div.setWidth(Dimension.parse("250px"));
			div.setHeight(Dimension.parse("250px"));
			
			div.setText("Things are going to happen to me");
			div.setStyle("position", "absolute");
			
			Integer red=  rand.nextInt(255);
			Integer blie = rand.nextInt(255);
			Integer yellow = rand.nextInt(255);
			
			String color = "#" +red.toHexString(red) + blie.toHexString(blie) + yellow.toHexString(yellow);
			div.setStyle("background-color", color);
			div.setStyle("top", rand.nextInt(500) + "px");
			div.setStyle("left", rand.nextInt(800) + "px");
			addChild(div);
		}
		
		//EXButton button = new EXButton("btn", "Click me to do random animation");
		addEvent(new RandomAnimatorEvent(), Event.READY);
		
		
		//addChild(button);
		
		
	}
	
	
	public static class RandomAnimatorEvent implements Event{

		public void ClientAction(ClientProxy container) {
			container.setTimeout(container.clone().makeServerRequest(this),1000);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			for(int i = 0; i < 10; i ++){
				String height = rand.nextInt(500) + "px";
				String width = rand.nextInt(800) + "px";
				String opacity = rand.nextDouble() + "";
				String top = rand.nextInt(350) + "px";
				String left = rand.nextInt(350) + "px";
		
				
				JMap animationOptions = new JMap().put("top", top).put("left", left).put("opacity", opacity).put("height", height).put("width", width);
				
				container.getRoot().getDescendentByName("toanimate-" + i).animate(animationOptions, "200", null);
			}
			container.setTimeout(container.clone().makeServerRequest(this),1000);
			
			
			
		}
		
	}


	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

}
