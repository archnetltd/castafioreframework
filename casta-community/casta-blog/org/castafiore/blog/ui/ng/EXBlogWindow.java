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
 package org.castafiore.blog.ui.ng;

import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXBlogWindow extends EXContainer {

	public EXBlogWindow(String name) {
		super(name, "div");
		addClass("portal-wrapper");
		EXContainer header = new EXContainer("header", "div");
		addChild(header);
		header.addClass("portal-header");
		
		EXContainer navigation = new EXContainer("navigation", "div");
		navigation.addClass("portal-navigation");
		EXContainer ul = new EXContainer("navUl","ul");
		navigation.addChild(ul);
		addChild(navigation);
		ul.addClass("bulletproof");
		
		EXContainer leftColumn = new EXBlogColumn("leftColumn");
		addChild(leftColumn);
		leftColumn.addClass("portal-leftcolumn");
		
		
		
		EXContainer body = new EXContainer("body", "div");
		addChild(body);
		body.addClass("portal-content");
		
		EXContainer rightColumn = new EXBlogColumn("rightColumn");
		rightColumn.addClass("portal-rightcolumn");
		addChild(rightColumn);
	}
	
	
	public Container getLeftColumn(){
		return getChild("leftColumn");
	}
	
	public Container getRightColumn(){
		return getChild("rightColumn");
	}
	
	public void setHeader(Container header){
		Container headerContainer = getChild("header");
		headerContainer.getChildren().clear();
		if(header != null){
			headerContainer.addChild(header);
		}
		headerContainer.setRendered(false);
	}
	
	
	public void setBody(Container body){
		Container bodyContainer = getChild("body");
		bodyContainer.getChildren().clear();
		if(body != null){
			bodyContainer.addChild(body);
		}	
		bodyContainer.setRendered(false);
	}
	
	public Container getBody(){
		Container bodyContainer = getChild("body");
		if(bodyContainer.getChildren().size() > 0){
			return bodyContainer.getChildren().get(0);
		}else{
			return null;
		}
	}
	
	
	public void setNavigation(ListModel navigations){
		Container ul = getDescendentByName("navUl");
		ul.getChildren().clear();
		ul.setRendered(false);
		
		if(navigations != null){
			int size = navigations.size();
			for(int i = 0; i < size; i ++){
				EXContainer li = new EXContainer("", "li");
				EXContainer a = new EXContainer("", "a");
				a.setText(navigations.getTextAt(i));
				Event event = navigations.getEventAt(i);
				if(event != null){
					a.addEvent(event, Event.CLICK);
				}
				a.setAttribute("href", "#");
				li.addChild(a);
				ul.addChild(li);
			}
		}
		
	}
	
	public void addInLeftColumn(EXBlogWidget widget, int position){
		widget.setStyle("margin-right", "5px");
		Container left = getChild("leftColumn");
		if( left.getDescendentById(widget.getId()) == null){
			if(position < left.getChildren().size()-1){
				
				left.addChildAt(widget, position);
			}else{
				left.addChild(widget);
			}
		}
	}
	
	public void addInLeftColumn(EXBlogWidget widget){
		widget.setStyle("margin-right", "5px");
		Container left = getChild("leftColumn");
		if( left.getDescendentById(widget.getId()) == null){
			left.addChild(widget);
		}
	}
	
	public void addInRightColumn(EXBlogWidget widget){
		widget.setStyle("margin-left", "13px");
		Container right = getChild("rightColumn");
		if( right.getDescendentById(widget.getId()) == null){
			right.addChild(widget);
		}
	}
	
	public void addInRightColumn(EXBlogWidget widget, int position){
		widget.setStyle("margin-left", "13px");
		Container right = getChild("rightColumn");
		if( right.getDescendentById(widget.getId()) == null){
			if(position < right.getChildren().size()-1){
				
				right.addChildAt(widget, position);
			}else{
				right.addChild(widget);
			}
		}
	}
}
