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

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.LayoutContainer;

public class EXHLayout extends EXVLayout implements LayoutContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXHLayout() {
		super("EXRowLayoutContainer");
	}

	public EXHLayout(String name) {
		super(name);
	}

	public Container getCell(String layoutData) {
		int iLayoutData = convertLayoutData(layoutData);
		if (iLayoutData == getChildren().size()) {
			EXContainer li = new EXContainer("", "li");
			li.setStyle("float", "left");
			li.setStyle("display", "inline");
			addChild(li);
		}

		return getChildByIndex(iLayoutData);
	}

}
