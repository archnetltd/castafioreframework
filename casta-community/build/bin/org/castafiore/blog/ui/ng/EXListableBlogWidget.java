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

public class EXListableBlogWidget extends EXBlogWidget {

	private ListModel model;	
	
	public EXListableBlogWidget(String name, String title) {
		super(name, title);
		EXContainer ul = new EXContainer("ul", "ul");
		setBody(ul);
	}
	
	
	public void setModel(ListModel model){
		this.model = model;
		Container ul = getDescendentByName("ul");
		ul.getChildren().clear();
		ul.setRendered(false);
		
		for(int i = 0; i < model.size(); i ++){
			EXContainer li = new EXContainer("", "li");
			EXContainer span = new EXContainer("", "span");
			span.setText(model.getTextAt(i));
			Event event = model.getEventAt(i);
			if(event != null){
				span.addEvent(event, Event.CLICK);
			}
			li.addChild(span);
			ul.addChild(li);
		}
		
	}
	
	public ListModel getModel(){
		return model;
	}

}
