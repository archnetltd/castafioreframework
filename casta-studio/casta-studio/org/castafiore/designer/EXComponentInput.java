package org.castafiore.designer;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXTextArea;

public class EXComponentInput extends EXTextArea  implements Event{

	public EXComponentInput(String name) {
		super(name);
		setStyle("height", "25px");
		addEvent(this, CLICK);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		getAncestorOfType(PopupContainer.class).addPopup(new EXMiniDesigner());
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
		
	}

}
