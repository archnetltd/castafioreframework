package org.castafiore.searchengine;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

// TODO: Auto-generated Javadoc
/**
 * The Interface EventDispatcher.
 */
public interface EventDispatcher extends Container{
	
	
	/** The Constant DISPATCHER. */
	public final static Event DISPATCHER = new Event(){

		@Override
		public void ClientAction(ClientProxy container) {
			
			//ClientProxy p = new ClientProxy("#mainLoading").setStyle("display", "block");
			
			container.mask().makeServerRequest(this);
			
		}

		@Override
		public boolean ServerAction(Container container,
				Map<String, String> request) throws UIException {
			container.getAncestorOfType(EventDispatcher.class).executeAction(container);
			return true;
		}

		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			ClientProxy p = new ClientProxy("#mainLoading").setStyle("display", "none");
			container.mergeCommand(p);
			
		}
		
	};
	
	/**
	 * Execute action.
	 *
	 * @param source the source
	 */
	public void executeAction(Container source);

}
