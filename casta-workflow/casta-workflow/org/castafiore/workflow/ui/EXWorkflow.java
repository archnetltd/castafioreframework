package org.castafiore.workflow.ui;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designer.portal.EXDesignablePortalContainer;
import org.castafiore.designer.portal.EXPortalContainerDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.wfs.Util;
import org.castafiore.workflow.Action;
import org.castafiore.workflow.Actor;
import org.castafiore.workflow.IllegalWorkflowStateException;
import org.castafiore.workflow.State;
import org.castafiore.workflow.Workflow;
import org.castafiore.workflow.ui.AbstractWorkflowContainer;

public class EXWorkflow extends EXDesignablePortalContainer implements Workflow{

	public EXWorkflow() {
		super();
		setAttribute("state", "0");
	}

	@Override
	public List<Action> getActions(Actor currrentActor, State currentState) {
		List<Action> acts = currentState.getActions();
		List<Action> res = new ArrayList<Action>();
		for(Action act : acts){
			if(act.getActor().equals(currrentActor)){
				res.add(act);
			}
		}
		return res;
	}

	@Override
	public List<Actor> getAllActors() {
		final List<Actor> res = new ArrayList<Actor>();
		ComponentUtil.iterateOverDescendentsOfType(this, EXActor.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				res.add((Actor)c);
				
			}
		});
		return res;
	}

	@Override
	public List<State> getAllStates() {
		final List<State> res = new ArrayList<State>();
		ComponentUtil.iterateOverDescendentsOfType(this, State.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				res.add((State)c);
				
			}
		});
		return res;
	}

	@Override
	public Actor getCurrentActor() {
		List<Actor>  actors = getAllActors();
		String loggedUser = Util.getRemoteUser();
		for(Actor a : actors){
			if(a.isUserAllowed(loggedUser)){
				return a;
			}
		}
		return null;
	}

	@Override
	public void onAddComponent(Container component) {
//		// TODO Auto-generated method stub
//		super.onAddComponent(component);
		
		if(component instanceof AbstractWorkflowContainer){
			((AbstractWorkflowContainer)component).onAddComponent(component);
		}
	}

	@Override
	public State getCurrentState() {
		int cur = Integer.parseInt(getAttribute("value"));
		for(State s : getAllStates()){
			if(s.getValue()== cur){
				return s;
			}
		}
		throw new IllegalWorkflowStateException("The workflow has entered an illegal state:" + cur);
	}

	@Override
	public String getDescription() {
		return getAttribute("description");
	}

	@Override
	public String getLabel() {
		return getAttribute("label");
	}

}
