package org.castafiore.designer.newportal.icons;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXPortalIcon extends EXContainer implements Event{

	public EXPortalIcon(String name) {
		super(name, "div");
		addClass("template");
		addChild(new EXContainer("img", "div").addClass("img"));
		addChild(new EXContainer("info", "div").addClass("info"));
		addEvent(this, CLICK);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXPortalIcon.class));
		container.makeServerRequest(this);
		
	}
	
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		for(Container c : container.getParent().getChildren()){
			c.setStyle("background", "url('designer/newportal/empty.png')");
			c.setAttribute("selected", "false");
			c.setStyle("border", "none");
		}
		container.setStyle("background", "url('designer/newportal/emptypressed.png')");
		container.setAttribute("selected", "true");
		container.setStyle("border", "solid red");
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
	}

}
