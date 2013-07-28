package org.castafiore.workflow.ui.designable;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.toolbar.menu.MenuListener;
import org.castafiore.ui.Container;

public class WorkflowMenuListener extends MenuListener{
	
	@Override
	public void newPortal(Container container){
		final EXDesigner designer = container.getAncestorOfType(EXDesigner.class);
		//EXDynaformPanel panel = new EXDynaformPanel("newportal","New Portal");
		designer.addPopup(new EXNewWorkflow(""));

	}

}
