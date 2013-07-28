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
 package org.castafiore.webwizard.forms.impl;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.webwizard.Template;

public class EXSelectTemplate extends EXBorderLayoutContainer implements StatefullComponent, Event {
	
	private String selectedTemplate;

	public EXSelectTemplate(String name, List<Template> templates) {
		super(name);
		
		Container thubmnails = new EXContainer("thumbnails", "div");
		thubmnails.setWidth(Dimension.parse("97%"))
		.setHeight(Dimension.parse("320px"))
		.setStyle("overflow-y", "scroll")
		.setStyle("overflow-x", "auto")
		.setStyle("margin", "5px")
		.setStyle("padding", "5px")
		.addClass("ui-corner-all")
		.addClass("ui-widget-content");
		addChild(thubmnails, LEFT);
		for(Template t : templates){
			Container img = new EXContainer(t.getName(), "img");
			img.setAttribute("src", t.getThumbnailUrl()).setStyle("float", "left").setStyle("padding", "5px").addEvent(this, CLICK);
			thubmnails.addChild(img);
		}		
//		Container search = new EXContainer("search", "div");
//		search.setWidth(Dimension.parse("135px"))
//		.setHeight(Dimension.parse("300px"))
//		.setStyle("overflow", "auto")
//		.setStyle("margin", "5px")
//		.setStyle("padding", "5px")
//		.addClass("ui-corner-all")
//		.addClass("ui-widget-content");
//		
//		addChild(search, CENTER);
		
	}


	public String getSelectedTemplate(){
		return selectedTemplate;
	}
	
	public Decoder getDecoder() {
		
		return null;
	}


	public Encoder getEncoder() {
		
		return null;
	}

	
	public String getRawValue() {
		
		return null;
	}


	public Object getValue() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setDecoder(Decoder decoder) {
		
		
	}

	
	public void setEncoder(Encoder encoder) {
		
	}


	public void setRawValue(String rawValue) {
		
		
	}


	public void setValue(Object value) {
		// TODO Auto-generated method stub
		
	}


	
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}


	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		if(selectedTemplate != null){
			Container c = container.getAncestorOfType(EXSelectTemplate.class).getDescendentByName(selectedTemplate);
			if(c != null){
				c.removeClass("ui-state-active");
			}
		}
		selectedTemplate = container.getName();
		container.addClass("ui-state-active");
		return true;
	}


	
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	

}
