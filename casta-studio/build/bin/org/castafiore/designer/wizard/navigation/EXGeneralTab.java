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
 package org.castafiore.designer.wizard.navigation;

import org.castafiore.ecm.ui.permission.PermissionButton;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButtonSet;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.toolbar.EXToolBar;

public class EXGeneralTab extends EXContainer {

	public EXGeneralTab(String name) {
		super(name, "div");
		setWidth(Dimension.parse("100%"));
		EXToolBar tb = new EXToolBar("acTB");
		tb.setStyle("padding", "1px 0 1px 5px");
		EXButtonSet bs = new EXButtonSet("bs");
		bs.addItem(new EXIconButton("newNode", "Add Node"));
		bs.addItem(new EXIconButton("deleteNode", "Delete Node"));
		bs.addItem(new EXIconButton("moveUp", "Move up"));
		bs.addItem(new EXIconButton("moveUp", "Move down"));
		tb.addItem(bs);
		bs.setTouching(true);
		
		addChild(tb);
		
		
		
		EXDynaformPanel panel = new EXDynaformPanel("", "");
		panel.addField("Name :", new EXInput("name"));
		panel.addField("Label", new EXInput("label"));
		addChild(panel);
		
		panel.setShowCloseButton(false);
		panel.setShowHeader(false);
		panel.setShowFooter(false);
		panel.setDraggable(false);
		
		addChild(new PermissionButton("Read","Read permissions"));
		addChild(new PermissionButton("Write", "Write permissions"));
		getChild("Read").setStyle("float", "left");
		getChild("Read").setStyle("width", "160px");
		getChild("Read").setStyle("padding", "10px 0 0 10px");
		getChild("Read").setStyle("width", "75px");
		
		getChild("Write").setStyle("float", "left");
		getChild("Write").setStyle("width", "160px");
		getChild("Write").setStyle("padding", "10px 0 0 10px");
		
		
	}
	
	
	
	
	

}
