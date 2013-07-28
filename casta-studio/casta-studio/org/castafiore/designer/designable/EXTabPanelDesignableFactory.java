package org.castafiore.designer.designable;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppableTabPanel;
import org.castafiore.ui.Container;

public class EXTabPanelDesignableFactory extends AbstractDesignableFactory {

	public EXTabPanelDesignableFactory() {
		super("Tab Panel");
		setText("Tab Panel");

	}
	
	@Override
	public String getCategory() {
		return "Core";
	}

	@Override
	public Container getInstance() {
		return new EXDroppableTabPanel("Tab Panel");
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
		return "core:tabs";
	}


}
