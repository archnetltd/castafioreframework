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

import org.castafiore.ui.ex.tree.TreeNode;

public class SampleMenuTreeNode implements TreeNode<EXMenuItem> {

	private int depth = 0;
	private int breadth = 0;
	private TreeNode<EXMenuItem> parent = null;
	
	public SampleMenuTreeNode(){
		this(0,0,null);
	}
	
	private SampleMenuTreeNode(int depth, int breadth,
			TreeNode<EXMenuItem> parent) {
		super();
		this.depth = depth;
		this.breadth = breadth;
		this.parent = parent;
	}

	public int childrenCount() {
		if(depth < 3){
			return 4;
		}else{
			return 0;
		}
	}


	public EXMenuItem getComponent() {
		EXMenuItem item = new EXMenuItem("", "Depth, Breadth : " + depth + "," + breadth);
		return item;
	}


	public TreeNode<EXMenuItem> getNodeAt(int index) {
		return new SampleMenuTreeNode(depth + 1, index, this);
	}


	public TreeNode<EXMenuItem> getParent() {
		return parent;
	}


	public boolean isLeaf() {
		return childrenCount() == 0;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
