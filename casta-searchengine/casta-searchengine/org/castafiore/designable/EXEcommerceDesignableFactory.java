package org.castafiore.designable;

import org.castafiore.ui.Container;

public class EXEcommerceDesignableFactory extends AbstractDesignableFactory{

	public EXEcommerceDesignableFactory() {
		super("EXEcommerceDesignableFactory");
		setText("Ecommerce");
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3041401847810868246L;

	@Override
	public String getCategory() {
		return "E Commerce";
	}

	@Override
	public Container getInstance() {
		return new EXEcommerce("myShop");
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{};
	}

	@Override
	public String getUniqueId() {
		return "ecommerce/shop";
	}

}
