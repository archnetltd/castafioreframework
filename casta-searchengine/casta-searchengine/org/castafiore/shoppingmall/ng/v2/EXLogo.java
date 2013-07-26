package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.shoppingmall.ng.v2.pages.EXHome;
import org.castafiore.shoppingmall.ng.v2.pages.EXMerchants;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;

public class EXLogo extends EXContainer implements Event{

	public EXLogo(String name) {
		super(name, "div");
		addClass("logo");
		
		Container signIn = new EXContainer("", "div").addClass("signIn");
		
		addChild(signIn);
		signIn.addChild(new EXContainer("signIn", "a").setAttribute("href", "#s").setText("Product Categories"));
//		signIn.addChild(new EXContainer("", "span").setText("|"));
//		signIn.addChild(new EXContainer("register", "a").setAttribute("href", "#r").setText("Register").addEvent(this, Event.CLICK));
		addEvent(this, Event.CLICK);
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		container.getAncestorOfType(EXMall.class).goToPage(EXMerchants.class);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
