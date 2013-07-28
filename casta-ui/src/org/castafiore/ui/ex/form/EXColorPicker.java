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
import org.castafiore.utils.EventUtil;
import org.castafiore.utils.ResourceUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXColorPicker extends EXInput {

	public EXColorPicker(String name) {
		super(name);
		addScript(ResourceUtil.getJavascriptURL("jquery/ui.colorpicker.js"));
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/colorpicker/colorpicker.css"));
		addEvent(new ColorPickerEvent(), Event.CLICK);
		
	}
	
	public EXColorPicker(String name, String value) {
		super(name);
		addScript(ResourceUtil.getJavascriptURL("jquery/ui.colorpicker.js"));
		addStyleSheet(ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/colorpicker/colorpicker.css"));
		addEvent(new ColorPickerEvent(), Event.CLICK);
		setValue(value);
		setAttribute("method", "clear");
		setAttribute("ancestor", getClass().getName());
		addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.DOUBLE_CLICK);
		
	}
	
	public void clear(){
		setValue("transparent");
		setStyle("background-color", "none");
	}
	
	public static class ColorPickerEvent implements Event{

		public void ClientAction(ClientProxy application) {
			application.appendJSFragment("startColorPicker(this)");
			
		}

		public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
			// TODO Auto-generated method stub
			return false;
		}

		public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	

	

}
