package org.castafiore.searchengine.middle;

import org.castafiore.searchengine.left.EXActionPanel;
import org.castafiore.searchengine.right.EXAdvertisementPanel;
import org.castafiore.shoppingmall.merchant.EXMerchantRotatorPanel;
import org.castafiore.ui.ex.EXContainer;

public class EXBody extends EXContainer{

	public EXBody(String name) {
		super(name, "div");
		addClass("prepend-top");
		addChild(new EXActionPanel("ActionPanel").setDisplay(false))
		.addChild(new EXMerchantRotatorPanel("rotators"))
		.addChild(new EXWorkingSpace("WorkingSpace"))
		.addChild(new EXAdvertisementPanel("AdvertisementPanel"));
	}

}
