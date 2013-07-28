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

import org.castafiore.ecm.ui.fileexplorer.button.EXButtonWithLabel;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class FileTreeNode implements TreeNode {

	private File file;
	
	
	public FileTreeNode(File file)
	{
		this.file = file;
	}
	
	public int childrenCount() {
		if(file.isDirectory())
		{
			Directory dir = (Directory)file;
			int count = dir.getFiles().count();
			return count;
		}
		return 0;
	}

	public EXContainer getComponent() {
		
		EXButtonWithLabel button = new EXButtonWithLabel("" , file.getName(), "BlueClosedFolder.gif", "medium");
		return button;
		
		//EXContainer a = new EXContainer("", "a");
		//a.setText(file.getName());
		//return a;
	}

	public TreeNode getNodeAt(int index) {
		if(file.isDirectory())
		{
			Directory directory = (Directory)file;
			File f = directory.getFiles().get(index);
			return new FileTreeNode(f);
		}
		return null;
	}

	public boolean isLeaf() {
		return !file.isDirectory();
	}

	public TreeNode getParent() {
		if(file != null && file.getParent() != null)
		{
			return new FileTreeNode(file.getParent());
		}
		else
		{
			//return new DefaultMutableTreeNode(null, false);
			return null;
		}
		//return null;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
