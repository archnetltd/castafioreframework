package org.castafiore.easyui.grid;

import java.util.List;

public interface TreeNode {

	public String getId();

	public String getText();

	public boolean isOpen();

	public boolean isChecked();

	public List<TreeNode> getChildren();

	public boolean isLeaf();

}