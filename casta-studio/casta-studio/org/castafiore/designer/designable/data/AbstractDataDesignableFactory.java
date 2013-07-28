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
 package org.castafiore.designer.designable.data;

import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.config.ConfigForm;
import org.castafiore.designer.designable.table.EXTableDataSourceConfigForm;
import org.castafiore.designer.designable.table.JSONTableModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.form.table.Table;

public abstract class AbstractDataDesignableFactory extends AbstractDesignableFactory {

	public AbstractDataDesignableFactory(String name) {
		super(name);
	}

	@Override
	public String getCategory() {
		return "Data";
	}


	@Override
	public Map<String, ConfigForm> getAdvancedConfigs() {
		Map<String, ConfigForm> forms = new ListOrderedMap();
		forms.put("DataSource", new EXTableDataSourceConfigForm());
		return forms;

	}
	
	public void applyAttribute(Container c, String attributeName, String attributeValue){
		//c.setAttribute(attributeName, attributeValue);
		try{
			if(attributeName.equalsIgnoreCase("datasource")){
				c.getDescendentOfType(Table.class).setModel(new JSONTableModel(c));
				c.refresh();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
