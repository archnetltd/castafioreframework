package org.castafiore.designer.config.ui;

import java.util.Map;

import org.castafiore.designer.designable.ConfigValues;
import org.castafiore.ui.Container;

public interface ConfigPanel extends Container{
	
	public void setContainer(Container container, String[] requiredAttributes,ConfigValues vals);

}
