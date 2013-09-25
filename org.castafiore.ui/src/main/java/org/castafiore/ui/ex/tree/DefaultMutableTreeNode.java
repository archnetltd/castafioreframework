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

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.Container;
import org.castafiore.ui.tree.TreeNode;

/**
 * Default convenient implementation of a {@link TreeNode} that allows addition
 * and removal of children node.
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public class DefaultMutableTreeNode implements TreeNode<Container> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<TreeNode<Container>> children = new ArrayList<TreeNode<Container>>();

	private TreeNode<Container> parent;

	private Container userObject = null;

	private boolean leaf;

	/**
	 * Constructs a {@link TreeNode} with the specified userContainer to hold
	 * and stating if this is going to be a leaf
	 * 
	 * @param userContainer
	 *            The user container to hold
	 * @param isLeaf
	 *            if this is a leaf node or not
	 */
	public DefaultMutableTreeNode(Container userContainer, boolean isLeaf) {
		this.userObject = userContainer;
		this.leaf = isLeaf;
	}

	/**
	 * Adds a child node to this node
	 * 
	 * @param node
	 */
	public void addChild(TreeNode<Container> node) {
		if (node instanceof DefaultMutableTreeNode)
			((DefaultMutableTreeNode) node).setParent(this);
		children.add(node);
	}

	/**
	 * Returns the number of children for this node
	 */
	public int childrenCount() {
		if (children != null) {
			return this.children.size();
		}
		return 0;
	}

	/**
	 * Returns the user container of this node
	 */
	public Container getComponent() {

		return userObject;
	}

	/**
	 * Returns a child node for the specified index. If cannot or index is
	 * greater or equal than children or is negative, returns null.
	 */
	public TreeNode<Container> getNodeAt(int index) {
		try {
			return children.get(index);
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * States if this a leaf or not. Does not count the number of children. Only
	 * returns the leaf attribute
	 */
	public boolean isLeaf() {
		return leaf;
	}

	/**
	 * Returns the parent node
	 */
	public TreeNode<Container> getParent() {
		return parent;
	}

	/**
	 * Sets the parent node of this node
	 * 
	 * @param parent
	 */
	public void setParent(TreeNode<Container> parent) {
		this.parent = parent;
	}

	public void refresh() {

	}

}
