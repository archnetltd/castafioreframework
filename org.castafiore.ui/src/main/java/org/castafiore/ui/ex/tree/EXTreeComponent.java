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
package org.castafiore.ui.ex.tree;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.tree.TreeComponent;

public class EXTreeComponent extends EXContainer implements TreeComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EXTreeComponent(String name, String label, String iconUrl) {
		super(name, "div");
		addClass("casta-node");
		addClass("ui-corner-all");
		setStyle("padding", "2px 10px 2px 2px");
		setStyle("cursor", "pointer");
		EXContainer img = new EXContainer("img", "img");
		img.setAttribute("src", iconUrl);
		img.setStyle("vertical-align", "middle");
		img.setStyle("position", "relative");
		img.setStyle("bottom", "2px");
		addChild(img);
		EXContainer span = new EXContainer("label", "span");
		span.setStyle("padding-left", "3px");
		span.setText(label);
		addChild(span);

		addEvent(new Event() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void ClientAction(ClientProxy container) {
				container.mergeCommand(new ClientProxy(".casta-node")
						.removeClass("ui-state-default"));
				container.addClass("ui-state-default");

			}

			public boolean ServerAction(Container container,
					Map<String, String> request) throws UIException {
				return false;
			}

			public void Success(ClientProxy container,
					Map<String, String> request) throws UIException {

			}

		}, Event.CLICK);
	}

	public void setIconUrl(String icon) {
		getChild("img").setAttribute("src", icon);
	}

	public void setLabel(String label) {
		getChild("label").setText(label);
	}

	public String getLabel() {
		return getChild("label").getText(false);
	}

	public static String getIcon(boolean leaf) {
		String icon = "http://www.extjs.com/deploy/dev/resources/images/default/tree/folder.gif";
		if (leaf) {
			icon = "http://www.extjs.com/deploy/dev/resources/images/default/tree/leaf.gif";
		}
		return icon;
	}

}
