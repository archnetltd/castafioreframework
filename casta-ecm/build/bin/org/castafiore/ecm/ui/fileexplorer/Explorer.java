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



import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

/**
 * Interface that represents an Explorer
 * 
 * User can implement this interface to create his own file Explorer<br>
 * @see implmentations of this fileExplorer
 * 
 * 
 * @author Kureem Rossaye
 *
 */
public interface Explorer extends PopupContainer  {

	
	public void OnFileSelected(File file)throws UIException;
	
	public String getCurrentAddress();
	
	public ExplorerView getView();
	
	public void setView(ExplorerView view);
	
	public void setView(ExplorerView view, Directory address);
	
	public void setSelectedFiles(String[] paths);
	
	public String[] getSelectedFiles();
	
	public void setClipBoard(Object o);
	
	public Object getClipBoard();
	
	
	
	


}
