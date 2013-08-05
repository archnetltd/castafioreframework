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
 package org.castafiore.ui.ex.form;

import java.util.ArrayList;

import org.castafiore.ui.Decoder;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.Encoder;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.list.DefaultDataModel;
import org.castafiore.ui.ex.list.EXSelect;

public class EXDimensionInput extends EXContainer implements StatefullComponent {

	public EXDimensionInput(String name, Dimension value) {
		super(name, "div");
		
		EXInput input = new EXInput("amount", value.getAmount() + "");
		input.addClass("input");
		input.setStyle("width", "150px");
		addChild(input);
		
		DefaultDataModel model = new DefaultDataModel(new ArrayList<Object>());
		model.addItem("px").addItem("%").addItem("pt").addItem("cm").addItem("mm").addItem("ex").addItem("in").addItem("pc");
		
		EXSelect select = new EXSelect("unit", model);
		select.addClass("unit-combo");
		select.setValue(value.getUnit());
		select.setStyle("width", "50px");
		addChild(select);
	}
	
	public EXDimensionInput(String name) {
		super(name, "div");
		
		EXInput input = new EXInput("amount");
		input.addClass("input");
		input.setStyle("width", "150px");
		addChild(input);
		
		DefaultDataModel model = new DefaultDataModel(new ArrayList<Object>());
		model.addItem("px").addItem("%").addItem("pt").addItem("cm").addItem("mm").addItem("ex").addItem("in").addItem("pc");
		
		EXSelect select = new EXSelect("unit", model);
		select.addClass("unit-combo");
		select.setStyle("width", "50px");
		addChild(select);
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
		String amount =getDescendentOfType(EXInput.class).getValue().toString();
		String unit = getDescendentOfType(EXSelect.class).getValue().toString();
		
		Dimension dim = new Dimension(unit,Integer.parseInt(amount));
		
		return dim;
		
	}

	public void setDecoder(Decoder decoder) {
		
	}

	public void setEncoder(Encoder encoder) {
		
	}

	public void setRawValue(String rawValue) {
		
	}

	public void setValue(Object value) {
		
		if(value instanceof Dimension){
			getDescendentOfType(EXInput.class).setValue(((Dimension) value).getAmount() + "");
			getDescendentOfType(EXSelect.class).setValue(((Dimension) value).getUnit());
		}
	}

}
