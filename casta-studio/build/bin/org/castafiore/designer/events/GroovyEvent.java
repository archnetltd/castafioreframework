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

package org.castafiore.designer.events;

import groovy.lang.Writable;
import groovy.text.Template;

import java.io.CharArrayWriter;
import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.script.ScriptService;
import org.castafiore.groovy.GroovyUtil;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.utils.BaseSpringUtil;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class GroovyEvent implements Event {
	
	private String template;
	
	private int type;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public GroovyEvent(String template, int type) {
		super();
		this.template = template;
		this.type = type;
	}

	public void ClientAction(ClientProxy application) {
		application.mask();
		application.makeServerRequest(this);
	}

	public void Success(ClientProxy component, Map<String, String> requestParameters) throws UIException {
		// TODO Auto-generated method stub
		
	}

	public boolean ServerAction(Container component, Map<String, String> requestParameters) throws UIException {
		if(component.getAncestorOfType(EXDesigner.class) != null){
			return false;
		}
		try
		{
			
			//String path = component.getAncestorOfType(PortalContainer.class).getAttribute("definitionpath");
			SpringUtil.getBeanOfType(ScriptService.class).executeEvent(template,component, requestParameters);

		    
		}
		catch(Exception e)
		{
			throw new UIException(e);
  
		}
		return true;
	}

}
