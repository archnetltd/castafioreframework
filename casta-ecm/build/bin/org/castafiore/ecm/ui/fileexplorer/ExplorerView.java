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
 package org.castafiore.ecm.ui.fileexplorer;




import java.util.List;

import org.castafiore.ecm.ui.fileexplorer.icon.ICon;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public interface ExplorerView extends Container {
	
	
	public int refreshView(String address)throws UIException;
	
	//public void refreshView(Directory address)throws UIException;
	
	public ViewModel<ToolBarItem> getActionToolbarModel();
	
	public String getIdentifierString();
	
	public ICon getIcon(String filePath);
	
	public void addItem(File file);
	
	
	public void showFiles(List<File> files);
	
	
	
	
	
	

}
