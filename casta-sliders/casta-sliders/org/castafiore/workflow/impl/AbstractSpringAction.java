package org.castafiore.workflow.impl;

import java.util.Map;

import org.castafiore.workflow.Action;
import org.castafiore.workflow.Actor;

public abstract class AbstractSpringAction implements Action{
	
	private String name;

	public abstract void doAction(Actor actor, Map<Object, Object> context);

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public abstract boolean canExecute(Actor actor) ;

	

}
