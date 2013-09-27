package org.castafiore.bootstrap.buttons;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.toolbar.ToolBar;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

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
