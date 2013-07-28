package org.castafiore.ecm.ui.finder;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class FinderCloseEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.getAncestorOfType(EXFinder.class).fadeOut(100,
				container.clone().makeServerRequest(this));
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		EXFinder finder = container.getAncestorOfType(EXFinder.class);
		if(finder.getCloser() != null){
			finder.getCloser().onClose(finder);
		}
		container.getAncestorOfType(EXFinder.class).remove();
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
