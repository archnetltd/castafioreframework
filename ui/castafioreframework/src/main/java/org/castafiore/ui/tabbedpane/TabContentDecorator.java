/*
 * 
 */
package org.castafiore.ui.tabbedpane;

import java.io.Serializable;

import org.castafiore.ui.Container;

/**
 * Interface for decorating content of a {@link TabPanel}
 * @author arossaye
 *
 */
public interface TabContentDecorator extends Serializable{
	
	/**
	 * Decorates the content
	 * @param contentContainer
	 */
	public void decorateContent(Container contentContainer);

}
