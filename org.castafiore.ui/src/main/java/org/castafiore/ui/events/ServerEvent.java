package org.castafiore.ui.events;

import java.util.Map;

import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;

public abstract class ServerEvent implements Event{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6727443002106806535L;



	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
	}

	

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		
	}

}
