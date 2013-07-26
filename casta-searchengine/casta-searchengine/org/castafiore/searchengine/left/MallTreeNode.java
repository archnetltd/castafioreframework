package org.castafiore.searchengine.left;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.tree.TreeNode;

public interface MallTreeNode extends TreeNode<Container> {
	
	
	
	public void executeAction(Container source);

}
