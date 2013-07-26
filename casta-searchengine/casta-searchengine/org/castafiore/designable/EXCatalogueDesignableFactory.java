package org.castafiore.designable;

import org.castafiore.ui.Container;

public class EXCatalogueDesignableFactory extends AbstractDesignableFactory{

	public EXCatalogueDesignableFactory() {
		super("EXCatalogueDesignableFactory");
		setText("Catalogue");
	}

	@Override
	public String getCategory() {
		return "E Commerce";
	}

	@Override
	public Container getInstance() {
		return new org.castafiore.shoppingmall.ng.v2.EXCatalogue();
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equalsIgnoreCase("merchant")){
			c.setAttribute("merchant", attributeValue);
		}
		if(attributeName.equalsIgnoreCase("search")){
			c.setAttribute("search", attributeValue);
		}
		
		if(attributeName.equalsIgnoreCase("type")){
			c.setAttribute("type", attributeValue);
		}
		
		String merchant = c.getAttribute("merchant");
		String search = c.getAttribute("search");
		String type = c.getAttribute("type");
		
		if(org.castafiore.utils.StringUtil.isNotEmpty(merchant) && org.castafiore.utils.StringUtil.isNotEmpty(search)){
			((org.castafiore.shoppingmall.ng.v2.EXCatalogue)c).search(type, search, merchant,0,"title");
		}
		
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{"type","merchant", "search"};
	}

	@Override
	public String getUniqueId() {
		return "ecommerce/catalogue";
	}
}
