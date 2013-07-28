package org.castafiore.ecm.ui.finder;

import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.File;

public class FinderDeleteFileEvent implements Event {

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this, "Do you really want to delete this file?");
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String selectedFile = container.getAncestorOfType(EXFinderContainer.class).getAttribute("selectedFile");
		File f = SpringUtil.getRepositoryService().getFile(selectedFile, Util.getRemoteUser());
		File parent = f.getParent();
		f.remove();
		parent.save();
		
		Container c = container.getAncestorOfType(EXFinder.class).getDescendentByName(selectedFile);
		c.setDisplay(false);
		//SpringUtil.getRepositoryService().delete(SpringUtil.getRepositoryService().getFile(selectedFile, Util.getRemoteUser()), Util.getRemoteUser());
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
