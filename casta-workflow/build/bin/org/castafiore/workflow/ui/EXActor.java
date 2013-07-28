package org.castafiore.workflow.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.Draggable;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;
import org.castafiore.utils.StringUtil;
import org.castafiore.workflow.Actor;

public class EXActor extends AbstractWorkflowContainer implements Actor, Draggable, Event {

	public EXActor(String name) {
		super(name);
		addEvent(this, Event.END_DRAG);
	}

	@Override
	public boolean isUserAllowed(String username) {
		String users = getAttribute("users");
		
		String[] as = StringUtil.split(users, ",");
		for(String s : as){
			if(s.equals(username)){
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		String users = getAttribute("users");
		String other = ((Container)obj).getAttribute("users");
		return users.equals(other);
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

}
