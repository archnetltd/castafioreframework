/*
 * 
 */
package org.castafiore.shoppingmall.user.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.EXMall;
import org.castafiore.searchengine.left.MallTreeNode;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.ui.ex.tree.TreeNode;

/**
 * The Class UserActionsTreeNode.
 */
public class UserActionsTreeNode implements MallTreeNode{

	/** The Constant ACTIONS. */
	private final static String[] ACTIONS = new String[]{"About Me", "Contact info", "Jobs Carreer", "Interests","My Wish List", "My subscriptions"};
	
	
	@Override
	public int childrenCount() {
		return ACTIONS.length;
	}

	
	@Override
	public Container getComponent() {
		return new EXContainer("", "a").setText("root");
	}

	
	@Override
	public TreeNode<Container> getNodeAt(final int index) {
		
		final String action = ACTIONS[index];
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
	public void executeAction(Container source) {
		if(source.getAncestorOfType(EXMall.class) != null){
			source.getAncestorOfType(EXMall.class).getWorkingSpace().viewOnly(EXMyAccountPanel.class);
			if(source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class) == null){
				source.getAncestorOfType(EXMall.class).showMyAccount();
			}
			if(source.getName().equalsIgnoreCase("About Me")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showAboutMe();
			}else if(source.getName().equalsIgnoreCase("Jobs Carreer")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showCarreer();
			}else if(source.getName().equalsIgnoreCase("Interests")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showInterests();
			}else if(source.getName().equalsIgnoreCase("Contact info")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showContactInfo();
			}else if(source.getName().equalsIgnoreCase("Settings")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showShopSettings();
			}else if(source.getName().equalsIgnoreCase("Products")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showProductList(Product.STATE_DRAFT);
			}else if(source.getName().equalsIgnoreCase("My Wish List")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showWishList();
			}else if(source.getName().equalsIgnoreCase("My subscriptions")){
				source.getAncestorOfType(EXMall.class).getDescendentOfType(EXMyAccountPanel.class).showMySubscriptions();
			}
		}else{
			if(source.getName().equalsIgnoreCase("About Me")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showAboutMe();
			}else if(source.getName().equalsIgnoreCase("Jobs Carreer")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showCarreer();
			}else if(source.getName().equalsIgnoreCase("Interests")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showInterests();
			}else if(source.getName().equalsIgnoreCase("Contact info")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showContactInfo();
			}else if(source.getName().equalsIgnoreCase("Settings")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showShopSettings();
			}else if(source.getName().equalsIgnoreCase("Products")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showProductList(Product.STATE_DRAFT);
			}else if(source.getName().equalsIgnoreCase("My Wish List")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showWishList();
			}else if(source.getName().equalsIgnoreCase("My subscriptions")){
				source.getAncestorOfType(EXPanel.class).getDescendentOfType(EXMyAccountPanel.class).showMySubscriptions();
			}
		}
	
		
		
	}

}
