package org.castafiore.workflow.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;
import org.castafiore.workflow.Action;
import org.castafiore.workflow.State;

public class EXState extends AbstractWorkflowContainer implements State, Draggable, Event {

	public EXState(String name) {
		super(name);
		addEvent(this, Event.END_DRAG);
	}

	@Override
	public String getColor() {
		return getStyle("background-color");
		
	}



	@Override
	public int getValue() {
		 try{
			 return Integer.parseInt(getAttribute("value"));
			
		}catch(Exception e){
			return 0;
		}
	}

	@Override
	public void setValue(int value) {
		setAttribute("value", value + "");
		
	}

	@Override
	public List<Action> getActions() {
		final List<Action> res = new ArrayList<Action>();
		ComponentUtil.iterateOverDescendentsOfType(this, EXAction.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				res.add((Action)c);
				
			}
		});
		return res;
	}
	
	@Override
	public JMap getDraggableOptions() {
		return new JMap();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		JMap opt = new JMap().put("x", container.getStyle("left"));
		opt.put("y", container.getStyle("top"));
		container.makeServerRequest(opt,this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		container.setStyle("left", request.get("x")).setStyle("top",request.get("y"));
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

	@Override
	public String getStateLabel() {
		return getLabel();
	}

}
