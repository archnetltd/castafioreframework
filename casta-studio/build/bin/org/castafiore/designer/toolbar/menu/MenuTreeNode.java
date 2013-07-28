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

public class MenuTreeNode implements TreeNode<EXMenuItem>{
	
	private final static String[] rootLabel = new String[]{"New Page","Open Page","Save Page","Save Portal",  "CSS Editor"};

	
	public int childrenCount() {
		return rootLabel.length;
	}

	
	public EXMenuItem getComponent() {
		return new EXMenuItem("Start", "Start");
	}

	
	public TreeNode<EXMenuItem> getNodeAt(final int index) {
		if(index == 0){
			return new LeafNode(rootLabel[index], rootLabel[index] , this);
		}else if(index == 1){
			return new LeafNode(rootLabel[index], rootLabel[index] , this);
		}else if(index == 2){
			return new LeafNode(rootLabel[index], rootLabel[index] , this);
		}else if(index == 3){
			return new LeafNode(rootLabel[index], rootLabel[index] , this);
		}
		//else if(index == 4){
//			return new FileMenuTreeNode(this, new String[]{"Edit Mode", "View Mode","Split Mode" }, "View");
//		}
		else if(index == 4){
			//return new FileMenuTreeNode(this, new String[]{"CSS Editor" }, "Execute");
			return new LeafNode(rootLabel[index], rootLabel[index] , this);
		}else{
			return null;
		}
	}

	
	public TreeNode<EXMenuItem> getParent() {
		
		return null;
	}

	
	public boolean isLeaf() {
		
		return false;
	}

	
	public void refresh() {
		
	}

}
