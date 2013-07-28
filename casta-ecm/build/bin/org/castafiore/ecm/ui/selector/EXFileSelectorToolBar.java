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
 package org.castafiore.ecm.ui.selector;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.toolbar.ToolBar;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.utils.ComponentUtil;

public class EXFileSelectorToolBar extends EXContainer implements ToolBar{

	public EXFileSelectorToolBar(String name) {
		super(name, "div");
		addClass("EXMiniFileExplorerBar");
		addClass("ui-widget-header");
		addClass("ui-corner-top");
		setStyle("margin-bottom", "1px");
	}

	public ToolBar addItem(ToolBarItem button) {
		addChild(button);
		return this;
	}
	
	
	public class EXInputItem extends EXInput implements ToolBarItem{

		public EXInputItem(String name, String value) {
			super(name, value);
		}

		public EXInputItem(String name) {
			super(name);
			addClass("EXMinifFileExplorerTBarItem YInput");
			setStyle("font-size", "12px");
			
		}
	}
	
	
	public static class EXButtonToolbarItem extends EXContainer implements ToolBarItem{

		public EXButtonToolbarItem(String name, String icon) {
			super(name, "div");
			addClass("EXMinifFileExplorerTBarItem");
			addClass("EXMiniFileExplorerButton");
			addClass("ui-state-default");
			addClass("ui-corner-all");
			addChild(ComponentUtil.getContainer("icon", "span", null, "ui-icon " + icon));
		}
		
		public void setIcon(String icon){
			getChildByIndex(0).setStyleClass("ui-icon " + icon);
		}
		
	}
	
	
	public  static class EXSeperatorItem extends EXContainer implements ToolBarItem{

		public EXSeperatorItem() {
			super("", "div");
			setStyleClass("EXMinifFileExplorerTBarItem ui-widget-content ui-corner-all");
			setAttribute("style", "width: 2px;height: 16px;margin: 3px 3px 3px 0");
		}
		
		
	}
	

}
