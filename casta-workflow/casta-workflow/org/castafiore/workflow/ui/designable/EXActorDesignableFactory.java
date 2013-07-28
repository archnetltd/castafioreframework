package org.castafiore.workflow.ui.designable;

import org.castafiore.ui.Container;
import org.castafiore.workflow.ui.AbstractWorkflowContainer;
import org.castafiore.workflow.ui.EXActor;

public class EXActorDesignableFactory extends AbstractWorkflowContainerDesignableFactory{

	public EXActorDesignableFactory() {
		super("Actors");
		
	}

	@Override
	public void applySpecificAttr(AbstractWorkflowContainer c,
			String attributeName, String attributeValue) {
		
		c.setAttribute(attributeName, attributeValue);
	}

	@Override
	public String[] getSpecificAttr() {
		return new String[]{"users"};
	}

	@Override
	public Container getInstance() {
		return new EXActor("Actor");
	}

	@Override
	public String getUniqueId() {
		return "workflow:actor";
	}


}
