package org.castafiore.shoppingmall.payment;

import java.math.BigDecimal;

import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXPayMobile extends EXXHTMLFragment implements PaymentMethod {

	public EXPayMobile(String name) {
		super(name,"templates/EXPayMobile.xhtml");
		addChild(new EXInput("authorizationCode"));
	}

	@Override
	public boolean confirm() {
		return true;
	}

	@Override
	public BigDecimal getNumberOfCreditsFor(BigDecimal credits) {
		return credits.subtract(new BigDecimal(10));
	}

	
	
	
	

}
