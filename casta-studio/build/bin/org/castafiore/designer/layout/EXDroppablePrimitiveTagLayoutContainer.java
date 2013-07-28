package org.castafiore.designer.layout;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.events.OnDeactivateEvent;
import org.castafiore.designer.events.OnDropEvent;
import org.castafiore.designer.events.OnOutEvent;
import org.castafiore.designer.events.OnOverEvent;
import org.castafiore.ui.Container;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.layout.EXXYLayoutContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;

public class EXDroppablePrimitiveTagLayoutContainer extends EXXYLayoutContainer implements DesignableLayoutContainer{
	
	public EXDroppablePrimitiveTagLayoutContainer(String name, String tageName){
		super(name,tageName);
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
	} 
	
	public EXDroppablePrimitiveTagLayoutContainer(){
		super();
		addEvent(new OnOverEvent(), Event.DND_OVER);
		addEvent(new OnOutEvent(), Event.DND_OUT);
		addEvent(new OnDeactivateEvent(), Event.DND_DEACTIVATE);
		addEvent(new OnDropEvent(), Event.DND_DROP);
	}

	public String[] getAcceptClasses() {
		return new String[]{"components"};
	}

	public JMap getDroppableOptions() {
		return new JMap().put("greedy", "true");
	}

	public String getPossibleLayoutData(Container container) {
		return container.getName();
	}

	public void onAddComponent(Container component) {

	}
	
	@Override
	public void moveUp(Container component) {
		ComponentUtil.moveUp(component, this);
	}

	@Override
	public void moveDown(Container component) {
		ComponentUtil.moveDown(component, this);
	}

}
