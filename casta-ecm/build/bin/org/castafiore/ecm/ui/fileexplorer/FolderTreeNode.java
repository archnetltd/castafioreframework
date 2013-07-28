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
 package org.castafiore.ecm.ui.fileexplorer;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Directory;

public class FolderTreeNode implements TreeNode {
	
	private Directory dir;
	
	private List<Directory> children = new ArrayList<Directory>();
	
	public FolderTreeNode( Directory dir)
	{
		this.dir = dir;
		
		FileIterator iterator = dir.getFiles();
		
		while(iterator.hasNext())
		{
			
			try
			{
				Directory d = (Directory)iterator.next();
				children.add(d);
			}
			catch(Exception e)
			{
				//e.printStackTrace();
			}
		}
		
		
	}

	public int childrenCount() {
		
		return children.size();
		
	}

	public EXContainer getComponent() {
		EXIconTreeNode node = new EXIconTreeNode("", "", dir.getName());
		
		return node;
	}

	public TreeNode getNodeAt(int index) {
		return new FolderTreeNode(children.get(index));
	}

	

	public boolean isLeaf() {
		return false;
	}

	public TreeNode getParent() {
		if(dir != null && dir.getParent() != null)
		{
			return new FolderTreeNode(dir.getParent());
		}
		return null;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
}
