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
 package org.castafiore.ui.ex.form.button;

import org.castafiore.JQContants;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.StringUtil;

public class EXIconButton extends AbstractButton implements JQContants {

	public EXIconButton(String name, String text, Icons eicon) {
		super(name, "a");
		addClass("fg-button-icon-left");
		setHeight(Dimension.parse("12px"));
		Container icon = ComponentUtil.getContainer("icon", "span", null, ICON_STYLE);
		addChild(icon);
		
		Container uiText = ComponentUtil.getContainer("text", "div", text, null);
		addChild(uiText);
		
		setIcon(eicon);
		setText(text);
	}
	
	public EXIconButton(String name, String text) {
		this(name, text, null);
	}
	
	public EXIconButton(String name,  Icons eicon) {
		this(name, null, eicon);
	}
	public void setIcon(Icons icon){
		if(icon != null){
		 	int ordinal = icon.ordinal();
		 	getChild("icon").setStyle("background-position", ICONS_BACK_POSITON[ordinal]);
		 	getChild("icon").setDisplay(true);
		 	addClass("fg-button-icon-left");
			addClass("fg-button-icon-right");
		}else{
			removeClass("fg-button-icon-left");
			removeClass("fg-button-icon-right");
			getChild("icon").setDisplay(false);
		}
	}
	
	public void setIcon(String iconUrl){
		getChild("icon").setStyle("background-position", (String)null);
		getChild("icon").setStyle("background-image", "url('"+iconUrl+"')");
		addClass("fg-button-icon-left");
		addClass("fg-button-icon-right");
	}
	
	public Container setText(String text){
		if(StringUtil.isNotEmpty(text)){
			getChild("text").setText(text);
			getChild("text").setDisplay(true);
		}else{
			removeClass("fg-button-icon-left");
			removeClass("fg-button-icon-right");
			addClass("fg-button-icon-solo");
			getChild("text").setDisplay(false);
		}
		return this;
	}
	
	public String getLabel(){
		return getChild("text").getText();
	}
	
	public void setLabel(String text){
		setText(text);
	}
	
	
	/**
	 * 1 . left
	 * 2 . right
	 * @param position
	 */
	public EXIconButton setIconPosition(int position){
		removeClass("fg-button-icon-left");
		removeClass("fg-button-icon-right");
		removeClass("fg-button-icon-solo");
		
		if(position == 1){
			addClass("fg-button-icon-left");
		}else{
			addClass("fg-button-icon-right");
		}
		return this;
	}

}
