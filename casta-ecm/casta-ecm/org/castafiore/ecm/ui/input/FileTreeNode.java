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
package org.castafiore.ecm.ui.input;

import java.util.List;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.ex.tree.TreeNode;
import org.castafiore.utils.BaseSpringUtil;
import org.castafiore.utils.ResourceUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.iterator.FileFilter;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.iterator.SimpleFileIterator;
import org.castafiore.wfs.service.RepositoryService;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.File;


public class FileTreeNode implements TreeNode{
	
	protected File file = null;
	
	protected FileIterator children = null;
	
	protected TreeNode parent=null;
	
	protected FileFilter fileFilter = null;
	
	private int childrenCount = 0;
	
	private  Event SELECT = new Event(){

		public void ClientAction(ClientProxy container) {
			
			
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			//EXtreeV tree = container.getAncestorOfType(type)
			container.getAncestorOfType(EXTree.class).setAttribute("value", file.getAbsolutePath());
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};
	
	public FileTreeNode(File file, TreeNode parent, FileFilter fileFilter)
	{
		this.parent = parent;
		this.file = file;
		this.fileFilter = fileFilter;
		if(file != null)
		{
			if(file.isDirectory())
			{
				children = ((Directory)file).getFiles(fileFilter);
				this.childrenCount = new Long(children.count()).intValue();
			}
		}
		else
		{
			List drives=  BaseSpringUtil.getBeanOfType(RepositoryService.class).getDrives(Util.getRemoteUser());
			SimpleFileIterator sfi = new SimpleFileIterator(drives);
			this.children = sfi;
			this.childrenCount = drives.size();
		}
	}
	
	public FileTreeNode(File file, TreeNode parent)
	{
		this(file,parent,null);
	}
	
	public FileTreeNode(File file)
	{
		this(file,null,null);
	}
	
	public FileTreeNode()
	{
		this(null,null,null);
	}
	

	public int childrenCount() {
		return childrenCount;
	}

	public EXContainer getComponent() {
		String icon = "org/castafiore/resource/tree/img/page.gif";
		if(!isLeaf())
		{
			icon = "org/castafiore/resource/tree/img/folderopen.gif";
			
		}
		String name = "My Computer";
		if(file != null)
			name = file.getName();
		EXTreeComponent component = new EXTreeComponent("", name, ResourceUtil.getDownloadURL("classpath", icon));
		
		component.addEvent(SELECT, Event.CLICK);
		return component;
		
	}

	public TreeNode getNodeAt(int index) {
		return new  FileTreeNode(children.get(index),this.parent,this.fileFilter);
	}

	public TreeNode getParent() {
		return parent;
	}

	public boolean isLeaf() {
		if(file != null)
			return !file.isDirectory();
		else
			return false;
	}

	public void refresh() {
		if(file != null){
			file = SpringUtil.getRepositoryService().getFile(file.getAbsolutePath(), Util.getRemoteUser());
		}
		
		if(file != null)
		{
			if(file.isDirectory())
			{
				children = ((Directory)file).getFiles(fileFilter);
				this.childrenCount = new Long(children.count()).intValue();
			}
		}
		else
		{
			List drives=  BaseSpringUtil.getBeanOfType(RepositoryService.class).getDrives(Util.getRemoteUser());
			SimpleFileIterator sfi = new SimpleFileIterator(drives);
			this.children = sfi;
			this.childrenCount = drives.size();
		}
	}

}
