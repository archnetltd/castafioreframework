package org.castafiore.workflow.ui;

import java.util.List;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.workflow.Action;
import org.castafiore.workflow.Actor;
import org.castafiore.workflow.State;
import org.castafiore.workflow.Workflow;

public class EXWorkflowManager extends EXContainer{


	
	private Workflow wf_;
	
	public EXWorkflowManager(String name,  Workflow wf) {
		super(name, "div");
		
		
		this.wf_ = wf;
		
		
		EXGrid grid = new EXGrid("", 3, 1);
		grid.getCell(0, 0).setStyle("width", "300px");
		grid.getCell(1, 0).setStyle("width", "100px");
		
		grid.getCell(0, 0).addChild(new EXContainer("label", "label").setText(wf.getLabel()));
		grid.getCell(0, 0).addChild(new EXContainer("desc", "p").setText(wf.getDescription()));
		
		State state = wf_.getCurrentState();
		
		grid.getCell(1, 0).addChild(new EXContainer("label", "label").setText(state.getStateLabel()));
		grid.getCell(1, 0).addChild(new EXContainer("desc", "p").setText(state.getDescription()));
		Actor actor = wf_.getCurrentActor();
		List<Action> actions = wf_.getActions(actor, state);
		
		for(Action act : actions){
			
			EXButton btn =new EXButton(act.getName(), act.getLabel());
			grid.getCell(2, 0).addChild(btn);
			
			
		}
	}
	
	
	

}
