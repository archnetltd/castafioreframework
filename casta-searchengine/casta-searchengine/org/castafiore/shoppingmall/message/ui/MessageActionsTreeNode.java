package org.castafiore.shoppingmall.message.ui;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.left.MallTreeNode;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.tree.TreeNode;

public class MessageActionsTreeNode implements MallTreeNode{
	private final static String[] ACTIONS = new String[]{"Inbox", "Sent", "Shared", "Draft"};
	
	
	@Override
	public int childrenCount() {
		return ACTIONS.length;
	}

	@Override
	public Container getComponent() {
		return new EXContainer("", "a").setText("root");
	}

	@Override
	public TreeNode<Container> getNodeAt(int index) {
		
		final String action = ACTIONS[index];
		
		return new MallTreeNode() {

			@Override
			public int childrenCount() {
				
				return 0;
			}

			@Override
			public Container getComponent() {
				return new EXContainer("", "a").setText(action).setAttribute("href", "#").setAttribute("action", action);
			}

			@Override
			public TreeNode<Container> getNodeAt(int index) {
				
				return null;
			}

			@Override
			public TreeNode<Container> getParent() {
				return this;
				//return null;
			}

			@Override
			public boolean isLeaf() {
				return true;
			}

			@Override
			public void refresh() {
			}

			@Override
			public void executeAction(Container source) {
				
			}
			
			
		};
	}

	@Override
	public TreeNode<Container> getParent() {
		
		return null;
	}

	@Override
	public boolean isLeaf() {
		
		return false;
	}

	@Override
	public void refresh() {
		
		
	}

	@Override
	public void executeAction(Container container) {
		if(container.getAncestorOfType(EXMall.class) != null){
			if(container.getAttribute("action").equalsIgnoreCase("Comments")){
				container.getAncestorOfType(EXMall.class).showAddedComments();
			}else if(container.getAttribute("action").equalsIgnoreCase("Inbox")){
				container.getAncestorOfType(EXMall.class).showInbox();
			}else if(container.getAttribute("action").equalsIgnoreCase("Sent")){
				container.getAncestorOfType(EXMall.class).showSentMessages();
			}else if(container.getAttribute("action").equalsIgnoreCase("Shared")){
				container.getAncestorOfType(EXMall.class).showSharedMessages();
			}else if(container.getAttribute("action").equalsIgnoreCase("Draft")){
				container.getAncestorOfType(EXMall.class).showAlerts();
			}
		}else{
			
			if(container.getAttribute("action").equalsIgnoreCase("Comments")){
				container.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMessagePanel.class).showAddedComments();
			}else if(container.getAttribute("action").equalsIgnoreCase("Inbox")){
				container.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMessagePanel.class).showInbox();
			}else if(container.getAttribute("action").equalsIgnoreCase("Sent")){
				container.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMessagePanel.class).showSentMessage();
			}else if(container.getAttribute("action").equalsIgnoreCase("Shared")){
				container.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMessagePanel.class).showSharedMessages();
			}else if(container.getAttribute("action").equalsIgnoreCase("Draft")){
				container.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMessagePanel.class).showDraftMessages();
			}
			//container.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMessagePanel.class).showAddedComments();
		}
		
	}

	
}
