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
 package org.castafiore.designer.designable.table;

import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.designable.data.AbstractDataDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.form.table.EXPagineableTable;
import org.castafiore.ui.ex.form.table.EXTable;

public class EXTableDesignableFactory extends AbstractDataDesignableFactory{

	public EXTableDesignableFactory() {
		super("EXTableDesignableFactory");
		setText("Table");
	}

	@Override
	public Container getInstance() {
		
		EXTable table = new EXTable("Table", new DefaultDesignableTableModel());
		table.setWidth(Dimension.parse("100%"));
		table.setColumnModel(new JSONColumnDecorator());
		table.setCellRenderer(new JSONCellRenderer());
		EXPagineableTable pTable = new EXPagineableTable("pTable", table);
		pTable.setWidth(Dimension.parse("100%"));
		
		return pTable;
	}

	public String getUniqueId() {
		return "data:table";
	}

	@Override
	public String[] getRequiredAttributes() {
		return null;
	}
	
	@Override
	public Map<String, ConfigForm> getAdvancedConfigs() {
		Map<String, ConfigForm> forms = new ListOrderedMap();
		forms.put("Model", new EXTableConfigForm());
		forms.put("DataSource", new EXTableDataSourceConfigForm());
		return forms;

	}
}
