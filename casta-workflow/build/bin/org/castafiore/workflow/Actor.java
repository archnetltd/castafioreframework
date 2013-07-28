package org.castafiore.workflow;

public interface Actor {
	
	public String getName();
	
	public String getLabel();
	
	public String getDescription();
	
	public boolean isUserAllowed(String username);
}
