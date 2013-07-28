package org.castafiore.ui.menu;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.ex.tree.TreeNode;

public class MutableMenuTreeNode implements TreeNode<EXMenuItem>{

	
	private TreeNode<EXMenuItem> parent;
	
	private EXMenuItem component;
	
	private List<TreeNode<EXMenuItem>> children = new ArrayList<TreeNode<EXMenuItem>>();
	
	
	
	public MutableMenuTreeNode(TreeNode<EXMenuItem> parent, EXMenuItem component) {
		super();
		this.parent = parent;
		this.component = component;
	}
	
	public void addChild(EXMenuItem child){
		MutableMenuTreeNode n = new MutableMenuTreeNode(this, child);
		children.add(n);
	}

	@Override
	public int childrenCount() {
		return children.size();
	}

	@Override
	public EXMenuItem getComponent() {
		return component;
	}

	@Override
	public TreeNode<EXMenuItem> getNodeAt(int index) {
		return children.get(index);
	}

	@Override
	public TreeNode<EXMenuItem> getParent() {
		return parent;
	}

	@Override
	public boolean isLeaf() {
		return children.size() == 0;
	}

	@Override
	public void refresh() {
		component.refresh();
		
	}

}
