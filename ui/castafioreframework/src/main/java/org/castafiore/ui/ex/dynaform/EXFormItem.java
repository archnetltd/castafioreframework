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
 package org.castafiore.ui.ex.dynaform;

import org.castafiore.ui.Dimension;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
/**
 * <div class="x-form-item">
		<label class="x-form-item-label" style="width: 75px;">First Name: </label>
		<div style="padding-left: 80px;" class="x-form-element">
			<input type="text" name="first" size="20" class="x-form-text x-form-field" style="width: 202px;" />
		</div>
		<div class="x-form-clear-left"></div>
	</div>
 * @author kureem
 *
 */
public class EXFormItem extends EXContainer {

	public EXFormItem(String name, String slabel, StatefullComponent component) {
		super(name, "div");
		setStyleClass("x-form-item");
		
		EXContainer label = new EXContainer("label", "label");
		label.setText(slabel);
		label.setStyleClass("x-form-item-label");
		label.setWidth(Dimension.parse("75px"));
		addChild(label);
		
		EXContainer formElement = new EXContainer("formElement", "div");
		formElement.addChild(component);
		formElement.setStyle("padding-left", "80px");
		addChild(formElement);
		EXContainer clearLeft = new EXContainer("", "div");
		clearLeft.setStyleClass("x-form-clear-left");
		addChild(clearLeft);
	}
	
	
	public StatefullComponent getFieldInput()
	{
		return (StatefullComponent)getChild("formElement").getChildByIndex(0);
	}

}
