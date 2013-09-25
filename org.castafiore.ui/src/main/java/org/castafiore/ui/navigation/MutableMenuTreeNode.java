/*
 * 
 */
package org.castafiore.ui.navigation;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.ui.tree.TreeNode;

/**
 * Convenient implementation of {@link TreeNode} that allows addition and
 * removal of {@link TreeNode}s
 * 
 * @author arossaye
 * 
 */
public class MutableMenuTreeNode implements TreeNode<MenuItem> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TreeNode<MenuItem> parent;

	private MenuItem component;

	private List<TreeNode<MenuItem>> children = new ArrayList<TreeNode<MenuItem>>();

	/**
	 * Constructs a {@link TreeNode} to be attached to the specified parent and
	 * to hold the specified {@link MenuItem}
	 * 
	 * @param parent
	 * @param component
	 */
	public MutableMenuTreeNode(TreeNode<MenuItem> parent, MenuItem component) {
		super();
		this.parent = parent;
		this.component = component;
	}

	/**
	 * Adds a child to the current Node
	 * 
	 * @param child
	 *            The chold to add
	 */
	public void addChild(MenuItem child) {
		MutableMenuTreeNode n = new MutableMenuTreeNode(this, child);
		children.add(n);
	}

	/**
	 * Return sthe children count
	 */
	@Override
	public int childrenCount() {
		return children.size();
	}

	/**
	 * Returns the component it is holding
	 */
	@Override
	public MenuItem getComponent() {
		return component;
	}

	/**
	 * Return child node at specified index
	 */
	@Override
	public TreeNode<MenuItem> getNodeAt(int index) {
		return children.get(index);
	}

	/**
	 * Returns the parent on which this node is attached.<br>
	 * Null if this node is root node
	 */
	@Override
	public TreeNode<MenuItem> getParent() {
		return parent;
	}

	/**
	 * Checks if this is a leaf node
	 */
	@Override
	public boolean isLeaf() {
		return children.size() == 0;
	}

	/**
	 * refresh the component being held by this node
	 */
	@Override
	public void refresh() {
		component.refresh();

	}

}
