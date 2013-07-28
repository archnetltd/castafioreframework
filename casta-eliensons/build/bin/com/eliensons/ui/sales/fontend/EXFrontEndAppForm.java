package com.eliensons.ui.sales.fontend;

import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.ui.Container;

import com.eliensons.ui.plans.EXElieNSonsApplicationForm;
import com.eliensons.ui.sales.EXElieNSonsMiniCart;

public class EXFrontEndAppForm extends EXElieNSonsApplicationForm{

	public EXFrontEndAppForm() {
		super();
	}

	public EXFrontEndAppForm(EXElieNSonsMiniCart minicart) {
		super(minicart);
	}

	public EXFrontEndAppForm(String name, EXElieNSonsMiniCart minicart) {
		super(name, minicart);
	}
	
	public void ok(){
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		if(true){
			Container c =co.getChild("review");
			EXMiniCart cart = getRoot().getDescendentOfType(EXMiniCart.class);
			if(c.getChildren().size() ==1){
				c.addChild(new EXFrontEndOrderReview("", cart,0).setStyle("width", "697px"));
			}
			else
				c.getChildByIndex(1).setDisplay(true);
			setDisplay(false);
		}
	}

}
