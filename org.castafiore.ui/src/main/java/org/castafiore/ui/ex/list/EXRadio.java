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

package org.castafiore.ui.ex.list;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.FormComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.dynaform.InputVerifier;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXRadio<T> extends EXContainer implements FormComponent<T>, Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DataModel<T> model;
	
	private InputVerifier verifier;
	
	public EXRadio(String name){
		super(name,"div");
	}
	
	public EXRadio<T> setModel(DataModel<T> model){
		this.model = model;
		refresh();
		return this;
	}
	
	public void refresh(){
		this.getChildren().clear();
		setRendered(false);
		if(model != null){
			int size =  model.getSize();
			for(int i = 0; i < size;i++){
				T v = model.getValue(i);
				Container radio = new EXContainer("__" + getName(), "input");
				radio.setReadOnlyAttribute("type", "radio");
				radio.setAttribute("value", i +"");
				addChild(radio.addEvent(this, CLICK));
				addChild(new EXContainer("", "label").setText(v.toString()));
			}
		}
		
	}
	
	public DataModel<T> getModel(){
		return model;
	}
	
	public EXRadio<T> setSelectedIndex(int index){
		setAttribute("value", index + "");
		return this;
	}
	
	public int getSelectedIndex(){
		try{
			return Integer.parseInt(getAttribute("value"));
		}catch(Exception e){
			setSelectedIndex(0);
			return 0;
		}
	}

	@Override
	public T getValue() {
		if(model != null)
			return model.getValue(getSelectedIndex());
		return null;
	}

	@Override
	public void setValue(T value) {
		if(model != null){
			int size =  model.getSize();
			for(int i = 0; i < size;i++){
				if(value.equals(model.getValue(i))){
					setSelectedIndex(i);
				}
			}
		}else{
			model = new DefaultDataModel<T>().addItem(value);
			refresh();
		}
		
		
	}

	@Override
	public FormComponent<T> setInputVerifier(InputVerifier verifier) {
		this.verifier = verifier;
		return this;
	}

	@Override
	public InputVerifier getInputVerifier() {
		return verifier;
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.getParent().setAttribute("value", container.getAttribute("value"));
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}



}
