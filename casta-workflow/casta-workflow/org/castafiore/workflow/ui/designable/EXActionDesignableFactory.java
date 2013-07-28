package org.castafiore.workflow.ui.designable;

import org.castafiore.ui.Container;
import org.castafiore.workflow.ui.AbstractWorkflowContainer;
import org.castafiore.workflow.ui.EXAction;

public class EXActionDesignableFactory extends AbstractWorkflowContainerDesignableFactory {

	public EXActionDesignableFactory() {
		super("Actions");
		
	}

	@Override
	public void applySpecificAttr(AbstractWorkflowContainer c,
			String attributeName, String attributeValue) {
		c.setAttribute(attributeName, attributeValue);
		if(attributeName.equals("color")){
			c.setStyle("background-color", attributeValue);
		}
	}

	@Override
	public String[] getSpecificAttr() {
		return new String[]{"color", "nextstate", "form"};
	}

	@Override
	public Container getInstance() {
		return new EXAction("Action");
	}

	@Override
	public String getUniqueId() {
		return "workflow:action";
	}

}
