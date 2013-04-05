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
	
	private boolean touching = true;

	public EXButtonSet(String name, ViewModel<ToolBarItem> buttons) {
		super(name, "div");
		addClass("ui-buttonset");
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
			if(touching){
				if(i == 0){
					c.setAttribute("corner", "left");
					c.removeClass("ui-corner-all");
					c.addClass("ui-corner-left");
					c.removeClass("ui-corner-right");
				}
				
				if(i == childrens-1){
					c.removeClass("ui-corner-all");
					c.addClass("ui-corner-right");
					c.removeClass("ui-corner-left");
					c.setAttribute("corner", "right");
				}
				
				if(i!= 0 && i != (childrens-1)){
					c.removeClass("ui-corner-all");
					c.removeClass("ui-corner-right");
					c.removeClass("ui-corner-left");
					c.setAttribute("corner", "none");
				}
			}else{
				c.addClass("ui-corner-all");
				c.setAttribute("corner", "all");
			}
		}

	}


	public EXButtonSet addItem(ToolBarItem b ){
		
		addChild(b);
		reAdjustTouching();
		return this;
	}
}
