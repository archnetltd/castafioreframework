package org.castafiore.bootstrap.buttons;

import org.castafiore.ui.EXContainer;
import org.castafiore.ui.toolbar.ToolBar;
import org.castafiore.ui.toolbar.ToolBarItem;

public class BTToolBar extends EXContainer implements ToolBar{

	public BTToolBar(String name) {
		super(name, "div");
		addClass("btn-toolbar");
		
	}
	
	public BTToolBar addItem(ToolBarItem item){
		addChild(item);
		return this;
	}
}
