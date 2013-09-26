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

public class EXVLayout extends EXContainer implements LayoutContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXVLayout() {
		super("VLayout", "ul");
		setStyle("list-style", "none");
		setStyle("padding", "0").setStyle("margin", "0");
	}

	public EXVLayout(String name) {
		super(name, "ul");

	}

	public int convertLayoutData(String layoutData) {
		return Integer.parseInt(layoutData);
	}

	protected Container getCell(String layoutData) {
		int iLayoutData = convertLayoutData(layoutData);
		if (iLayoutData == getChildren().size()) {
			EXContainer li = new EXContainer("", "li");
			addChild(li);
		}

		return getChildByIndex(iLayoutData);
	}

	public void addChild(Container child, String layoutData) {
		getCell(layoutData).addChild(child);

	}

	public Container getChild(String name, String layoutData) {
		return getCell(layoutData).getChild(name);
	}

	public List<Container> getChildren(String layoutData) {
		return getCell(layoutData).getChildren();
	}

	public Container getDescendentById(String id, String layoutData) {
		return getCell(layoutData).getDescendentById(id);
	}

	public Container getDescendentByName(String name, String layoutData) {
		return getCell(layoutData).getDescendentByName(name);
	}

	public Container getDescendentOfType(Class<? extends Container> type,
			String layoutData) {
		return getCell(layoutData).getDescendentOfType(type);
	}

	public List<DroppableSection> getSections() {
		List<DroppableSection> result = new ArrayList<DroppableSection>();
		for (int i = 0; i < getChildren().size(); i++) {
			Container item = getCell(i + "").getChildByIndex(0);
			DroppableSection section = new DroppableSection(item.getId(),
					item.getName(), i);
			result.add(section);
		}
		return result;
	}

	public void validateLayoutData(String layoutData)
			throws InvalidLayoutDataException {
		try {
			Integer.parseInt(layoutData.toString());
		} catch (Exception e) {
			throw new InvalidLayoutDataException(e);
		}

	}

	public Container getContainer(String layoutData) {
		return getCell(layoutData);
	}

	public void removeChildFromLayout(String id) {
		getDescendentById(id).getParent().remove();
		setRendered(false);
	}

}
