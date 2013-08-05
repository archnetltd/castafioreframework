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

package org.castafiore.ui.ex.form;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.Expression;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXCheckBox extends EXInput{
	
	public EXCheckBox(String name) {
		this(name, false);
	}
	
	public EXCheckBox(String name, boolean checked) {
		super(name);
		setReadOnlyAttribute("type", "checkbox");
		if(checked)
		{
			super.setValue("checked");
			super.setAttribute("checked", "checked");
		}
		else
			super.setValue("");
		
	}

	
	public boolean isChecked(){
		return "checked".equalsIgnoreCase(getValue().toString());
	}
	
	public void setChecked(boolean checked){
		if(checked){
			super.setValue("checked");
		}else{
			super.setValue("");
		}
		if(!rendered() && getParent() != null){
			getParent().setRendered(false);
		}else{
			setRendered(false);
		}
			
	}
	
	
	public static class CHANGE_EVT implements Event{

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			
		}

		public void ClientAction(ClientProxy container) {
			Expression exp = container.getAttribute("value").equal("checked");
			
			container.IF(exp, container.clone().setAttribute("value", ""), container.clone().setAttribute("value", "checked"));
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			return false;
		}
	
	}
	
	
	public void onReady(ClientProxy proxy){
		if(isChecked()){
			proxy.setAttribute("checked", "checked");
		}
	}

}
