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
 package org.castafiore.ecm.ui.selector;

import org.castafiore.ecm.ui.input.FileTreeNode;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.types.File;

public class FileSelectorFileTreeNode extends FileTreeNode {
	
	private final static Event SELECT = new SelectFolderInTreeEvent();

	public FileSelectorFileTreeNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public FileSelectorFileTreeNode(File file, TreeNode parent,
			FileFilter fileFilter) {
		super(file, parent, fileFilter);
		// TODO Auto-generated constructor stub
	}



	public FileSelectorFileTreeNode(File file, TreeNode parent) {
		super(file, parent);
		// TODO Auto-generated constructor stub
	}



	public FileSelectorFileTreeNode(File file) {
		super(file);
		// TODO Auto-generated constructor stub
	}



	@Override
	public EXContainer getComponent() {
		String icon = "org/castafiore/resource/tree/img/page.gif";
		if(!isLeaf())
		{
			icon = "org/castafiore/resource/tree/img/folderopen.gif";
			
		}
		String name = "My Computer";
		if(file != null)
			name = file.getName();
		EXTreeComponent component = new EXTreeComponent(file.getAbsolutePath(), name, ResourceUtil.getDownloadURL("classpath", icon));
		component.setAttribute("path", file.getAbsolutePath());
		if(!isLeaf()){
			component.addEvent(SELECT, Event.CLICK);
		}
		return component;
	}
	

	
	public TreeNode getNodeAt(int index) {
		return new  FileSelectorFileTreeNode(children.get(index),this.parent,this.fileFilter);
	}

}
