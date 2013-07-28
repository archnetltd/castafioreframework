package org.castafiore.designer.layout;

import org.castafiore.designable.layout.DesignableLayoutContainer;
import org.castafiore.ui.Container;

/**
 * This interface is applied on {@link DesignableLayoutContainer} that reacts when children properties is being configured<br>
 * This reaction occurs only when children is being configured in Designer
 * @author Kureem Rossaye
 *
 */
public interface ChildrenAttributeConfigurationListener extends DesignableLayoutContainer{
	
	public void applyAttribute(Container child,String name, String value);

}
