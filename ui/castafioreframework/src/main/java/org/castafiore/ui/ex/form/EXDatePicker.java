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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXDatePicker extends EXMaskableInput {
	
	public EXDatePicker(String name) {
		this(name, new Date());
	}

	public EXDatePicker(String name, Date date) {
		super(name,new SimpleDateFormat("dd-MM-yyyy").format(date), "99-99-9999");
		//this.setValue(date);
		
//		addEvent(new Event(){
//
//			public void ClientAction(ClientProxy application) {
//				application.addMethod("datepicker", new JMap().put("dateFormat", "dd/mm/yy"));
//				
//			}
//
//			public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
//				
//				
//			}
//
//			public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
//				
//				return false;
//			}
//			
//		}, Event.READY);
		
	}
	
	public Object getValue(){
		try{
			String raw = super.getRawValue();
			return new SimpleDateFormat("dd-MM-yyyy").parse(raw);
		}catch(Exception e){
			return null;
		}
	}
	
	public void setValue(Object date){
		if(date != null){
			if(date instanceof String)
				setRawValue(date.toString());
			else
				setRawValue(new SimpleDateFormat("dd-MM-yyyy").format(date));
		}
		else
			setRawValue("");
	}
	
	
//	public void onReady(ClientProxy proxy){
//		proxy.addMethod("datepicker", new JMap());
//	}

}
