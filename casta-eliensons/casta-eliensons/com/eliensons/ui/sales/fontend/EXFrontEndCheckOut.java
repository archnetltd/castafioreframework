package com.eliensons.ui.sales.fontend;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;
import com.eliensons.ui.sales.EXElieNSonsCheckout;
import com.eliensons.ui.sales.EXElieNSonsMiniCart;

public class EXFrontEndCheckOut extends EXElieNSonsCheckout {

	public EXFrontEndCheckOut(String name, EXElieNSonsMiniCart minicart) {
		super(name, minicart);
		this.getChildren().clear();
		setRendered(false);
		Container billing = new EXContainer("billing", "div").addClass("cart-widget");
		billing.addChild(new EXContainer("head", "div").addChild(new EXContainer("", "h4").addClass("ui-widget-header").addClass("ui-corner-top").setText("Billing information").addChild(new EXContainer("close", "a").setStyle("float", "right").addEvent(this, CLICK).setStyleClass("ui-dialog-titlebar-close ui-corner-all").setText("<span class=\"ui-icon ui-icon-closethick\">close</span>"))));
		billing.addChild(new EXFrontEndAppForm(minicart).setStyle("width", "707px"));
		addChild(billing);		
		Container review = new EXContainer("review", "div").addClass("cart-widget");
		review.addChild(new EXContainer("head", "div").setText("<h4 class=\"ui-widget-header ui-corner-top\">Review Order</h4>"));
		addChild(review);
	}

}
