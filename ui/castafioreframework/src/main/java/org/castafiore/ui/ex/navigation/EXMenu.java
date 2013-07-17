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
package org.castafiore.ui.ex.navigation;

import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.TreeNode;

/**
 * A menu controlled by the {@link TreeNode}
 * @author arossaye
 *
 */
public class EXMenu extends EXContainer {
	private TreeNode<EXMenuItem> node;

	/**
	 * Creates a menu with the specified name and populated with the specified {@link TreeNode}
	 * @param name The name of the menu
	 * @param node The {@link TreeNode} to populate the menu
	 */
	public EXMenu(String name, TreeNode<EXMenuItem> node) {
		super(name, "ul");

		setModel(node);
		setStyle("width", "200px").setStyle("position", "absolute");

	}

	/**
	 * Constructs an empty menu
	 * @param name
	 */
	public EXMenu(String name){
		this(name,null);
	}
	
	/**
	 * Re creates the menu with the specified {@link TreeNode}
	 * @param node The {@link TreeNode}
	 * @return The newly created {@link EXMenu}
	 */
	public EXMenu setModel(TreeNode<EXMenuItem> node) {
		this.node = node;
		this.getChildren().clear();
		this.setRendered(false);
		if (node != null) {
			for (int i = 0; i < node.childrenCount(); i++) {

				TreeNode<EXMenuItem> n = node.getNodeAt(i);
				addChild(new EXContainer("", "li").addChild(n.getComponent()));
				if (!n.isLeaf()) {
					EXMenu menu = new EXMenu("", n);
					addChild(menu);
				}
			}
		}
		return this;
	}

	/**
	 * The {@link TreeNode} of the menu
	 * @return
	 */
	public TreeNode<EXMenuItem> getModel() {
		return node;
	}

	@Override
	public void onReady(ClientProxy proxy) {

		super.onReady(proxy);
		proxy.addMethod("menu");
	}

}
