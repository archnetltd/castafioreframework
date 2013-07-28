package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.designer.helpers.TemplateHelper;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.scripting.TemplateComponent;

public class PopulateEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		Container cont = container.getAncestorOfType(EXConfigVerticalBar.class).getDescendentOfType(EXConfigItem.class).getContainer();
		if(cont instanceof TemplateComponent){
			TemplateHelper.populateTemplate((TemplateComponent)cont);
			container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).getParent().getAncestorOfType(EXConfigVerticalBar.class).refresh();
			container.getAncestorOfType(EXConfigItem.class).getAncestorOfType(EXConfigVerticalBar.class).getParent().getAncestorOfType(EXConfigVerticalBar.class).open();
		}
		
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
