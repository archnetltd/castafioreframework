package org.castafiore.shoppingmall.payment;

import java.math.BigDecimal;

import org.castafiore.ui.Container;

public interface PaymentMethod extends Container {
	
	public boolean confirm();
	
	public BigDecimal getNumberOfCreditsFor(BigDecimal credits);

}
