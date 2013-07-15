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

import java.io.Serializable;

import org.castafiore.ui.Container;

/**
 * Interface representing a node to be used in a tree
 * 
 * @author Kureem Rossaye<br>
 *         kureem@gmail.com Oct 22, 2008
 */
public interface TreeNode<T extends Container> extends Serializable {

	/**
	 * Returns the number of children for this node
	 * 
	 * @return
	 */
	int childrenCount();

	/**
	 * The node for the specified index
	 * 
	 * @param index
	 * @return
	 */
	public TreeNode<T> getNodeAt(int index);

	/**
	 * The component it holds
	 * 
	 * @return
	 */
	public T getComponent();

	/**
	 * States if this node is a leaf node or not
	 * 
	 * @return
	 */
	public boolean isLeaf();

	/**
	 * 
	 * @return The parent of this node. Null if root node
	 */
	public TreeNode<T> getParent();

	/**
	 * Refreshes and re-arranges the nodes if necessary
	 */
	public void refresh();

}
