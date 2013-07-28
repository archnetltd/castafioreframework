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
 package org.castafiore.designer.toolbar.menu;

import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.ui.menu.EXMenuItem;

public class FileMenuTreeNode implements TreeNode<EXMenuItem> {

	private String[] labels = new String[]{"New Page", "Open Page", "Save Page"};
	
	private TreeNode<EXMenuItem> parent;
	
	private String name;
	
	
	
	public FileMenuTreeNode(TreeNode<EXMenuItem> parent, String labels[], String name) {
		super();
		this.parent = parent;
		this.labels = labels;
		this.name = name;
	}


	public int childrenCount() {
		return labels.length;
	}


	public EXMenuItem getComponent() {
		return new EXMenuItem(name,name);
	}

	
	public TreeNode<EXMenuItem> getNodeAt(int index) {
		return new LeafNode(labels[index], labels[index], this);
	}

	
	public TreeNode<EXMenuItem> getParent() {
		return parent;
	}

	
	public boolean isLeaf() {
		return false;
	}

	
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
