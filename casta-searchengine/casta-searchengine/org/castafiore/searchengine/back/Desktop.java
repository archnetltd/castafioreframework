package org.castafiore.searchengine.back;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.panel.Panel;

public interface Desktop extends Container {

	public void addWindow(Panel window, boolean forceNew);
	
	public String[] getDefaultPositions();
	
	
	public void removeWindow(String name);
	
}
