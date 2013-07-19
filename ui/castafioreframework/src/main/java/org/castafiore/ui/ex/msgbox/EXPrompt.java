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
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.button.EXButton;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;

public abstract class EXPrompt extends EXDynaformPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public EXPrompt(String name, String title, String text) {
		super(name,title);
		
		addField(text, new EXInput("input"));
		EXButton yesButton = new EXButton("ok", "Ok");
		yesButton.addEvent(new OnOkEvent(), Event.CLICK);
		addButton(yesButton);
		
		EXButton noButton = new EXButton("cancel", "Cancel");
		noButton.addEvent(CLOSE_EVENT, Event.CLICK);
		addButton(noButton);
	}
	
	public String getInputValue()
	{
		return  ((StatefullComponent)getDescendentByName("input")).getValue().toString();
	}
	
	public StatefullComponent getInput(){
		return  ((StatefullComponent)getDescendentByName("input"));
	}



	public abstract boolean onOk(Map<String, String> request);
	
	
	public static class OnOkEvent implements Event
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXPrompt.class));
			container.makeServerRequest( this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			if(container.getAncestorOfType(EXPrompt.class).onOk(request)){
				EXPrompt dialog = container.getAncestorOfType(EXPrompt.class);
				request.put("dId", dialog.getId());
				dialog.remove();
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			if(request.containsKey("dId"))
				container.appendJSFragment(new ClientProxy("#" + request.get("dId")).fadeOut(100).getCurrentJQuery());
		}	
	}
}
