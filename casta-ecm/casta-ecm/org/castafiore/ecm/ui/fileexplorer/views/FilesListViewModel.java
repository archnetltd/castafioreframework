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
 package org.castafiore.ecm.ui.fileexplorer.views;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.form.table.TableModel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class FilesListViewModel implements TableModel {

	final String[] columns = new String[]{"Name", "Size", "Type", "Date Modified"};
	
	private Directory currentDir =null;
	
	final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	
	public FilesListViewModel (String dir)
	{
		try
		{
			RepositoryService service = SpringUtil.getRepositoryService();
			if(!StringUtil.isNotEmpty(dir))
			{
				
				this.currentDir = service.getDirectory("root",Util.getRemoteUser());
			}
			else
			{
				this.currentDir = (Directory)service.getFile(dir,Util.getRemoteUser());
			}
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
	}
	
	
	public int getColumnCount() {
		return 4;
	}

	public String getColumnNameAt(int index) {
		return columns[index];
	}

	public int getRowCount() {
		return currentDir.getFiles().count();
	}

	public int getRowsPerPage() {
		return 10;
	}

	public Object getValueAt(int col, int row, int page) {
		File f = currentDir.getFiles().get(row);
		
		
		if(col == 0)
			return f.getName();
		else if(col == 1)
		{
			if(!f.isDirectory())
			{
				return f.length();
			}
			else
			{
				return "";
			}
		}
		else if(col == 2)
		{
			if(f.isDirectory())
			{
				return "Folder";
			}
			else
			{
				return "File";
			}
		}
		else 
		{
			return format.format(new Date(f.lastModified()));
		}
	}

	public void setValueAt(int col, int row, int page, Object newValue) {
		// TODO Auto-generated method stub
		
	}

	
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}
	
}
