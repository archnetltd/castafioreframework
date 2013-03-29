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
package org.castafiore.ui.ex.contextmenu;

import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

/**
 * This class is the component that represents a context menu. Normally it is
 * the engine that uses this component.
 * 
 * @see ContextMenuAble
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Dec 16, 2008
 */
public class EXContextMenu extends EXContainer {

	private ContextMenuModel model = null;

	public EXContextMenu(String name, ContextMenuModel model) {
		super(name, "div");
		setStyleClass("contextMenu");
		setStyle("display", "none");
		this.model = model;
		refresh();

	}

	public void refresh() {
		this.getChildren().clear();

		EXContainer ul = new EXContainer("", "ul");

		addChild(ul);

		for (int i = 0; i < model.size(); i++) {
			// <li id="open"><img src="folder.png" /> Open</li>

			EXContainer li = new EXContainer("", "li");

			EXContainer img = new EXContainer("", "img");
			img.setAttribute("src", model.getIconSource(i));
			li.addChild(img);
			ul.addChild(li);
			Event event = model.getEventAt(i);
			if (event != null)
				li.addEvent(model.getEventAt(i), Event.CLICK);
			img.setText(model.getTitle(i));
		}

		this.setRendered(false);
	}

}
