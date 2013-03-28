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

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

public class EXButtonSet extends EXContainer implements ToolBarItem {
	
	private ViewModel<ToolBarItem> model; 
	
	private boolean touching = false;

	public EXButtonSet(String name, ViewModel<ToolBarItem> buttons) {
		super(name, "div");
		
		addClass("fg-buttonset");
		addClass("ui-helper-clearfix");
		setModel(buttons);
	}
	
	public EXButtonSet(String name) {
		this(name,null);
	}

	
	
	public ViewModel<ToolBarItem> getModel() {
		return model;
	}

	public void setModel(ViewModel<ToolBarItem> model) {
		
		
		this.model = model;
		this.getChildren().clear();
		this.setRendered(false);
		if(model != null){
			int size = model.size();
			for(int i = 0; i < size; i ++){
				ToolBarItem b = model.getComponentAt(i, this);
				addChild(b);
			}
			reAdjustTouching();
		}
	}
	
	
	
	public boolean isTouching() {
		return touching;
	}



	public void setTouching(boolean touching) {
		this.touching = touching;
		reAdjustTouching();
	}

	
	private void reAdjustTouching(){
		int childrens = getChildren().size();
		for(int i = 0; i < childrens; i ++){
			Container c = getChildren().get(i);
			c.removeClass("ui-corner-all");
			c.removeClass("ui-corner-left");
			c.removeClass("ui-corner-right");
			
			if(touching){
				if(i== 0){
					c.addClass("ui-corner-left");
				}else if( i== (childrens - 1)){
					c.addClass("ui-corner-right");
				}
				
			}else{
				c.addClass("ui-corner-all");
			}
		}
		
		if(touching){
			addClass("fg-buttonset-multi");
		}else{
			removeClass("fg-buttonset-multi");
		}
		
//		if(getChildren().size() >=2){
//			int last = getChildren().size() - 1;
//			getChildByIndex(0).removeClass("ui-corner-all");
//			getChildByIndex(0).removeClass("ui-corner-left");
//			getChildByIndex(0).removeClass("ui-corner-right");
//			getChildByIndex(last).removeClass("ui-corner-all");
//			getChildByIndex(last).removeClass("ui-corner-left");
//			getChildByIndex(last).removeClass("ui-corner-right");
//			if(touching){
//				getChildByIndex(0).addClass("ui-corner-left");
//				getChildByIndex(last).addClass("ui-corner-right");
//				addClass("fg-buttonset-multi");
//			}else{
//				getChildByIndex(0).addClass("ui-corner-all");
//				getChildByIndex(last).addClass("ui-corner-all");
//				removeClass("fg-buttonset-multi");
//			}
//		}
		
		
		
		//addClass(styleClass)
	}


	public EXButtonSet addItem(ToolBarItem b ){
		b.setStyle("float", "left");
		addChild(b);
		reAdjustTouching();
		return this;
	}
}
