/*
 * Copyright (C) 2007-2008 Castafiore
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

package org.castafiore.ui.navigation;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class MacCarousel extends EXContainer {

	
	private ViewModel model_;
	
	private int pageSize = 3;
	
	private int curPage = 0;
	
	public MacCarousel(String name, ViewModel model, int pageSize) {
		super(name, "div");
		setStyleClass("carousel");
		
		EXContainer leftButton = new EXContainer("leftButton", "div");
		
		leftButton.setStyleClass("left-button");
		leftButton.addEvent(getPreviousPageEvent(), Event.CLICK);
		addChild(leftButton);
		
		EXContainer content = new EXContainer("content", "div");
		content.setStyleClass("content");
		addChild(content);
		
		EXContainer rightButton = new EXContainer("rightButton", "div");
		rightButton.setStyleClass("right-button");
		addChild(rightButton);
		
		rightButton.addEvent(getEventNextPage(), Event.CLICK);
		
		addEventsOnButtons();
		
	
		this.model_ = model;
		
		
		this.refresh();
		
		
	}

	@Override
	public void refresh() {
		super.refresh();
		
		this.getChild("content").getChildren().clear();
		
		for(int i =  0; i < model_.size(); i ++)
		{
			EXContainer item=  new EXContainer("item-" + i, "div");
			
			item.setStyleClass("item");
			
			if(i >= pageSize)
			{
				item.setDisplay(false);
			}
			item.addChild(model_.getComponentAt(i, this));
			this.getChild("content").addChild(item);
		}
		
		this.getChild("content").setRendered(false);
	}
	
	
	public Event getPreviousPageEvent()
	{
		Event event = new Event()
		{

			public void ClientAction(ClientProxy application) {
				application.makeServerRequest(this);
				
			} 

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				
				int start = Integer.parseInt(requestParameters.get("start"));
				int end = Integer.parseInt(requestParameters.get("end"));
				 
				
				ClientProxy content = component.getAncestorOfType(MacCarousel.class).getDescendentByName("content");
				
				for(int i = 0; i < content.getChildren().size(); i ++)
				{
					ClientProxy item = content.getChildren().get(i);
					
					ClientProxy clone = item.clone();
					
					
					
					if(i < start )
					{
						component.appendJSFragment("setTimeout(\"" + clone.fadeOut(100).getCompleteJQuery().replace('\n', ' ').replace(';', ' ').replace("\"", "'") + "\", 100)");
						item.fadeOut(100);
					}
					else if(i >end)
					{
						//component.appendJSFragment("setTimeout(\"" + clone.fadeOut(100).getCompleteJQuery().replace('\n', ' ').replace(';', ' ').replace("\"", "'") + "\", 100)");
						item.fadeOut(100);
					}
					else
					{
						//item.fadeIn(100);
						
						component.appendJSFragment("setTimeout(\"" + clone.fadeIn(100).getCompleteJQuery().replace('\n', ' ').replace(';', ' ').replace("\"", "'") + "\", 1000)");
					}
				}
				
				
				
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				
				
				MacCarousel c = (MacCarousel)component.getAncestorOfType(MacCarousel.class);
				
				
				if(c.curPage > 0)
				{
					int size = c.model_.size();
					
					int pageSize = c.pageSize;
					
					c.curPage--;
					int curPage = c.curPage;
					
					
					
					int start = curPage*pageSize;
					int end = start + pageSize -1;
					
					requestParameters.put("start", start + "");
					requestParameters.put("end", end + "");
					
					return true;
				}
				return false;
				
				
			}
			
		};
		
		return event;
	}
	
	
	private Event getEventNextPage()
	{
		Event event = new Event()
		{

			public void ClientAction(ClientProxy application) {
				application.makeServerRequest(this);
				
			} 

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				
				int start = Integer.parseInt(requestParameters.get("start"));
				int end = Integer.parseInt(requestParameters.get("end"));
				 
				
				ClientProxy content = component.getAncestorOfType(MacCarousel.class).getDescendentByName("content");
				
				for(int i = 0; i < content.getChildren().size(); i ++)
				{
					ClientProxy item = content.getChildren().get(i);
					
					ClientProxy clone = item.clone();
					
					
					
					if(i < start )
					{
						component.appendJSFragment("setTimeout(\"" + clone.fadeOut(100).getCompleteJQuery().replace('\n', ' ').replace(';', ' ').replace("\"", "'") + "\", 100)");
						item.fadeOut(100);
					}
					else if(i >end)
					{
						//component.appendJSFragment("setTimeout(\"" + clone.fadeOut(100).getCompleteJQuery().replace('\n', ' ').replace(';', ' ').replace("\"", "'") + "\", 100)");
						item.fadeOut(100);
					}
					else
					{
						//item.fadeIn(100);
						
						component.appendJSFragment("setTimeout(\"" + clone.fadeIn(100).getCompleteJQuery().replace('\n', ' ').replace(';', ' ').replace("\"", "'") + "\", 1000)");
					}
				}
				
				
				
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				
				
				MacCarousel c = (MacCarousel)component.getAncestorOfType(MacCarousel.class);
				
				
				if(c.pageSize *(c.curPage + 1) < c.model_.size())
				{
					int size = c.model_.size();
					
					int pageSize = c.pageSize;
					
					c.curPage++;
					int curPage = c.curPage;
					
					
					
					int start = curPage*pageSize;
					int end = start + pageSize -1;
					
					requestParameters.put("start", start + "");
					requestParameters.put("end", end + "");
					
					return true;
				}
				return false;
				
				
			}
			
		};
		
		return event;
	}
	
	
	
	
	private void addEventsOnButtons()
	{
		getChild("leftButton").addEvent(new Event(){

			public void ClientAction(ClientProxy application) {
				application.setAttribute("class", "left-button-over");
				
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
		}, Event.MOUSE_OVER);
		
		
		getChild("leftButton").addEvent(new Event(){

			public void ClientAction(ClientProxy application) {
				application.setAttribute("class", "left-button");
				
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
		}, Event.MOUSE_OUT);
		
		
		
		getChild("rightButton").addEvent(new Event(){

			public void ClientAction(ClientProxy application) {
				application.setAttribute("class", "right-button-over");
				
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
		}, Event.MOUSE_OVER);
		
		
		getChild("rightButton").addEvent(new Event(){

			public void ClientAction(ClientProxy application) {
				application.setAttribute("class", "right-button");
				
			}

			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				
			}

			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
				// TODO Auto-generated method stub
				return false;
			}
			
		}, Event.MOUSE_OUT);
	}

}
