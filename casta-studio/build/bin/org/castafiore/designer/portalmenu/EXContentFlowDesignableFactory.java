package org.castafiore.designer.portalmenu;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.designer.layout.EXDroppableXYLayoutContainer;
import org.castafiore.ui.Container;

public class EXContentFlowDesignableFactory extends AbstractDesignableFactory{
	public EXContentFlowDesignableFactory() {
		super("EXContentFlowDesignableFactory");
		setText("Content flow");
	}

	@Override
	public String getCategory() {
		return "Layout";
	}

	@Override
	public Container getInstance() {
		return new EXContentFlow("Content flow");
	}

	
	
	public void applyAttribute(Container c, String attributeName, String attributeValue){
		
		
	}
	
	public String getUniqueId() {
		return "core:flow";
	}

	@Override
	public String[] getRequiredAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
}
