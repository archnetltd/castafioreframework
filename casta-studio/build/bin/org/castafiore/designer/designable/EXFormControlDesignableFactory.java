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
 package org.castafiore.designer.designable;

import org.castafiore.beans.info.IBeanInfo;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designable.DesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXColorPicker;
import org.castafiore.ui.ex.form.EXDatePicker;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXMaskableInput;
import org.castafiore.ui.ex.form.EXPassword;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.AbstractEXList;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXRadio;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

public class EXFormControlDesignableFactory extends AbstractDesignableFactory implements DesignableFactory, IBeanInfo {
	
	private String type="textbox";

	public EXFormControlDesignableFactory() {
		super("DraggableComponent");
		
		 
	}
	public Container getInstance(){
		Container c = getInstance_();
		c.setAttribute("label", c.getName());
		return c;
	}
	protected Container getInstance_(){
		String itemName = getType();
		if(itemName.equals("textbox")){
			return new EXInput("text box");
		}else if(itemName.equals("password")){
			return new EXPassword("password");
		}else if(itemName.equals("textarea")){
			return new EXTextArea("text area");
		}else if(itemName.equals("colorpicker")){
			return new EXColorPicker("color picker");
		}else if(itemName.equalsIgnoreCase("datepicker")){
			return new EXDatePicker("date picker");
		}else if(itemName.equals("button")){
			return new EXButton("button", "button");
		}else if(itemName.equals("checkbox")){
			return new EXCheckBox("check box", true);
		}else if(itemName.equals("select")){
			return new EXSelect("select", new DefaultDataModel<Object>().addItem("Option 1", "Option 2", "Option 3"));
		}else if(itemName.equals("richtextarea")){
			return new EXRichTextArea("richtext");
		}else if(itemName.equals("radio")){
			return new EXRadio("radio", new DefaultDataModel<Object>().addItem("Option 1", "Option 2", "Option 3"));
		}
			
		
		return new EXInput("dsdfs");
	}

	public String getCategory() {
		return "Form controls";
	}

	private String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	


@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equals("label")){
			
			if(c instanceof EXButton){
				((EXButton)c).setText(attributeValue);
			}else{
			
				DynaForm panel = c.getAncestorOfType(DynaForm.class);
				if(panel != null){
					panel.setLabelFor(attributeValue, (StatefullComponent)c);
				}
			}
		}else if( c instanceof AbstractEXList){
			String[] vals = StringUtil.split(attributeValue, ",");
			DefaultDataModel<Object> model = new DefaultDataModel<Object>();
			for(String s : vals){
				model.addItem(s);
			}
			((AbstractEXList)c).setModel(model);
			
		}else if(attributeName.equals("mask") && c instanceof EXMaskableInput){
			((EXMaskableInput)c).applyMask(attributeValue);
		}
		c.setAttribute(attributeName, attributeValue);
	}
	public String getUniqueId() {
		return "core:" + getType();
	}
	
	public String[] getRequiredAttributes(){
		
		if(getType().equals("select") || getType().equals("radio")){
			return new String[]{"label", "options"};
		}
		
		if(getType().equalsIgnoreCase("textbox")){
			return new String[]{"label", "mask"};
		}
		return new String[]{"label"};
	}
	public String getInfoAttribute(String key) {
		return "Creates a " + getType();
	}
	public String[] getKeys() {
		return new String[]{"description"};
	}
	public String getSupportedUniqueId() {
		return getUniqueId();
	}
}
