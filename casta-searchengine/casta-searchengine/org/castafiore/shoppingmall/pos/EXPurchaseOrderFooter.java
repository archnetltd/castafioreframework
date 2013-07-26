package org.castafiore.shoppingmall.pos;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXPurchaseOrderFooter extends EXXHTMLFragment{

	public EXPurchaseOrderFooter(String name) {
		super(name, "templates/pos/EXPurchaseOrderFooter.xhtml");
		addChild(new EXContainer("numItem", "span"));
		
		addChild(new EXContainer("msubTotal", "span"));
		addChild(new EXContainer("mtotal", "span"));
	}
	
	public void setOrder(Order order){
		Merchant m = MallUtil.getCurrentMerchant();
		String currency = m.getCurrency();
		getChild("msubTotal").setText(StringUtil.toCurrency(currency, order.getSubTotal()));
		getChild("mtotal").setText(StringUtil.toCurrency(currency, order.getTotal()));
	}

}
