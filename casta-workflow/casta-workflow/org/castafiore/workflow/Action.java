package org.castafiore.workflow;

import org.castafiore.ui.Container;

public interface Action {
	
	public String getName();
	
	public String getLabel();
	
	public String getDescription();
	
	public State execute(WorkflowContext context);
	
	public Container getButtonType(String type);
	
	public Actor getActor();
	
	

}
