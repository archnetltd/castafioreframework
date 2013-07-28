package org.castafiore.workflow.ui.designable;

import org.castafiore.designer.newportal.EXNewPortal;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;

public class EXNewWorkflow extends EXNewPortal{

	public EXNewWorkflow(String name) {
		super(name);
		super.getChildren().clear();
		addChild(new EXContainer("body", "div").addClass("ui-widget-content").addChild(new EXWorkflowSelectFolderBody("EXWorkflowSelectFolderBody")));
		
		addChild(new EXButton("next", "Next").addEvent(this, Event.CLICK).setStyle("float", "right").setStyle("margin-top", "4px").setStyle("padding", "2px 20px"));
		addChild(new EXButton("back", "Back").addEvent(this, Event.CLICK).setStyle("float", "left").setStyle("margin-top", "4px").setStyle("padding", "2px 20px"));
	}

}
