package org.castafiore.ui.navigation;

import org.castafiore.ui.Container;
import org.castafiore.ui.tree.TreeNode;

public interface Menu extends Container {

	public TreeNode<MenuItem> getModel();

}
