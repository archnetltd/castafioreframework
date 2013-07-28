package org.castafiore.designer.portal;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ui.Container;

public class EXPageComponentDesignableFactory extends AbstractDesignableFactory {
	
	private final static String PAGE_PATH_ATTR = "pagePath";
	
	public EXPageComponentDesignableFactory() {
		super("EXPageComponentDesignableFactory");
		setText("Page Component");	
	}
	@Override
	public String getCategory() {
		return "Portal";
	}

	@Override
	public Container getInstance() {
		return new EXPageComponent();
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equalsIgnoreCase(PAGE_PATH_ATTR))
			((EXPageComponent)c).setPagePath(attributeValue);
		
	}

	@Override
	public String[] getRequiredAttributes() {
		return new String[]{PAGE_PATH_ATTR};
	}

	@Override
	public String getUniqueId() {
		return "portal:page-component";
	}

}
