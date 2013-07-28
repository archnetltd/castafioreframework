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
 package org.castafiore.blog.ui.ng.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.blog.ui.ng.EXBlogWidget;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.dynaform.FormModel;
import org.castafiore.ui.ex.form.button.Button;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.ComponentUtil;

public class EXFormBlogWidget extends EXBlogWidget implements DynaForm{
	
	private FormModel model;

	public EXFormBlogWidget(String name, String title) {
		super(name, title);
		EXContainer div = new EXContainer("div", "div");
		//addChild(div);
		setBody(div);
	}

	public List<EXButton> getActions() {
		List buttons = getChild("fieldsetButton").getChildren();
		return buttons;
	}

	public List<StatefullComponent> getFields() {
		List result = new ArrayList();
		ComponentUtil.getDescendentsOfType(this, result, StatefullComponent.class);
		return result;
	}

	public FormModel getFormModel() {
		return model;
	}

	public void setFormModel(FormModel model) {
		this.model = model;
		
		Container div = getDescendentByName("div");
		div.getChildren().clear();
		div.setRendered(false);
		
		int size = model.size();
		for(int i = 0; i <size; i++){
			EXContainer fieldSet = new EXContainer("", "div");
			//fieldSet.addClass("fieldset");
			String label = model.getLabelAt(i, this);
			
			Container uilabel = ComponentUtil.getContainer("", "span", label, null);
			
			
			StatefullComponent stf = model.getFieldAt(i, this);
			//stf.addClass("input");
			fieldSet.addChild(uilabel);
			fieldSet.addChild(stf);
			div.addChild(fieldSet);
		}	
		
		EXContainer fieldsetButton = new EXContainer("fieldsetButton", "div");
		fieldsetButton.addClass("fieldsetButton");
		div.addChild(fieldsetButton);
		int actionSize = model.actionSize();
		for(int i = 0; i < actionSize; i++){
			EXButton button = model.getActionAt(i, this);
			button.addClass("button");
			fieldsetButton.addChild(button);
		}
		
		
	}

	public DynaForm addButton(Button button) {
		// TODO Auto-generated method stub
		return this;
		
	}

	public DynaForm addField(String label, StatefullComponent input) {
		// TODO Auto-generated method stub
		return this;
		
	}

	public StatefullComponent getField(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, StatefullComponent> getFieldsMap() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.dynaform.DynaForm#setLabelFor(java.lang.String, org.castafiore.ui.StatefullComponent)
	 */
	@Override
	public void setLabelFor(String label, StatefullComponent c) {
		// TODO Auto-generated method stub
		
	}
}
