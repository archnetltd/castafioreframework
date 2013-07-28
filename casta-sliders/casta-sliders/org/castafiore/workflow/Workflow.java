package org.castafiore.workflow;

import java.io.Serializable;
import java.util.Map;


public interface Workflow extends Serializable{
	
	/**
	 * Returns the name of the workflow
	 * @return
	 */
	public String getName();
	
	
	/**
	 * Returns the current state of the workflow
	 * @return
	 */
	public State getCurrentState();
	
	
	/**
	 * The actor executes an arbitrary action in the workflow.<br>
	 * 
	 * @param actor the actor executing the workflow
	 * @return Returns the {@link Action} executed
	 * @throws MultiplePossibleActionException when multiple possible paths available.
	 * @throws NoPossibleActionException when no actions available to execute
	 */
	public Action execute(Actor actor)throws MultiplePossibleActionException, NoPossibleActionException, InvalidActorInteractionException;
	
	
	/**
	 * 
	 * @param actor
	 * @param actionName
	 * @return
	 * @throws NoSuchActionException
	 */
	public Action execute(Actor actor, String actionName)throws NoSuchActionException, InvalidActorInteractionException;
	/**
	 * restarts the workflow
	 * Mandatory implementation
	 */
	public void reStart();
	
	
	/**
	 * Returns the context of the workflow
	 * @return
	 */
	public Map<Object, Object> getContext();
	
	
	
	
	
	

}
