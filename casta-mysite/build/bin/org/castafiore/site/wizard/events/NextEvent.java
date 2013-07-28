package org.castafiore.site.wizard.events;

import java.util.Map;

import org.castafiore.searchengine.back.OS;
import org.castafiore.security.User;
import org.castafiore.site.wizard.GetStartedPage;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class NextEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		User u  = container.getAncestorOfType(GetStartedPage.class).moveNext();
		if(u != null){
			//container.getAncestorOfType(YoudoPortal.class).go
			Container root = container.getRoot();
			root.getChildren().clear();
			root.setRendered(false);
			root.addChild(new OS("OS", u));
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
//		if(request.containsKey("redirect")){
//			container.appendJSFragment("window.location = 'webos.jsp'");
//			
//			
//		}
		
	}

}
