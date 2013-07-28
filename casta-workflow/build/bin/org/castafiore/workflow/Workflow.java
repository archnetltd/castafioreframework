package org.castafiore.workflow;

import java.util.List;

public interface Workflow {
	
	public List<State> getAllStates();
	
	public State getCurrentState();
	
	public List<Actor> getAllActors();
	
	public List<Action> getActions(Actor currrentActor, State currentState);
	
	public Actor getCurrentActor();
	
	public String getLabel();
	
	public String getDescription();
	
	

	

}
