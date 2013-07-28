package org.castafiore.designer.events;

import java.util.Map;

import org.castafiore.designer.EXDesigner;
import org.castafiore.designer.script.ui.EXScriptEditor;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.panel.EXPanel;

public class ShowScriptEditor implements Event {

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXPanel panel = new EXPanel("panel");
		panel.setWidth(Dimension.parse("600px"));
		EXDesigner des = container.getAncestorOfType(EXDesigner.class);
		String portalPath = des.getCurrentDir() + "/" + des.getCurrentName();;
		EXScriptEditor editor = new EXScriptEditor("", portalPath,false);
		//editor.setHeight(Dimension.parse("600px"));
		editor.setStyle("width", "100%");
		panel.setBody(editor);
		container.getAncestorOfType(PopupContainer.class).addPopup(panel);
		panel.setTitle(editor.getAttribute("current"));
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
