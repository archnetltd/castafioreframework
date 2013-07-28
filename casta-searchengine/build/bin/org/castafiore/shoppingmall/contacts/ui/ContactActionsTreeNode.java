package org.castafiore.shoppingmall.contacts.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.left.MallTreeNode;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tree.TreeNode;

public class ContactActionsTreeNode implements MallTreeNode{
	List<String> ACTIONS = new ArrayList<String>();
	public ContactActionsTreeNode(){
		ShoppingMallUser user = MallUtil.getCurrentUser();
		ACTIONS = user.getContactCategories();
	}
	
	@Override
	public int childrenCount() {
		return ACTIONS.size();
	}

	@Override
	public Container getComponent() {
		return new EXContainer("", "a").setText("root");
	}

	@Override
	public TreeNode<Container> getNodeAt(int index) {
		
		final String action = ACTIONS.get(index);
		
		return new MallTreeNode() {

			@Override
			public int childrenCount() {
				
				return 0;
			}

			@Override
			public Container getComponent() {
				return new EXContainer(action, "a").setText(action).setAttribute("href", "#");
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
		//container.getAncestorOfType(EXMall.class).showContacts(container.getName());
		
	}

	
}
