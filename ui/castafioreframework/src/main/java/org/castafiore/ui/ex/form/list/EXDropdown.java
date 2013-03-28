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

package org.castafiore.ui.ex.form.list;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.input.Decoder;
import org.castafiore.ui.input.Encoder;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;




/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXDropdown<T>  extends EXContainer implements StatefullComponent{
	
	private final static Event OPEN = new Event(){

		public void ClientAction(ClientProxy container) {
			//container.hover(fn1, fn2)
			String inputId = container.getAncestorOfType(EXDropdown.class).getChildren().get(0).getChildren().get(0).getId();
			String dropdownId = container.getAncestorOfType(EXDropdown.class).getChildren().get(1).getId();
			container.addMethod("adjustBelow", new JMap().put("input", inputId).put("dropdown", dropdownId));
			//.getAncestorOfType(EXDropdown.class).getChildren().get(1).fadeIn(100);
			//ifClose.setAttribute("state", "open");
			
			//ClientProxy ifOpen = container.clone().setAttribute("state", "close").getAncestorOfType(EXDropdown.class).getChildren().get(1).fadeOut(100);
			//container.IF(container.getAttribute("state").equal("close"), ifClose, ifOpen);*/
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
			if(container.getAttribute("state").equals("close")){
				container.getAncestorOfType(EXDropdown.class).getDescendentOfType(EXList.class).setDisplay(true);
				container.setAttribute("state", "open");
			}else{
				container.getAncestorOfType(EXDropdown.class).getDescendentOfType(EXList.class).setDisplay(false);
				container.setAttribute("state", "close");
			}
			//container.getAncestorOfType(EXDropdown.class).getDropdown().setDisplay(true);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private final static Event CLOSE = new Event(){

		public void ClientAction(ClientProxy container) {
			container.getAncestorOfType(EXDropdown.class).getChildren().get(1).fadeOut(100);
			
		}

		public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
			container.getAncestorOfType(EXDropdown.class).getDropdown().setDisplay(false);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	private final static Event updateText = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest( this);
			
		}

		public boolean ServerAction(Container container, Map<String, String> request) throws UIException {
			//container.getAncestorOfType(EXDropdown.class)
			Object value = ((StatefullComponent)container).getValue();
			container.getAncestorOfType(EXDropdown.class).getDescendentOfType(EXInput.class).setValue(value);
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request) throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	
	

	
	

	public EXDropdown(String name, StatefullComponent list) {
		
		super(name, "div");
		
		EXContainer inputLayer = new EXContainer("inputLayer", "div");
		
		addChild(inputLayer);
		EXInput field = new EXInput("field");
		//field.setHeight(Dimension.parse("16px"));
		//field.setStyleClass("x-form-text x-form-field x-form-focus");
		inputLayer.addChild(field);
		
		EXContainer trigger = new EXContainer("trigger", "img");
		trigger.setAttribute("state", "close");
		trigger.addEvent(OPEN, Event.CLICK);
		//trigger.setStyleClass("x-form-trigger");
		trigger.setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/dropdown/s.gif"));
		inputLayer.addChild(trigger);
		
		EXContainer dropdownWrap = new EXContainer("", "div");
		//dropdownWrap.setStyleClass("x-combo-list");
		//dropdownWrap.setStyle("z-index", "11000");
		//dropdownWrap.setStyle("position", "absolute");
		addChild(dropdownWrap);

		dropdownWrap.addChild(list);
		dropdownWrap.setDisplay(false);
		list.addEvent(CLOSE, Event.CLICK);
		list.addEvent(updateText, Event.CLICK);
		
		//setWidth(Dimension.parse("195px"));
		//list.setStyle("width", "195px");
		list.setName("list");
		
		
	}
	
	public EXDropdown(String name, DataModel<T> model) {
		this(name,new EXList<T>("list", model));
	}
	
	@Override
	public Container setWidth(Dimension dimension)
	{
		int amount = dimension.getAmount();
		int inputAmount = amount - 23;
		super.setWidth(dimension);
		getInput().setWidth(new Dimension(inputAmount));
		getDropdown().setWidth(dimension);
		return this;
	}
	
//	public  EXList<T> getList(DataModel<T> model)
//	{
//		EXList<T> list = new EXList<T>("list", model);
//		return list;
//	}
	
	
	public StatefullComponent getInput()
	{
		return (StatefullComponent)getChildByIndex(0).getChildByIndex(0);
	}

	public StatefullComponent getDropdown()
	{
		return (StatefullComponent)getChildByIndex(1).getChildByIndex(0);
	}

	public String getRawValue() {
		return getInput().getRawValue();
	}


	public void setRawValue(String rawValue) {
		getInput().setRawValue(rawValue);
		
	}

	public Decoder getDecoder() {
		
		return getInput().getDecoder();
	}

	public Encoder getEncoder() {
		return getInput().getEncoder();
	}

	public Object getValue() {
		return getInput().getValue();
	}

	public void setDecoder(Decoder decoder) {
		getInput().setDecoder(decoder);
		
	}

	public void setEncoder(Encoder encoder) {
		getInput().setEncoder(encoder);
		
	}

	public void setValue(Object value) {
		getInput().setValue(value);
		getDropdown().setValue(value);
		
	}
	
	
	
	

}
