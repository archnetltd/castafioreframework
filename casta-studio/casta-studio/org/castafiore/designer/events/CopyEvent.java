package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class CopyEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
			Container cont = container.getAncestorOfType(EXConfigItem.class).getContainer();
			container.getAncestorOfType(EXDesigner.class).copy(cont);
			
		}catch(Exception e){
			request.put("error", "true");
			throw new UIException(e);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
