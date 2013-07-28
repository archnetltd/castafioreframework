package org.castafiore.designer.designable.datarepeater;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.designable.ConfigValue;
import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;

public class EXPlugableItemDesignableFactory extends AbstractDesignableFactory{

	public EXPlugableItemDesignableFactory() {
		super("EXPlugableItemDesignableFactory");
		setText("Plugable Item");
	}

	@Override
	public String getCategory() {
		return "Core";
	}

	@Override
	public Container getInstance() {
		return new EXPlugableComponent("Component");
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		c.setAttribute(attributeName,attributeValue);
		if(attributeName.equalsIgnoreCase("componentPath")){
			((EXPlugableComponent)c).setComponentPath(attributeValue);
		}
		
	}

	@Override
	@ConfigValues(configs={@ConfigValue(attribute="componentPath",values="search:pages")})
	public String[] getRequiredAttributes() {
		return new String[]{"componentPath"};
	}

	@Override
	public String getUniqueId() {
		return "core:pluggablecomponent";
	}
}
