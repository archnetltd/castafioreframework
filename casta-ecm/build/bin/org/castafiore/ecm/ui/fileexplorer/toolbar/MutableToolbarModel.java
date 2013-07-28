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

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

public class MutableToolbarModel implements ViewModel<ToolBarItem>{

	List<ToolBarItem> items = new ArrayList<ToolBarItem>();
	
	public int bufferSize() {
		return size();
	}

	public ToolBarItem getComponentAt(int index, Container parent) {
		return items.get(index);
	}

	public int size() {
		return items.size();
	}

	public List<ToolBarItem> getItems() {
		return items;
	}

	public void setItems(List<ToolBarItem> items) {
		this.items = items;
	}
	
	
	public MutableToolbarModel addItem(ToolBarItem item)
	{
		items.add(item);
		return this;
	}

}
