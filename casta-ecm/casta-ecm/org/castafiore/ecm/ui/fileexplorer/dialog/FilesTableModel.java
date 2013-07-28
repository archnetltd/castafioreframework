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
 package org.castafiore.ecm.ui.fileexplorer.dialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.castafiore.wfs.types.File;
import org.castafiore.wfs.types.FileImpl;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class FilesTableModel implements TableModel {

	
	private List<File> temp = new ArrayList<File>();
	
	
	public FilesTableModel(String directory,Class<? extends File> filterType )
	{
		QueryParameters params = new QueryParameters().setEntity(filterType == null?FileImpl.class:filterType);
		params.addRestriction(Restrictions.eq("parent.absolutePath", directory)).addOrder(Order.desc("clazz")).addOrder(Order.asc("name"));
		temp = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
	}

	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex == 0)
			return File.class;
		else if(columnIndex == 1)
			return Calendar.class;
		else
			return String.class;
	}

	public int getColumnCount() {
		return 3;
	}

	public String getColumnNameAt(int index) {
		if(index == 0)		{
			return "Name";
		}
		else if(index == 1){
			return "Modified";
		}else{
			return "Selecter";
		}
	}

	public int getRowCount() {
		return temp.size();
	}

	public int getRowsPerPage() {
		return 25;
	}

	public Object getValueAt(int col, int rowr, int page) {
		
		
		int row = (page*getRowsPerPage()) + rowr;
		
		if(col == 0)
		{
			return temp.get(row);
		}
		else if(col ==1)
		{
			return temp.get(row).getLastModified();
		}else{
			Object result =  temp.get(row).getAbsolutePath();
			return result;
		}
		
		
	}

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
}
