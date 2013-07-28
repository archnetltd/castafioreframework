package org.castafiore.designer.help;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXDesignerHelp extends EXXHTMLFragment{

	public EXDesignerHelp() {
		super("help", "helptoc.html");
		
		addChild(new EXContainer("closeButton", "span").addClass("ui-icon").addClass("cloeButton").addEvent(CLOSE_HELP, Event.CLICK));
		
	}

	public final static Event CLOSE_HELP = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			container.makeServerRequest(this);
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			//container.getAncestorOfType(EXDesignerHelp.class).setDisplay(false);
			EXDesignerHelp help = container.getAncestorOfType(EXDesignerHelp.class);
			request.put("idToRemove", help.getId());
			container.getAncestorOfType(EXDesignerHelp.class).remove();
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			container.mergeCommand(new ClientProxy(".myHelpMask").remove());
			container.mergeCommand(new ClientProxy("#" + request.get("idToRemove")).remove());
			
		}
		
	};
}
