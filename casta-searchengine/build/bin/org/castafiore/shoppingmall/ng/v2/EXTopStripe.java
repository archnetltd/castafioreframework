package org.castafiore.shoppingmall.ng.v2;

import java.util.Map;

import org.castafiore.designable.checkout.EXRegisterUser;
import org.castafiore.shoppingmall.ng.v2.registrations.AgentRegistrationModel;
import org.castafiore.shoppingmall.ng.v2.registrations.MerchantRegistrationModel;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.ResourceUtil;

public class EXTopStripe extends EXContainer implements Event {

	public EXTopStripe(String name) {
		super(name, "div");
		setStyle("width", "100%").setStyle("height", "22px").setStyle("background-color", "steelblue").setStyle("text-align", "center");
		
		
		
		//addChild(new EXContainer("agent","a").setStyle("color", "white").setStyle("margin", "0 12px").setStyle("font-weight", "bold").setAttribute("href", "#").setText("Register as an Agent").addEvent(this, CLICK));
		addChild(new EXContainer("merchant","a").setStyle("color", "white").setStyle("margin", "0 12px").setStyle("font-weight", "bold").setAttribute("href", "#").setText("Click here to register as a merchant and get your products displayed here.").addEvent(this, CLICK));
		//addChild(new EXContainer("lawyer","a").setStyle("color", "white").setStyle("margin", "0 12px").setStyle("font-weight", "bold").setAttribute("href", "#").setText("Register as a lawyer").addEvent(this, CLICK));
		//addChild(new EXContainer("accountant","a").setStyle("color", "white").setStyle("margin", "0 12px").setStyle("font-weight", "bold").setAttribute("href", "#").setText("Register as an accountant").addEvent(this, CLICK));
		//addChild(new EXContainer("logistic","a").setStyle("color", "white").setStyle("margin", "0 12px").setStyle("font-weight", "bold").setAttribute("href", "#").setText("Register as a logistic provider").addEvent(this, CLICK));
		//addChild(new EXContainer("service","a").setStyle("color", "white").setStyle("margin", "0 12px").setStyle("font-weight", "bold").setAttribute("href", "#").setText("Register as a service provider").addEvent(this, CLICK));
		
		
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equalsIgnoreCase("agent")){
			EXAccordeonPanel panel = new EXAccordeonPanel("panel", new AgentRegistrationModel());
			
			container.getRoot().getDescendentOfType(PopupContainer.class).addPopup(panel);
		}else if(container.getName().equalsIgnoreCase("merchant")){
			EXAccordeonPanel panel = new EXAccordeonPanel("panel", new MerchantRegistrationModel());
			
			container.getRoot().getDescendentOfType(PopupContainer.class).addPopup(panel);
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
