package org.castafiore.workflow;

import java.io.Serializable;
import java.util.List;

public interface State extends Serializable{
	
	/**
	 * A name should be unique within the whole workflow
	 * @return
	 */
	public String getName();
	
	/**
	 * Returnsn all possible actions for the specified actor
	 * @param actor
	 * @return
	 */
	public List<Action> getActions(Actor actor);
	
	
	/**
	 * Returns all action in the State
	 * @return
	 */
	public List<Action> getActions();

}
