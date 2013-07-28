package org.castafiore.designable;

import org.castafiore.ui.ex.EXApplication;

public class ECommerceApp extends EXApplication {

	public ECommerceApp() {
		super("ECommerceApp");
		addChild(new EXEcommerce(""));
	}

}
