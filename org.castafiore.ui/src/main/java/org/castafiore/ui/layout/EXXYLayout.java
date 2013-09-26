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
package org.castafiore.ui.layout;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.InvalidLayoutDataException;
import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.LayoutContainer;

public class EXXYLayout extends EXContainer implements LayoutContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXXYLayout(String name, String tagName) {
		super(name, tagName);
	}

	public EXXYLayout(String name) {
		super(name, "div");
	}

	public EXXYLayout() {
		super("XYLayout", "div");
	}

	public void addChild(Container child, String layoutData) {
		addChild(child);

	}

	public Container getChild(String name, String layoutData) {

		return getChild(name);
	}

	public List<Container> getChildren(String layoutData) {
		return getChildren();
	}

	public Container getContainer(String layoutData) {
		return getDescendentById(layoutData);
	}

	public Container getDescendentById(String id, String layoutData) {
		return getDescendentById(id);
	}

	public Container getDescendentByName(String name, String layoutData) {
		return getDescendentByName(name);
	}

	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getDescendentOfType(type);
	}

	public List<DroppableSection> getSections() {
		List<Container> children = getChildren();

		List<DroppableSection> sections = new ArrayList<DroppableSection>(
				children.size());

		for (Container c : children) {
			DroppableSection ds = new DroppableSection(c.getId(), c.getName(),
					c.getName());
			sections.add(ds);
		}
		return sections;

	}

	public void removeChildFromLayout(String id) {
		getDescendentById(id).remove();
		setRendered(false);

	}

	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {

	}

}
