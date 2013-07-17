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
 package org.castafiore.ui.ex.panel;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.button.EXButton;

public class EXPanelButtonContainer extends EXContainer {

	public EXPanelButtonContainer(String name, List<EXButton> buttons) {
		super(name, "div");
		setStyleClass("x-panel-btns-ct");
		EXContainer div1 = new EXContainer("", "div");
		div1.setStyleClass("x-panel-btns x-panel-btns-center");
		addChild(div1);
		
		EXGrid grid = new EXGrid("grid", buttons.size(), 1);
		int count = 0;
		for(EXButton button : buttons)
		{
			grid.getCell(count, 0).setStyleClass("x-panel-btn-td");
			grid.getCell(count, 0).addChild(button);
			count++;
		}
		
		div1.addChild(grid);
		
	}
	
	public EXPanelButtonContainer(String name){
		this(name, new ArrayList<EXButton>(0));
	}
	
	public void addButton(EXButton button)
	{
		EXContainer td = new EXContainer("", "td");
		td.setStyleClass("x-panel-btn-td");
		td.addChild(button);
		getDescendentOfType(EXGrid.class).getChildByIndex(0).addChild(td);
	}
	
	public List<EXButton> getButtons(){
		List<EXButton> result = new ArrayList<EXButton>();
		List<Container> tds = getDescendentOfType(EXGrid.class).getChildByIndex(0).getChildren();
		for(Container td : tds){
			result.add((EXButton)td.getChildByIndex(0));
		}
		return result;
		
	}

}
