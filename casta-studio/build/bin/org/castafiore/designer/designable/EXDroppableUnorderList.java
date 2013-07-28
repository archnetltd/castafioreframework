package org.castafiore.designer.designable;

import javax.swing.plaf.ComponentUI;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.layout.EXUnOrderedList;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;

public class EXDroppableUnorderList extends EXUnOrderedList implements DesignableLayoutContainer{

	public EXDroppableUnorderList() {
		super("Unordered List");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getPossibleLayoutData(Container container) {
		return getChildren().size() + "";
	}

	@Override
	public void moveDown(Container component) {
		
		ComponentUtil.moveUp(component.getParent(), this);
	}

	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveUp(component.getParent(), this);
		
	}

	@Override
	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAcceptClasses() {
		return new String[]{"component"};
	}

	@Override
	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

}
