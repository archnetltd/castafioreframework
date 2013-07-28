package org.castafiore.designer.script.ui;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class OpenScriptEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXScriptEditor.class));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXScriptEditor editor = container.getAncestorOfType(EXScriptEditor.class);
		editor.openScript(container.getName());
		List<Container> children = container.getParent().getChildren();
		for(Container c : children){
			c.removeClass("ui-state-active");
			
		}
		container.addClass("ui-state-active");
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
