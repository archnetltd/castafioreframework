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
 package org.castafiore.designer.wizard.navigation;

import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.contextmenu.ContextMenuModel;
import org.castafiore.ui.ex.tree.EXTree;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.ex.tree.TreeNode;

public class NavigationCreator extends EXContainer{

	public NavigationCreator(String name) {
		super(name, "div");
		
		EXTree tree = new EXTree("navigations", getStartingNavigation());
		addChild(tree);
		tree.setWidth(Dimension.parse("300px"));
		//tree.setStyle("border", "solid 1px silver");
		tree.setStyle("float", "left");
		tree.addClass("ui-widget-content");
		setWidth(Dimension.parse("100%"));
		addChild(new NavigationConfig(""));
	}
	
	protected NavigationTreeNode getStartingNavigation(){
		NavigationDTO root = new NavigationDTO();
		
		root.setName("root");
		root.setLabel("root");
		root.setPageReference("root");
		root.setUri("uri");
		
		String[] navs = new String[]{"Home", "Products", "About us", "Contact us"};
		
		for(String nav : navs){
			NavigationDTO n = new NavigationDTO();
			n.setLabel(nav);
			n.setName(nav);
			n.setPageReference(nav);
			n.setUri(nav);
			root.getChildren().add(n);
		}
		
		
		return new NavigationTreeNode(root, null);
		
		
	}
	
	
	public static class NavigationTreeNode implements TreeNode<EXTreeComponent>{
		
		
		private NavigationDTO navigation;
		
		private NavigationTreeNode parent;

		public NavigationTreeNode(NavigationDTO navigation,
				NavigationTreeNode parent) {
			super();
			this.navigation = navigation;
			this.parent = parent;
		}

		public int childrenCount() {
			return navigation.getChildren().size();
		}

		public EXTreeComponent getComponent() {
			EXTreeComponent tr = new NavigationTreeComponent(navigation.getName(),navigation.getLabel(), EXTreeComponent.getIcon(isLeaf()));
			tr.setAttribute("uri", navigation.getUri());
			tr.setAttribute("pageReference", navigation.getPageReference());
			return tr;
		}

		public TreeNode<EXTreeComponent> getNodeAt(int index) {
			return new NavigationTreeNode(navigation.getChildren().get(index),this);
		}

		public TreeNode<EXTreeComponent> getParent() {
			return parent;
		}

		public boolean isLeaf() {
			return (childrenCount()<= 0);
		}

		public void refresh() {
			
			
		}
		
	}
	
	
	public static class NavigationTreeComponent extends EXTreeComponent {

		public NavigationTreeComponent(String name, String label, String iconUrl) {
			super(name, label, iconUrl);
			
		}

		public ContextMenuModel getContextMenuModel() {
	
			return new NavigationTreeComponentContextMenuModel();
		}
		
	}
	
	
	public static class NavigationTreeComponentContextMenuModel implements ContextMenuModel{

		private final static String[] titles = new String[]{"Add Child", "Delete", "Move Up", "Move Down"};
		
		public Event getEventAt(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getIconSource(int index) {
			// TODO Auto-generated method stub
			return null;
		}

		public String getTitle(int index) {
			return titles[index];
		}

		public int size() {
			return titles.length;
		}
		
	}
	
	

}
