package org.castafiore.shoppingmall.orders;

import org.castafiore.ui.Container;
import org.castafiore.wfs.types.File;

public interface GUIReactor {
	
	public void react(Container source, String actor, String organization, File file, OrdersWorkflow caller);

}
