package org.castafiore.designer.layout;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;

public class EXDroppableBorderLayoutContainer extends EXBorderLayoutContainer implements DesignableLayoutContainer {

	
	public String getPossibleLayoutData(Container container) {
		
		if(getContainer(TOP).getChildren().size() == 0){
			return TOP;
		}else if(getContainer(RIGHT).getChildren().size() == 0){
			return RIGHT;
		}else if(getContainer(BOTTOM).getChildren().size() == 0){
			return BOTTOM;
		}else if(getContainer(LEFT).getChildren().size() == 0){
			return LEFT;
		}else if(getContainer(CENTER).getChildren().size() == 0){
			return CENTER;
		}else{
			throw new UIException("All the regions in "+getName()+" where components can be dropped have already been filled");
		}
		
		
	}


	public void onAddComponent(Container component) {
		// TODO Auto-generated method stub
		
	}
	
	public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getOptions() {
		return new JMap().put("greedy", "true");
	}



	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}
	
	
	@Override
	public void moveUp(Container component) {
		//ComponentUtil.moveUp(component, this);
	}

	@Override
	public void moveDown(Container component) {
		//ComponentUtil.moveDown(component, this);
	}

}
