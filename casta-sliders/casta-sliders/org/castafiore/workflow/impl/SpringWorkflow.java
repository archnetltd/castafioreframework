package org.castafiore.workflow.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.castafiore.workflow.Action;
import org.castafiore.workflow.Actor;
import org.castafiore.workflow.InvalidActorInteractionException;
import org.castafiore.workflow.MultiplePossibleActionException;
import org.castafiore.workflow.NoPossibleActionException;
import org.castafiore.workflow.NoSuchActionException;
import org.castafiore.workflow.State;
import org.castafiore.workflow.Workflow;
import org.castafiore.workflow.WorkflowException;

public class SpringWorkflow implements Workflow {

	private String name;

	private List<State> states = new ArrayList<State>();

	private String currentState;

	private Map<Object, Object> context = new HashMap<Object, Object>();

	public Action execute(Actor actor) throws MultiplePossibleActionException,
			NoPossibleActionException {
		State state = getCurrentState();
		List<Action> actions = state.getActions(actor);
		if (actions.size() > 0) {
			throw new MultiplePossibleActionException("There are " 	+ actions.size() + " actions " + " for user "+ actor.getUsername() + " in workflow " + name);
		} else if (actions.size() == 0) {
			throw new NoPossibleActionException("There are " + actions.size() + " action " + " for user " + actor.getUsername()	+ " in workflow " + name);
		}
		
		Action action = actions.get(0);
		if(action.canExecute(actor))
			action.doAction(actor, getContext());
		else
			throw new InvalidActorInteractionException("The actor " + actor.getUsername() + " cannot execute the action " + action.getName() + " in state " + state.getName() + " in workflow " + name);
		
		return action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getCurrentState() {
		
		if(currentState != null){
			currentState = states.get(0).getName();
			return states.get(0);
		}else{
			for(State state : states){
				if(currentState.equals(state.getName())){
					return state;
				}
			}
		}
		throw new WorkflowException("Fatal: current state of the workflow cannot be found");
	}

	public void reStart() {
		
	}

	public Action execute(Actor actor, String actionName)throws NoSuchActionException {
		State state = getCurrentState();
		List<Action> actions = state.getActions(actor);
		if (actions.size() == 0) {
			throw new NoSuchActionException("There are " + actions.size() + " action " + " for user " + actor.getUsername()	+ " in workflow " + name);
		}
		
		for(Action action : actions){
			if(action.getName().equals(actionName)){
				if(action.canExecute(actor)){
					action.doAction(actor, getContext());
					return action;
				}
				else
					throw new InvalidActorInteractionException("The actor " + actor.getUsername() + " cannot execute the action " + action.getName() + " in state " + state.getName() + " in workflow " + name);
			}
		}	
		throw new NoSuchActionException("There are " + actions.size() + " action " + " for user " + actor.getUsername()	+ " in workflow " + name);
	}

	public List<State> getStates() {
		return states;
	}

	public void setStates(List<State> states) {
		this.states = states;
	}

	public Map<Object, Object> getContext() {
		return context;
	}

	public void setContext(Map<Object, Object> context) {
		this.context = context;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
}
