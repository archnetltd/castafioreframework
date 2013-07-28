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
 package org.castafiore.blog.ui.ng.events;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.castafiore.blog.ui.BlogApplication;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.dynaform.DynaForm;

public class SignInEvent implements Event{

	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		DynaForm form = container.getAncestorOfType(DynaForm.class);
		StatefullComponent username = (StatefullComponent)form.getDescendentByName("username");
		StatefullComponent password = (StatefullComponent)form.getDescendentByName("password");
		
		
		boolean valid = true;
		String sUsername = username.getValue().toString();
		String sPassword = password.getValue().toString();
		
		if(StringUtils.isEmpty(sUsername)){
			username.setStyle("border", "dashed 1px red");
			valid = false;
		}
		
		if(StringUtils.isEmpty(sPassword)){
			password.setStyle("border", "dashed 1px red");
			valid = false;
		}
		
		if(valid){
			try{
				BlogApplication app = (BlogApplication)container.getRoot();
				app.signIn(sUsername, sPassword);
			
			}catch(Exception e){
				throw new UIException(e);
			}
		}
		
		
		return true;
		
		
		
		
		
		
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
