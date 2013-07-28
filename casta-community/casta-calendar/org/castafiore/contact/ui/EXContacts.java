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
 package org.castafiore.contact.ui;

import java.util.List;

import org.castafiore.contact.Contact;
import org.castafiore.designer.designable.table.JSONTableModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.table.Table;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.ComponentUtil;

public class EXContacts extends EXContainer implements Table{
	
	private TableModel model;

	public EXContacts(String name,  String title, List<Contact> contacts) {
		super(name, "div");
		addClass("ui-widget");
		Container h3 = ComponentUtil.getContainer("title", "h3", title, "ui-state-default ui-panel-header");
		addChild(h3);
		addChild(ComponentUtil.getContainer("body", "div", null, "ui-widget-content"));
		getChild("body").addChild(new EXContactDepartment("", contacts));
	}

	public TableModel getModel() {
		return model;
	}

	public void setModel(TableModel model) {
		this.model = model;
		JSONTableModel tModel = (JSONTableModel)model;
		getChild("body").getChildren().clear();
		getChild("body").setRendered(false);
		List<Contact> contacts = tModel.getData();
		getChild("body").addChild(new EXContactDepartment("", contacts));
		
	}

	@Override
	public void changePage(int page) {
		setModel(model);
		
	}

	@Override
	public int getCurrentPage() {
		
		return 0;
	}

	@Override
	public int getPages() {
		
		return 1;
	}

}
