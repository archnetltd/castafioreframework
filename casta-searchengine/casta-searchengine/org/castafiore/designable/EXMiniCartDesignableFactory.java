package org.castafiore.designable;

import org.castafiore.ui.Container;

public class EXMiniCartDesignableFactory extends AbstractDesignableFactory{
	
	public EXMiniCartDesignableFactory() {
		super("EXMiniCartDesignableFactory");
		setText("Shopping Cart");
		// TODO Auto-generated constructor stub
	}

	@Override
	public Container getInstance() {
		return new EXMiniCart("Shopping Cart");
	}

	public String getUniqueId() {
		return "ecommerce/shoppingcart";
	}

	@Override
	public String getCategory() {
		return "E Commerce";
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getRequiredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}


}
