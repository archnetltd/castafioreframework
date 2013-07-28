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
 package org.castafiore.sms.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.ex.toolbar.EXToolBar;
import org.castafiore.utils.EventUtil;

public class EXSMSModule extends EXBorderLayoutContainer {
	
	public EXSMSModule(){
		EXToolBar toolbar = new EXToolBar("toolbar");
		toolbar.addItem(new EXIconButton("add", "Add Instance", Icons.ICON_PLUSTHICK).setIconPosition(1));
		
		addChild(toolbar, TOP);
		getDescendentByName("add").setAttribute("method", "showAddNew").setAttribute("ancestor", getClass().getName()).addEvent(EventUtil.GENERIC_FORM_METHOD_EVENT, Event.CLICK);
		EXTable table = new EXTable("table", new ListSMSInstanceTableModel());
		table.setWidth(Dimension.parse("100%"));
		table.setCellRenderer(new ListSMSCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("pTable", table);
		pTable.setWidth(Dimension.parse("100%"));
		addChild(pTable, CENTER);
	}
	
	
	public void showAddNew(Container caller){
		
		EXCreateSMSInstance exInstance = new EXCreateSMSInstance("");
		addPopup(exInstance);
		
	}

}
