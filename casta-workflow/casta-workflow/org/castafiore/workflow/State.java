package org.castafiore.workflow;

import java.util.List;

public interface State {
	
	public int getValue();
	
	public void setValue(int value);
	
	public String getStateLabel();
	
	public String getDescription();
	
	public String getColor();
	
	public List<Action> getActions();
}
