package org.castafiore.ecm.ui.editor;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.js.JMap;

public class EditImageEvent implements Event{

	
	
	@Override
	public void ClientAction(ClientProxy container) {
		JMap options = new JMap();
		options.put("referrer", "splashy");
		options.put("image", container.getAttribute("src"));
		options.put("exit", "http://localhost:8080/casta-ui/os.jsp");
		//options.put("locktarget", true);
		
		ClientProxy callback = container.clone().executeFunction("pixlr.overlay.show", options);
		
		container.getScript("js1/pixlr.js", callback);
		
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
