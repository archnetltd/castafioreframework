package org.castafiore.ui.mac.events;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.mac.EXMacFinderColumn;

public class OpenColumnEvent implements Event {

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXMacFinderColumn.class).getParent(), "Opening page");
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getAncestorOfType(EXMacFinderColumn.class).openUsing(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
