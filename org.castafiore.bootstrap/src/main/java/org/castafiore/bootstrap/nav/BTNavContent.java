package org.castafiore.bootstrap.nav;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.tab.TabModel;
import org.castafiore.ui.ex.tab.TabPanel;

public abstract class BTNavContent extends EXContainer{

	public BTNavContent(String name, String tagName) {
		super(name, tagName);

	}
	
	
	public abstract Container showTab(TabPanel panel, TabModel model, int index);
	
	
	

}
