package org.castafiore.workflow.ui.designable;

import org.castafiore.ui.Container;
import org.castafiore.workflow.ui.AbstractWorkflowContainer;
import org.castafiore.workflow.ui.EXState;

public class EXStateDesignableFactory extends AbstractWorkflowContainerDesignableFactory{

	public EXStateDesignableFactory() {
		super("States");
	}

	@Override
	public Container getInstance() {
		return new EXState("State");
	}	

	@Override
	public String getUniqueId() {
		return "workflow:state";
	}

	@Override
	public void applySpecificAttr(AbstractWorkflowContainer c,
			String attributeName, String attributeValue) {
		EXState state = (EXState)c;
		if(attributeName.equals("value")){
			state.setValue(Integer.parseInt(attributeValue));
		}	
	}

	@Override
	public String[] getSpecificAttr() {
		return new String[]{"value"};
	}

}
