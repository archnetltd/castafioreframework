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
 package org.castafiore.ui.ex.tree;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ResourceUtil;

public class EXTree extends EXContainer{
	
	
	public static Event OPEN = new Event(){

		public void ClientAction(ClientProxy container) {
			container.mask(container.getAncestorOfType(EXTree.class));
			container.makeServerRequest(new JMap().put("action", container.getAttribute("action")),this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			
			String action = request.get("action");
			
			if("open".equals(action))
			{
				request.put("idto", container.getAncestorOfType(Node.class).Open());
			}
			else
			{
				request.put("idto",container.getAncestorOfType(Node.class).Close());
				
			}
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			String action = request.get("action");
			String id = request.get("idto");
			
			ClientProxy proxy = new ClientProxy("#" + id);
			if(action.equalsIgnoreCase("open"))
			{
				
				container.mergeCommand(proxy.setStyle("display", "none").slideDown(100));
			}
			else
			{
				container.mergeCommand(proxy.setStyle("display", "block").slideUp(100));
			}
			
		}
		
	};
	
	
	public static Event CLOSE = new Event(){

		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(Node.class).Close();
			return true;
		}

		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
	};

	public EXTree(String name, TreeNode<EXTreeComponent> treeNode) {
		super(name, "div");
		setStyle("font-family", "Verdana,Geneva,Arial,Helvetica,sans-serif; font-size: 11px");
		
		
		//EXContainer component = treeNode.getComponent();
		//addChild(component);
		
		Node root = new Node("root", treeNode);
		//root.Open();
		
		addChild(root);
		setStyle("width", "auto");
		setHeight(Dimension.parse("500px"));
		setStyle("overflow", "auto");
		
	}
	
	
	/**
	 * deletes the node containing the component with specified id
	 * @param idOfComponentHolding
	 */
	public Node getNode(String idOfComponentHolding){
		
		 Node node = getDescendentById(idOfComponentHolding).getAncestorOfType(Node.class);
		 return node;		
	}
	
	
	
	//public void refreshNode()
	
	
	public class Node extends EXContainer
	{
		public Node(String name, TreeNode node) {
			super(name, "div");
			setStyle("padding-left", "10px");
			addChild(new NodeContainer(node));
		}
		
		public void refreshNode(){
			TreeNode treeNode = getDescendentOfType(NodeContainer.class).treeNode;
			treeNode.refresh();
			this.getChildren().clear();
			this.setRendered(false);
			
			addChild(new NodeContainer(treeNode));
			
			Open();
		}
		
		public String Open()
		{
			return getDescendentOfType(NodeContainer.class).Open();
		}
		
		public String Close()
		{
			return getDescendentOfType(NodeContainer.class).Close();
		}
	}
	
	
	public class NodeContainer extends EXContainer{
		
		private TreeNode treeNode;
		
		private int size;
		
		public  NodeContainer( TreeNode treeNode)
		{
			super("NodeContainer", "div");
			
			this.treeNode = treeNode;
			this.size = treeNode.childrenCount();
			boolean isLeaf = treeNode.isLeaf();
			
			TreeFramework tf = new TreeFramework(getDepth(treeNode), isLeaf, size);
			addChild(tf);
			
			Container userObject = treeNode.getComponent();
			if(userObject != null)
			{
				userObject.setStyle("display", "inline");
				addChild(userObject);
			}
			
			if(!isLeaf)
			{
				EXContainer children = new EXContainer("children", "div");
				children.setDisplay(false);
				addChild(children);
			}
		}
		
		public String Open()
		{
			if(size > 0)
			{
				Container children= getChild("children");
				children.setDisplay(true);
				int currentSize = children.getChildren().size();
				getDescendentOfType(Plus.class).setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/tree/img/nolines_minus.gif"));
				getDescendentOfType(Plus.class).setAttribute("action", "close");
				if(currentSize != this.size)
				{
					children.getChildren().clear();
					children.setRendered(false);
					for(int i = 0; i < size; i ++)
					{
						children.addChild(new Node("", treeNode.getNodeAt(i)));
					}
				}
			}
			return getChild("children").getId();
		}
		
		public String Close()
		{
			getChild("children").setDisplay(false);
			getDescendentOfType(Plus.class).setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/tree/img/nolines_plus.gif"));
			getDescendentOfType(Plus.class).setAttribute("action", "open");
			return getChild("children").getId();
		}
	}
	
	
	public static int getDepth(TreeNode treeNode)
	{
		int i = 0;
		
		TreeNode tmp = treeNode;
		while(tmp != null)
		{
			i++;
			tmp = tmp.getParent();
		}
		return i;
	}
	
	
	public class TreeFramework extends EXContainer{

		public TreeFramework(int depth, boolean isLeft, int childrenCount) {
			super("TreeFramework", "div");
			setStyle("display", "inline");
			for(int i =0; i < depth; i ++)
			{
				if((i == depth - 1) && !isLeft && childrenCount > 0)
				{
					addChild(new Plus());
				}
				else
				{
					addChild(new Empty());
				}
					
			}
		}
		
	}
	
	
	
	public static class Plus extends EXContainer
	{

		public Plus() {
			super("Plus", "img");
			setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/tree/img/nolines_plus.gif"));
			addEvent(OPEN, Event.CLICK);
			setAttribute("action", "open");
			setStyle("vertical-align", "middle");
		}
		
	}
	
		
	public static class Minus extends EXContainer
	{

		public Minus(String name) {
			super(name, "img");
			setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/tree/img/nolines_minus.gif"));
			addEvent(OPEN, Event.CLICK);
		}
		
	}
	
	
	
	public static class Empty extends EXContainer
	{

		public Empty() {
			super("Empty", "img");
			setAttribute("src", ResourceUtil.getDownloadURL("classpath", "org/castafiore/resource/tree/img/empty.gif"));
			setStyle("vertical-align", "middle");
			setStyle("width", "10px");
		}
		
	}

}
