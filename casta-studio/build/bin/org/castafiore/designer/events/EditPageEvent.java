package org.castafiore.designer.events;

import java.util.List;
import java.util.Map;

import org.castafiore.designable.portal.PageContainer;
import org.castafiore.designer.config.EXConfigVerticalBar;
import org.castafiore.designer.config.ui.EXConfigItem;
import org.castafiore.ui.Application;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class EditPageEvent implements Event{


	public void ClientAction(ClientProxy container) {
		container.mask();
		container.makeServerRequest(this);
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXConfigItem configItem = container.getAncestorOfType(EXConfigItem.class);
		
		PageContainer pg = container.getAncestorOfType(Application.class).getDescendentOfType(PageContainer.class);
		
		
		List<Container> containers = configItem.getAncestorOfType(EXConfigVerticalBar.class).getParent().getAncestorOfType(EXConfigVerticalBar.class).getChildByIndex(0).getChildren();
		for(Container c : containers){
			if(c instanceof EXConfigVerticalBar){
				c.getChildByIndex(0).setStyle("border", "solid 1px black");
			}
		}
		
		configItem.getParent().setStyle("border", "double steelBlue");
		
		pg.setPage(configItem.container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
