package org.castafiore.ui.interceptors;

import java.util.List;
import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class AsynchronousInterceptor implements Interceptor{
	
	private static Event R = new Event() {
		
		@Override
		public void Success(ClientProxy container, Map<String, String> request)
				throws UIException {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean ServerAction(Container container, Map<String, String> request)
				throws UIException {
			container.setRendered(false);
			List<Event> r = container.getEvents().get(Event.READY);
			container.setAttribute("done", "true");
			r.remove(this);
			return true;
		}
		
		@Override
		public void ClientAction(ClientProxy container) {
			container.setTimeout(container.clone().makeServerRequest(this), 3000);
			
		}
	};

	@Override
	public boolean onRender(Container container) {
		if("true".equals(container.getAttribute("done")) ){
			container.setAttribute("done", "false");
			
		}else{
			container.addEvent(R, Event.READY);
			for(Container c : container.getChildren()){
				
				c.flush(12031980);
			}
			
		}
		return false;
	}

	@Override
	public Interceptor next() {
		// TODO Auto-generated method stub
		return null;
	}

}
