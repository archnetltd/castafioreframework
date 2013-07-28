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
 package org.castafiore.ecm.ui.fileexplorer.tree;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.BinaryFile;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;

public class EXFileTreeNode implements TreeNode<EXTreeComponent> {

	
	private String path = null;
	
	private int count= 0;
	
	private boolean leaf = false;
	
	private TreeNode<EXTreeComponent> parent;
	
	private FileIterator fileIterator= null;
	
	public EXFileTreeNode(File file , TreeNode<EXTreeComponent> parent){
		//this.file= file;
		this.path= file.getAbsolutePath();
		
		if (! (file instanceof Directory) || (file instanceof BinaryFile)){
			leaf = true;
		}else{
			//return false;
			leaf = false;
		}
		if(!isLeaf()){
			fileIterator = ((Directory)file).getFiles();
			this.count = new Long(fileIterator.count()).intValue();
		}
	}
	
	
	public int childrenCount() {
		return count;
	}

	public EXTreeComponent getComponent() {
		File file= SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		return new EXFileTreeComponent(file);
	}

	public TreeNode<EXTreeComponent> getNodeAt(int index) {
		if(!isLeaf()){
			return new EXFileTreeNode( fileIterator.get(index), this);
		}else{
			return null;
		}
	}

	public TreeNode<EXTreeComponent> getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return leaf;
	}
	


	public void refresh() {
		File file= SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		if(!isLeaf()){
			fileIterator = ((Directory)file).getFiles();
			this.count = new Long(fileIterator.count()).intValue();
		}
		
	}

}
