package org.castafiore.workflow;

import java.io.Serializable;

public interface WorkflowManager extends Serializable{
	
	public Workflow getWorkflow(String name);
	
	public String[] getWorkflowNames();
	
	

}
