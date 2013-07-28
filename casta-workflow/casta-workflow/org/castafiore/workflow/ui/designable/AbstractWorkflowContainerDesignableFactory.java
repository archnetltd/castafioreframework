package org.castafiore.workflow.ui.designable;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.designable.AbstractDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.workflow.ui.AbstractWorkflowContainer;
import org.castafiore.workflow.ui.EXState;

public abstract class AbstractWorkflowContainerDesignableFactory extends AbstractDesignableFactory{

	public AbstractWorkflowContainerDesignableFactory(String name) {
		super(name);
		
	}

	@Override
	public String getCategory() {
		return "workflow";
	}

	

	@Override
	public void applyAttribute(Container c, String attributeName,
			String attributeValue) {
		c.setAttribute(attributeName, attributeValue);
		
		AbstractWorkflowContainer state = (AbstractWorkflowContainer)c;
		if(attributeName.equals("label")){
			state.setLabel(attributeValue);
		}else if(attributeName.equals("description")){
			state.setDescription(attributeValue);
		}else if(attributeName.equals("background-color")){
			state.setStyle("background-color", attributeValue);
		}else{
			applySpecificAttr(state, attributeName, attributeValue);
		}
		
	}
	
	@Override
	public String[] getRequiredAttributes() {
		String[] specific = getSpecificAttr();
		List<String> res = new ArrayList<String>();
		res.add("label");
		res.add("description");
		res.add("background-color");
		if(specific != null){
			for(String s : specific){
				res.add(s);
			}
		}
		return res.toArray(new String[res.size()]);
	}
	
	public abstract void applySpecificAttr(AbstractWorkflowContainer c, String attributeName, String attributeValue);
	
	
	public abstract String[] getSpecificAttr();

}
