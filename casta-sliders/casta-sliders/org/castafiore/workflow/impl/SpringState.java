package org.castafiore.workflow.impl;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.workflow.Action;
import org.castafiore.workflow.Actor;
import org.castafiore.workflow.State;
import org.castafiore.workflow.Workflow;

public class SpringState implements State{
	
	private List<Action> actions = new ArrayList<Action>();
	
	private String name;

	public List<Action> getActions(Actor actor) {
		
		return null;
	}

	public List<Action> getActions() {
		
		return actions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	

}
