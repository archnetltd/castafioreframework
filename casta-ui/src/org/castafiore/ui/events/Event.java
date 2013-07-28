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

package org.castafiore.ui.events;

import java.io.Serializable;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;

/**
 * The event model in castafiore
 * It is split into 3 parts due to the fact that in a web application codes can be executed on the browser, and on the server as well
 * 
 * Of course the Success method is executed after a ServerAction
 * 
 *    				request data					requestData
 * ClientAction -----------------> ServerAction-------------------->Success
 * 
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * June 27 2008
 */
public interface Event extends Serializable {
	
	/**
	 * event type on blur
	 */
	public final static int BLUR = 1;
	
	public final static int CHANGE = 2;
	
	public final static int CLICK = 3;
	
	public final static int DOUBLE_CLICK = 20;
	
	public final static int FOCUSS = 4;
	
	public final static int HELP = 5;
	
	public final static int HOVER = 6;
	
	public final static int KEY_DOWN = 7;
	
	public final static int KEY_PRESS = 8;
	
	public final static int KEY_UP = 9;
	
	public final static int LOAD = 10;
	
	public final static int MOUSE_DOWN = 11;
	
	public final static int MOUSE_MOVE = 12;
	
	public final static int MOUSE_OUT = 13;
	
	public final static int MOUSE_OVER = 14;
	
	public final static int MOUSE_UP = 15;
	
	public final static int RESET = 16;
	
	public final static int SELECT = 17;
	
	public final static int SUBMIT = 18;
	
	public final static int UNLOAD = 19;
	
	public final static int READY = 21;
	
	public final static int SCROLL = 22;
	
	
	
	public final static int START_RESIZE = 24;
	
	public final static int RESIZE = 25;
	
	public final static int STOP_RESIZE = 26;
	
	public final static int RIGHT_CLICK = 27;
	
	public final static int RIGHT_MOUSE_UP = 28;
	
	public final static int RIGHT_MOUSE_DOWN = 29;
	
	public final static int DRAG = 30;
	
	
	public final static int START_DRAG = 31;
	
	public final static int END_DRAG = 32;
	
	
	public final static int DND_ACTIVATE = 33;
	
	public final static int DND_DEACTIVATE = 34;
	
	public final static int DND_OVER = 35;
	
	public final static int DND_OUT = 36;
	
	public final static int DND_DROP = 37;
	
	
	public final static int SELECTABLE_SELECTED = 38;
	
	public final static int SELECTABLE_SELECTING = 39;
	
	public final static int SELECTABLE_START = 40;
	
	public final static int SELECTABLE_STOP = 41;
	
	public final static int SELECTABLE_UNSELECTED = 42;
	
	public final static int SELECTABLE_UNSELECTING = 43;
	
	public final static int MISC = 44;
	
	
	
	/**
	 * This method represents the client part of an event.
	 * This method is executed only once on rendering of the component.
	 * {@link ClientProxy} provides a nice abstraction for generating javascript codes. It is the browser version of the component
	 * 
	 * It is this method that is responsible in starting a server request to be executed in the serverAction method
	 * 
	 * This allows user to make actions that does not require server actions
	 * 
	 * @param container - A proxy for the browser
	 */
	public void ClientAction(ClientProxy container);
	
	
	
	/**
	 * executed whenever a serverRequest is made in the clientAction method
	 * {@link ClientProxy.makeServerRequest}
	 * 
	 * Here the component is the server version of the Component
	 * 
	 * The browser will be update only if this method returns true
	 * This allows users to make server action, that does not require update of the client
	 * 
	 * @param container - the container on which the event was registered
	 * @param request	- request parameters that can be passed in the {@link ClientProxy.makeServerRequest}
	 * @return			- tells the engine to update the browser or not
	 * @throws UIException
	 */
	public boolean ServerAction(Container container, Map<String, String> request)throws UIException;
	

	/**
	 * This method is executed after each server action
	 * Basically it is the javascript that may be required after a server action
	 * @param container 	- again, this is the browser version of the component on which the event was registered
	 * @param request		- request parameters that can be added in the serverAction method
	 * @throws UIException
	 */
	public void Success(ClientProxy container,Map<String, String> request)throws UIException;
}
