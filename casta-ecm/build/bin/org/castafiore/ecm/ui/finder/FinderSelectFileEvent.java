package org.castafiore.ecm.ui.finder;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class FinderSelectFileEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXFinderContainer.class).getDescendentByName("finderContainer"));
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String absolutePath = container.getAttribute("file");
		//Directory dir = SpringUtil.getRepositoryService().getDirectory(absolutePath, Util.getRemoteUser());
		container.getAncestorOfType(EXFinderColumn.class).selectFile(absolutePath);
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
