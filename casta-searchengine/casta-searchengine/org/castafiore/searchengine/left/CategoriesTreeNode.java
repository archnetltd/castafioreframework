package org.castafiore.searchengine.left;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.tree.TreeNode;

public class CategoriesTreeNode implements MallTreeNode{
	
	boolean ist = false;
	
	
	private CategoriesTreeNode parent = null;
		// TODO Auto-generated constructor stub
	//private Directory current;
	
	//FileIterator<Directory> children = null;
	
	
	public CategoriesTreeNode(CategoriesTreeNode parent){
		//this(null, SpringUtil.getBeanOfType(ShoppingMallManager.class).getDefaultMall().getRootCategory());
		this.ist = parent!=null;
		this.parent = parent;
	}

//	private CategoriesTreeNode(MallTreeNode parent, Directory current) {
//		super();
//		this.parent = parent;
//		this.current = current;
//		children = current.getChildren(Directory.class);
//	}

	@Override
	public int childrenCount() {
		if(ist)
			return 0;
		else
			return 1;
	}

	@Override
	public Container getComponent() {
		
//		EXSearchProductLink link = new EXSearchProductLink("");
//		link.setText(current.getName());
//		String searchTerm = "cat:";
//		
//		if(parent != null && !parent.getComponent().getName().equalsIgnoreCase("categories")){
//			searchTerm =  ((EXSearchProductLink)parent.getComponent()).getSearchTerm();
//		}
//		searchTerm = searchTerm + "/" + current.getName();
//		link.setSearchTerm(searchTerm.replace("/categories", ""));
//		return link;
		//return new EXContainer("", "a").setText(current.getName()).setAttribute("href", "#");
		if(ist)
			return new EXCategories("EXCategories");
		else
			return null;
	}

	@Override
	public TreeNode<Container> getNodeAt(int index) {
		return new CategoriesTreeNode(this);
	}

	@Override
	public TreeNode<Container> getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return childrenCount() ==0;
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void executeAction(Container source) {
		// TODO Auto-generated method stub
		
	}

}
