package org.castafiore.workflow.ui.designable;

import org.castafiore.designer.layout.EXDroppablePrimitiveTagLayoutContainer;
import org.castafiore.designer.portal.EXDesignablePortalContainer;
import org.castafiore.designer.portal.EXPortalContainerDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.workflow.ui.EXWorkflow;

public class EXWorkflowDesignableFactory extends EXPortalContainerDesignableFactory{
	
	public EXWorkflowDesignableFactory() {
		super();
		setName("EXWorkflowDesignableFactory");
		setText("Workflow");
	}
	
	
	
	
	@Override
	public String getCategory() {
		return "workflow";
	}

	@Override
	public Container getInstance() {
		EXDroppablePrimitiveTagLayoutContainer lc = new EXWorkflow();
		lc.setStyle("width", "800px");
		lc.setStyle("height", "600px");
		lc.setStyle("background", "beige");
		lc.setStyle("border", "solid 1px steelblue");
		setAttribute("text", "");
		return lc;
	}

	public String getUniqueId() {
		
		return "workflow:workflow";
	}

}
