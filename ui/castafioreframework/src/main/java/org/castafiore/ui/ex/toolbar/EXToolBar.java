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

package org.castafiore.ui.ex.toolbar;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXToolBar extends EXContainer implements ToolBar  {

	private ViewModel<ToolBarItem> model ;
	
	public EXToolBar(String name, ViewModel<ToolBarItem> model) {
		this(name);
		setStyle("padding", "4px").setStyle("display", "inline-block");
		addClass("ui-widget-header").addClass("ui-corner-all");
		setModel(model);
		
	}

	
	public EXToolBar(String name) {
		super(name, "div");
		setStyle("padding", "4px").setStyle("display", "inline-block");
		addClass("ui-widget-header").addClass("ui-corner-all");
	}
	
	public ViewModel<ToolBarItem> getModel() {
		return model;
	}

	public void setModel(ViewModel<ToolBarItem> model) {
		this.model = model;
		refresh();
	}
	
	
	@Override
	public void refresh() {
		if(model != null)
		{
			this.getChildren().clear();
			this.setRendered(false);
			int size = model.size();
			for(int i = 0; i < size; i++){
				addItem(model.getComponentAt(i, this));
			}
		}
	}


	public ToolBar addItem(ToolBarItem button) {
		button.setStyle("display", "inline-block").setStyle("margin-right", "7px");
		addChild(button);
		return this;
	}
}
