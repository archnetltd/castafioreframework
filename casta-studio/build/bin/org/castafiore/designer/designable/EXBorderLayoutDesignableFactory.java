package org.castafiore.designer.designable;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppableBorderLayoutContainer;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.ex.layout.EXBorderLayoutContainer;

public class EXBorderLayoutDesignableFactory extends AbstractDesignableFactory{

	public EXBorderLayoutDesignableFactory() {
		super("Border Layout");
		setText("Border Layout");
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		EXBorderLayoutContainer layout = new EXDroppableBorderLayoutContainer();
		layout.setName("Border Layout");
		//layout.getContainer(layout.TOP).setStyle("min-height", "120px");
		layout.setWidth(Dimension.parse("100%"));
		layout.getContainer(layout.CENTER).setStyle("min-width", "600px").setStyle("min-height", "500px");
		layout.getContainer(layout.BOTTOM).setStyle("min-height", "125px");
		
		
		return layout;
	}


	public String getUniqueId() {
		return "layout:borderlayout";
	}

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		
	}

	@Override
	public String[] getRequiredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

}
