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

import org.castafiore.ecm.ui.fileexplorer.icon.EXIcon;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;

public class FilesViewModel implements ViewModel<EXIcon> {
	
	private Directory currentDir;
	FileIterator children = null;
	
	public FilesViewModel(String dir) {
		super();
		try
		{
			RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
			if(dir == null)
			{
				
				this.currentDir = service.getDirectory("root",Util.getRemoteUser());
				
			}
			else
			{
				this.currentDir = (Directory)service.getFile(dir,Util.getRemoteUser());
			}
			currentDir.refresh();
			children = currentDir.getFiles();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		
	}


	public FilesViewModel(Directory currentDir) {
		super();
		try
		{
			if(currentDir == null)
			{
				RepositoryService service = BaseSpringUtil.getBeanOfType(RepositoryService.class);
				this.currentDir = service.getDirectory("root",Util.getRemoteUser());
			}
			else
			{
				this.currentDir = currentDir;
			}
			children = currentDir.getFiles();
		}
		catch(Exception e)
		{
			throw new RuntimeException(e);
		}
		
	}

	public EXIcon getComponentAt(int index, Container parent) {
		//return new ICon("", children.get(index));
		return new EXIcon("", currentDir.getFiles().get(index));
	}

	public int size() {
		if(currentDir == null)
			return 0;
		return new Long(children.count()).intValue();
	}
	
	public int bufferSize() {
		return size();
	}


	
}
