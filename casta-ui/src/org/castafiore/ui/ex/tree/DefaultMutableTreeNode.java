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

import org.castafiore.ui.ex.EXContainer;
/**
 * 
 * 
 * @author Kureem Rossaye<br>
 *          kureem@gmail.com
 * Oct 22, 2008
 */
public class DefaultMutableTreeNode implements TreeNode {

	
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	private TreeNode parent;
	
	private EXContainer userObject = null;
	
	private boolean leaf;
	
	
	
	public DefaultMutableTreeNode(EXContainer userContainer,  boolean isLeaf)
	{
		this.userObject = userContainer;
		this.leaf = isLeaf;
	}
	
	public void addChild(TreeNode node)
	{
		if(node instanceof DefaultMutableTreeNode)
			((DefaultMutableTreeNode)node).setParent(this); 
		children.add(node);
	}
	
	public int childrenCount() {
		if(children != null)
		{
			return this.children.size();
		}
		return 0;
	}

	public EXContainer getComponent() {
	
		return userObject;
	}

	public TreeNode getNodeAt(int index) {
		try
		{
			return children.get(index);
		}
		catch(Exception e)
		{
			
		}
		return null;
	}

	

	

	
	
	
	

	public boolean isLeaf() {
		return leaf;
	}

	public TreeNode getParent() {
		return parent;
	}

	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	

	

}
