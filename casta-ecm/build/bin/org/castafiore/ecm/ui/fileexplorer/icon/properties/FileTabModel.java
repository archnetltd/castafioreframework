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

import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.tabbedpane.TabModel;
import org.castafiore.ui.tabbedpane.TabPanel;
import org.castafiore.wfs.types.File;

public class FileTabModel implements TabModel {
	
	private static String[] labels = new String[]{"Basic", "Security"};
	
	private File file;
	
	

	public FileTabModel(File file) {
		super();
		this.file = file;
	}

	public int getSelectedTab() {
		
		return 0;
	}

	public EXContainer getTabContentAt(TabPanel pane, int index) {
		if(index == 0)
		{
			EXDynaformPanel panel = new EXDynaformPanel("basisss","Basic properties", new BasicFormModel(file));
			panel.setDraggable(false);
			panel.setShowHeader(false);
			panel.setWidth(Dimension.parse("486px"));
			return panel;
		}
		else
		{
			EXDynaformPanel panel = new PermissionTab("permissionTab", file);
			panel.setDraggable(false);
			panel.setShowHeader(false);
			panel.setWidth(Dimension.parse("486px"));
			return panel;
		}
	}

	public String getTabLabelAt(TabPanel pane, int index, boolean selected) {
		return labels[index];
	}

	public void releaseTab(EXContainer tab) {
		// TODO Auto-generated method stub
		
	}

	public void selectTab(EXContainer tab) {
		// TODO Auto-generated method stub
		
	}

	public int size() {
		return labels.length;
	}

}
