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
 package org.castafiore.ecm.ui.fileexplorer.toolbar;

import org.castafiore.ecm.ui.fileexplorer.events.CreateDirEvent;
import org.castafiore.ecm.ui.fileexplorer.events.UploadFileEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.form.button.EXIconButton;
import org.castafiore.ui.ex.form.button.Icons;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

public class ActionToolbarModel implements ViewModel<ToolBarItem> {
	
	public ToolBarItem getComponentAt(int index, Container parent) {
		
		if(index == 0)
		{
			
			EXIconButton item = new EXIconButton("","Add folder", org.castafiore.ui.ex.form.button.Icons.ICON_FOLDER_OPEN);
			 
			item.addEvent(new CreateDirEvent(), Event.CLICK);
			
			return item;
		}
		else 
		{
			EXIconButton item = new EXIconButton("item-" + index,"Upload file", Icons.ICON_IMAGE);
			
			item.addEvent(new UploadFileEvent(), Event.CLICK);
			
			return item;
		}
	}

	public int size() {
		return 2;
	}

	public int bufferSize() {
		return size();
	}

}
