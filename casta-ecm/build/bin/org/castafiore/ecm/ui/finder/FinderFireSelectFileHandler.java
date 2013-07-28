package org.castafiore.ecm.ui.finder;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class FinderFireSelectFileHandler implements Event {

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXFinder.class)).makeServerRequest(this);
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		final String selectedFile = container.getAncestorOfType(EXFinderContainer.class).getAttribute("selectedFile");
		File f= SpringUtil.getRepositoryService().getFile(selectedFile, Util.getRemoteUser());
		container.getAncestorOfType(EXFinder.class).getHandler().onSelectFile(f, container.getAncestorOfType(EXFinder.class));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
