package org.castafiore.workflow;

import java.io.Serializable;
import java.util.Map;

public interface Action extends Serializable{
	
	/**
	 * The name of an action should be unique within a state
	 * @return
	 */
	public String getName();
	
	/**
	 * Actually performs the action
	 */
	public void doAction(Actor actor, Map<Object, Object> context)throws InvalidActorInteractionException;
	
	
	/**
	 * Checks if this actor has the right to execute this actions
	 * @param actor
	 * @return
	 */
	public boolean canExecute(Actor actor);

}
