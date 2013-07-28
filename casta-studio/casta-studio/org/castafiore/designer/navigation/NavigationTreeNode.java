/**
 * 
 */
package org.castafiore.designer.navigation;

import org.castafiore.designer.model.NavigationDTO;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.tree.EXTreeComponent;
import org.castafiore.ui.ex.tree.TreeNode;

/**
 * @author acer
 *
 */
public class NavigationTreeNode implements TreeNode<EXTreeComponent>{

	private NavigationDTO dto_;
	
	private TreeNode<EXTreeComponent> parent_;
	
	
	
	public NavigationTreeNode(NavigationDTO dto, TreeNode<EXTreeComponent> parent) {
		super();
		dto_ = dto;
		parent_ = parent;
	}

	@Override
	public int childrenCount() {
		return dto_.getChildren().size();
	}

	@Override
	public EXTreeComponent getComponent() {
		return new EXTreeComponent(dto_.getName(), dto_.getLabel(), "icons-2/fugue/icons/document-attribute-p.png");
	}

	
	@Override
	public TreeNode<EXTreeComponent> getNodeAt(int index) {
		return new NavigationTreeNode(dto_.getChildren().get(index), this);
	}



	@Override
	public TreeNode<EXTreeComponent> getParent() {
		return parent_;
	}

	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.tree.TreeNode#isLeaf()
	 */
	@Override
	public boolean isLeaf() {
		if(childrenCount() == 0){
			return true;
		}else{
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see org.castafiore.ui.ex.tree.TreeNode#refresh()
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		
	}

}
