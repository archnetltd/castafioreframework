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
 package org.castafiore.designer.portalmenu.config;

import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.ex.tree.TreeNode;

public class PortalMenuTreeNode implements TreeNode {
	
	private NavigationDTO navigation = null;
	
	private TreeNode parent = null;
	
	

	public PortalMenuTreeNode(NavigationDTO navigation, TreeNode parent) {
		super();
		this.navigation = navigation;
		this.parent = parent;
	}
	
	

	public NavigationDTO getNavigation() {
		return navigation;
	}



	public void setNavigation(NavigationDTO navigation) {
		this.navigation = navigation;
	}



	public void setParent(TreeNode parent) {
		this.parent = parent;
	}



	public int childrenCount() {
		return navigation.getChildren().size();
		
	}

	public Container getComponent() {

		if(isLeaf()){
			EXTreeComponent item = new EXTreeComponent("",navigation.getLabel() , "http://www.extjs.com/deploy/dev/resources/images/default/tree/leaf.gif");
			return item;
		}else{
			EXTreeComponent item = new EXTreeComponent("", navigation.getLabel(), "http://www.extjs.com/deploy/dev/resources/images/default/tree/folder.gif");
			return item;
		}
	}

	public TreeNode getNodeAt(int index) {
		return new PortalMenuTreeNode(navigation.getChildren().get(index), this);
	}

	public TreeNode getParent() {
		return parent;
	}

	public boolean isLeaf() {
		return navigation.getChildren().size() > 0;
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
