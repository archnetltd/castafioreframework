package org.castafiore.ui.mac.events;

import java.io.Serializable;

import org.castafiore.ui.Container;
import org.castafiore.ui.mac.EXMacFinderColumn;

public interface OpenColumnEventHandler extends Serializable{
	
	
	/**
	 * Should return a freshly created {@link EXMacFinderColumn} to open adjacent to the {@link EXMacFinderColumn} containing the container containing the EventHandler
	 * @param caller
	 * @return
	 */
	public EXMacFinderColumn getColumn(Container caller);

}
