package org.castafiore.ui.mac.renderer;

import java.io.Serializable;

import org.castafiore.ui.mac.item.MacColumnItem;

public interface MacFinderColumnViewModel extends Serializable {
	
	public int size();
	
	public MacColumnItem getValueAt(int index);

}
