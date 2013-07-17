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
 package org.castafiore.ui.ex;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.button.Button;

public class MutableViewModel implements ViewModel<Container> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Container> items = new ArrayList<Container>();

	public int bufferSize() {
		return this.size();
	}

	public Container getComponentAt(int index, Container parent) {
		return items.get(index);
	}

	public int size() {
		return items.size();
	}
	
	public MutableViewModel addItem(Button item)
	{
		items.add(item);
		return this;
	}

	public List<Container> getItems() {
		return items;
	}

	public void setItems(List<Container> items) {
		this.items = items;
	}
	
	

}
