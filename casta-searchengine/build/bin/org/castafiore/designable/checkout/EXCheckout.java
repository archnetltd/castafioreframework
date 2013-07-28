package org.castafiore.designable.checkout;

import java.util.Map;

import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.js.Var;
import org.castafiore.wfs.Util;

public class EXCheckout extends EXContainer implements Event{

	public EXCheckout(String name) {
		super(name, "div");
		setStyle("margin-top", "30px");
		Container billing = new EXContainer("billing", "div").addClass("cart-widget");
		//<a role="button" class="ui-dialog-titlebar-close ui-corner-all" href="#" style="float:right"><span class="ui-icon ui-icon-closethick">close</span></a>
		billing.addChild(
				
				new EXContainer("head", "div").addChild(new EXContainer("", "h4").addClass("ui-widget-header").addClass("ui-corner-top").setText("Billing information").addChild(new EXContainer("close", "a").setStyle("float", "right").addEvent(this, CLICK).setStyleClass("ui-dialog-titlebar-close ui-corner-all").setText("<span class=\"ui-icon ui-icon-closethick\">close</span>"))));
		
	
		
		
		billing.addChild(new EXBillingInformation("").setStyle("width", "707px"));
		
		addChild(billing);
		
		if(Util.getRemoteUser() != null){
			billing.getDescendentOfType(EXBillingInformation.class).signIn(Util.getRemoteUser());
		}
		
		Container shipping = new EXContainer("shipping", "div").addClass("cart-widget");
		shipping.addChild(new EXContainer("head", "div").setText("<h4 class=\"ui-widget-header ui-corner-top\">Shipping information</h4>"));
		addChild(shipping);
		
		
		Container shippingMethod = new EXContainer("shippingMethod", "div").addClass("cart-widget");
		shippingMethod.addChild(new EXContainer("head", "div").setText("<h4 class=\"ui-widget-header ui-corner-top\">Shipping method</h4>"));
		addChild(shippingMethod);
		
		
		Container payment = new EXContainer("payment", "div").addClass("cart-widget");
		payment.addChild(new EXContainer("head", "div").setText("<h4 class=\"ui-widget-header ui-corner-top\">Payment information</h4>"));
		addChild(payment);
		
		
		Container review = new EXContainer("review", "div").addClass("cart-widget");
		review.addChild(new EXContainer("head", "div").setText("<h4 class=\"ui-widget-header ui-corner-top\">Review Order</h4>"));
		addChild(review);
		setStyle("width", "709px");
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		//container.makeServerRequest(new JMap().put("code", new Var("event.keyCode")), this);
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("close")){
			getParent().getChildren().clear();
			getParent().setRendered(false);
			
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.getAncestorOfType(EXCheckout.class).fadeOut(100);
		
	}

}
