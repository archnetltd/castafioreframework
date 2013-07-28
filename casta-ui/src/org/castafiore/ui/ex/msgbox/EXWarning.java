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
 package org.castafiore.ui.ex.msgbox;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXWarning extends EXContainer{

	public EXWarning(String name) {
		super(name, "div");
		addClass("ui-widget");
		addClass("ui-warning");
		Container c = new EXContainer("msgContainer", "div").addClass("ui-state-error").addClass("ui-corner-all");
		addChild(c);
		addEvent(new Event(){

			
			public void ClientAction(ClientProxy container) {
				container.setTimeout(container.clone().fadeOut(100, container.clone().makeServerRequest(this)), 100);
				
			}

			
			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				container.setDisplay(false);
				return true;
			}

			
			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {
				// TODO Auto-generated method stub
				
			}
			
		}, Event.CLICK);
	}
	
	
	public void addMessage(String message){
		getChild("msgContainer").addChild(new EXContainer("", "p").setText("<span class=\"ui-icon ui-icon-info\"></span><label>"+message+"</label>"));
	}
	
	public void clearMessage(){
		getChild("msgContainer").getChildren().clear();
		getChild("msgContainer").setRendered(false);
	}
	

}
