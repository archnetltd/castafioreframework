package org.castafiore.shoppingmall.ng.v2;

import org.castafiore.shoppingmall.product.ui.EXMiniCarts;
import org.castafiore.ui.ex.EXContainer;

public class EXHeader extends EXContainer{

	public EXHeader(String name) {
		super(name, "div");
		addClass("head");
		addChild(new EXContainer("left", "div").addClass("left").addChild(new EXLogo("logo")));
		addChild(new EXContainer("center", "div").addClass("center").addChild(new EXContainer("banner", "div").addClass("banner")).addChild(new EXSearch("search")) );
		addChild(new EXContainer("right", "div").addClass("right").addChild(new EXLogin("login")));
		//getChildByIndex(1).getChildByIndex(0).addChild(new EXMiniCarts("carts"));
	}
	
	
	
}
