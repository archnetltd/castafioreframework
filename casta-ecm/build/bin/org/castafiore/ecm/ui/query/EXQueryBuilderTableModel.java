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
 package org.castafiore.ecm.ui.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.BaseSpringUtil;
import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;

public class EXQueryBuilderTableModel implements TableModel {

	private final static String[] LABELS = new String[]{"Name", "Type", "Order By", "Operator", "Values"};
	
	private SessionFactory sessionFactory;
	
	private ClassMetadata metadata ;
	
	
	private List<String> propertyNames = new ArrayList<String>();
	
	
	
	public EXQueryBuilderTableModel(String clazz) {
		super();
		this.sessionFactory = BaseSpringUtil.getBeanOfType(SessionFactory.class);
		this.metadata = sessionFactory.getClassMetadata(clazz);
		String[] propertyNames = metadata.getPropertyNames();
		Arrays.sort(propertyNames);
		
		for(String prop : propertyNames){
			
			Type type = metadata.getPropertyType(prop);
			if(!type.isAssociationType() && !type.isCollectionType() && !type.isComponentType()){
				if(!prop.equals("editPermissions") && !prop.equals("readPermissions") && !prop.equals("clazz"))
					this.propertyNames.add(prop);
			}
			//metadata.getPropertyType(prop)
		}
	}

	
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	
	public int getColumnCount() {
		return LABELS.length;
	}


	public String getColumnNameAt(int index) {
		return LABELS[index];
	}


	public int getRowCount() {
		return propertyNames.size();
	}


	public int getRowsPerPage() {
		return getRowCount();
	}


	public Object getValueAt(int col, int row, int page) {
		if(col == 0)
			return propertyNames.get(row);
		else {
			return metadata.getPropertyType(propertyNames.get(row));
		}
	}


	public boolean isCellEditable(int rowIndex, int columnIndex) {
		
		return false;
	}

}
