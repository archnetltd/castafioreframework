package org.castafiore.designer.script.ui;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class SaveScriptEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXScriptEditor.class)).makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXScriptEditor editor = container.getAncestorOfType(EXScriptEditor.class);
		editor.saveCurrentScript();
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
