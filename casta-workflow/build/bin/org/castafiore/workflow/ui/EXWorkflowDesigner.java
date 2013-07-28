package org.castafiore.workflow.ui;

import java.util.Map;

import org.castafiore.designer.EXDesigner;
import org.castafiore.workflow.ui.designable.WorkflowMenuListener;

public class EXWorkflowDesigner extends EXDesigner{

	public EXWorkflowDesigner() {
		super();
		setTemplateLocation("templates/workflow/EXWorkflowDesigner.xhtml");
	}
	
	public void handleMenuItem(Map<String, String> request){
		new WorkflowMenuListener().handleMenu(request.get("menuitem"), this);
	}

}
