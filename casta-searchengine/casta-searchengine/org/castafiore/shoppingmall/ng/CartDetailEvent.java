package org.castafiore.shoppingmall.ng;

import java.util.Map;

import org.castafiore.designable.EXCartDetail;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;

public class CartDetailEvent implements Event{

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXMiniCart cart = container.getAncestorOfType(EXMiniCart.class);
		EXCartDetail detail = new EXCartDetail("");
		detail.setStyle("width", "800px").setStyle("z-index", "3000");
		cart.getRoot().getDescendentOfType(PopupContainer.class).addPopup(detail);
		detail.init(cart);
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
