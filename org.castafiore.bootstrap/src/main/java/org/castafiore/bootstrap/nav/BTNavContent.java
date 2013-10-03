package org.castafiore.bootstrap.nav;

import org.castafiore.ui.Container;
import org.castafiore.ui.EXContainer;
import org.castafiore.ui.tab.TabModel;
import org.castafiore.ui.tab.TabPanel;

public abstract class BTNavContent extends EXContainer{

	public BTNavContent(String name, String tagName) {
		super(name, tagName);

	}
	
	
	public abstract Container showTab(TabPanel panel, TabModel model, int index);
	
	
	

}
