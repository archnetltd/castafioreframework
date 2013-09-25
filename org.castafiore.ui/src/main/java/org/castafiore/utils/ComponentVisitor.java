/*
 * 
 */
package org.castafiore.utils;

import org.castafiore.ui.Container;

/**
 * 
 * Utility interface helps to visit the DOM and execute an action
 * 
 * @see ComponentUtil.iterateOverDescendentsOfType
 * @author Kureem Rossaye Since 1.0
 * 
 */
public interface ComponentVisitor {

	/**
	 * 
	 * @param root
	 *            - The root container to start visiting.
	 */
	public void doVisit(Container root);

}
