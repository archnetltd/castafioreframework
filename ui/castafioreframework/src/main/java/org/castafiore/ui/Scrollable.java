/*
 * 
 */
package org.castafiore.ui;

public interface Scrollable extends Container {

	public final static int SCROLL_VERTICAL = 0;

	public final static int SCROLL_HORIZONTAL = 1;

	/**
	 * adds new page to the list.
	 */
	public void addPage();

	/**
	 * returns the direction of the scroll.<br>
	 * SCROLL_HORIZONTAL or SCROLL_VERTICAL
	 * 
	 * @return
	 */
	public int getDirections();

	/**
	 * Tells if scrollable functionality has been switched on
	 * 
	 * @return
	 */
	public boolean isScrollable();

}
