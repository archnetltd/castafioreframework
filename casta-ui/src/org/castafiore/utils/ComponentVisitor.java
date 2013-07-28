package org.castafiore.utils;

import org.castafiore.ui.Container;

public interface ComponentVisitor {
	
	public void doVisit(Container c);

}
