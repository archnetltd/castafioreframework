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
 package org.castafiore.ecm.ui.fileexplorer.icon.properties;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.dynaform.DynaForm;
import org.castafiore.ui.ex.dynaform.FormModel;
import org.castafiore.ui.ex.form.EXLabel;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class BasicFormModel implements FormModel {

	private File file;

	private static String[] labels = new String[]{"Name :", "Type :", "Contents :", "Location :", "Modified :"};
	
	public BasicFormModel(File file) {
		super();
		this.file = file;
	}
	
	public int actionSize() {
		
		return 0;
	}

	public EXButton getActionAt(int index, DynaForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	public StatefullComponent getFieldAt(int index, DynaForm form) {
		if(index == 0)
			return new EXLabel("", file.getName());
		else if (index == 1)
		{
			String[] parts = StringUtil.split(file.getClazz(), ".");
			String type = parts[parts.length-1];
			return new EXLabel("", type);
		}
		else if(index == 2)
		{
			String cts = "0 files";
			if(file.isDirectory())
			{
				 cts =  ((Directory)file).getFiles().count() + " files";
			}
			return new EXLabel("", cts);
		}
		else if(index == 3)
		{
			return new EXLabel("", file.getAbsolutePath());
		}
		else {
			return new EXLabel("", "lastmodified");
		}
		
	}

	public String getLabelAt(int index, DynaForm form) {
		return labels[index];
	}

	public int size() {
		return labels.length;
	}

	

	

}
