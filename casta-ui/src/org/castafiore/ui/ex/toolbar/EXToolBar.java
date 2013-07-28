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

import org.castafiore.ui.ex.Corners;
import org.castafiore.ui.ex.EXWidget;
import org.castafiore.ui.ex.ViewModel;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class EXToolBar extends EXWidget implements ToolBar  {

	private ViewModel<ToolBarItem> model ;
	
	public EXToolBar(String name, ViewModel<ToolBarItem> model) {
		this(name);
		setModel(model);
	}

	
	public EXToolBar(String name) {
		super(name, "div");
		setStyleClass("fg-toolbar ui-widget-header ui-helper-clearfix");
		setCorners(Corners.ALL);
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
		button.setStyle("float", "left");
		addChild(button);
		return this;
	}
}
