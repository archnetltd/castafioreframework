package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class MoveDownEvent implements Event {
	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
	}

	public boolean ServerAction(Container container,
			Map<String, String> request) throws UIException {
		
		Container cont = container.getAncestorOfType(EXConfigItem.class).getContainer();
		if(cont instanceof DesignableLayoutContainer){
			cont.getParent().getAncestorOfType(DesignableLayoutContainer.class).moveDown(cont);
		}else{
			cont.getAncestorOfType(DesignableLayoutContainer.class).moveDown(cont);
		}
		container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).getParent().getAncestorOfType(EXConfigVerticalBar.class).refresh();
		container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).getParent().getAncestorOfType(EXConfigVerticalBar.class).open();
		//container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).remove();
		return true;
	}

	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
