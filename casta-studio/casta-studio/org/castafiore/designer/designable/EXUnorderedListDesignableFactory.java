package org.castafiore.designer.designable;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ui.Container;

public class EXUnorderedListDesignableFactory extends AbstractDesignableFactory {

	public EXUnorderedListDesignableFactory() {
		super("EXUnorderedListDesignableFactory");
		// TODO Auto-generated constructor stub
	}


	@Override
	public String getCategory() {
		return "list";
	}

	@Override
	public Container getInstance() {
		return new EXDroppableUnorderList();
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		if(attributeName.equals("Direction")){
			if(attributeValue.equalsIgnoreCase("Vertical")){
				for(Container cc : c.getChildren()){
					cc.setStyle("float", "none");
				}
			}else if(attributeValue.equalsIgnoreCase("Horizontal")){
				for(Container cc : c.getChildren()){
					cc.setStyle("float", "left");
				}
			}
		}
		
	}

	@Override
	@ConfigValues(configs={@ConfigValue(attribute="Direction",values={"Vertical", "Horizontal"})})
	public String[] getRequiredAttributes() {
		return new String[]{"Direction"};
	}

	@Override
	public String getUniqueId() {
		return "core:ul";
	}

}
