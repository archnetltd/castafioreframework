/*
 * Copyright (C) 2007-2008 Castafiore
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
import org.castafiore.ui.tree.TreeNode;

/**
 * Component or Container used to display a node of the {@link EXTree} component
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class EXNode extends EXContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeNode<Container> node = null;

	/**
	 * Static event to open or close a node of a tree
	 */
	public static Event OPEN_CLOSE_EVENT = new Event() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {

		}

		public void ClientAction(ClientProxy container) {

			container.makeServerRequest(this);

		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			if (container.getParent().getStyleClass()
					.equalsIgnoreCase("TLMclosed")) {
				((EXNode) container.getParent()).open();
			} else {
				((EXNode) container.getParent()).close();
			}
			return true;
		}

	};

	/**
	 * Constructs
	 * 
	 * @param name
	 * @param node
	 */
	public EXNode(String name, TreeNode<Container> node) {
		super(name, "li");
		Container userObject = node.getComponent();
		if (node.isLeaf())
			setStyleClass("doc");
		else {
			setStyleClass("TLMclosed");
			if (userObject != null) {
				userObject.addEvent(OPEN_CLOSE_EVENT, Event.CLICK);
			}
		}

		if (userObject != null) {
			addChild(userObject);
		}
		this.node = node;
		setStyle("width", "0px");
		refresh();

	}

	public void open() {
		if (!node.isLeaf()) {
			if (getDescendentByName("ul") == null) {
				EXContainer ul = new EXContainer("ul", "ul");
				for (int i = 0; i < node.childrenCount(); i++) {
					TreeNode<Container> child = node.getNodeAt(i);
					EXNode childNode = new EXNode("", child);
					ul.addChild(childNode);

				}
				addChild(ul);
			}
			setStyleClass("TLMopen");

		}
	}

	public void close() {
		if (!node.isLeaf()) {
			setStyleClass("TLMclosed");
		}
	}

	public TreeNode<Container> getNode() {
		return node;
	}

	public void setNode(TreeNode<Container> node) {

		this.node = node;
		refresh();
	}

}
