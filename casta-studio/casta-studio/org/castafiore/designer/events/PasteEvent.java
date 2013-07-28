package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class PasteEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		try{
		Container cont = container.getAncestorOfType(EXConfigItem.class).getContainer();
		if(cont instanceof DesignableLayoutContainer){
			if(!container.getAncestorOfType(EXDesigner.class).paste((DesignableLayoutContainer)cont)){
				request.put("nocopy", "true");
			}
		}else{
			
			request.put("cannot", "true");
		}
		}catch(Exception e){
			request.put("error", "true");
			throw new UIException(e);
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("nocopy")){
			container.alert("Nothing copied yet");
		}else if(request.containsKey("nocopy")){
			container.alert("Cannot paste here");
		}
		
	}

}
