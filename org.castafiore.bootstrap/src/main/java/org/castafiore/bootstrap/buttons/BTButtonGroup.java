package org.castafiore.bootstrap.buttons;

import org.castafiore.bootstrap.OrientationType;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

public class BTButtonGroup extends EXContainer implements ToolBarItem{

	public BTButtonGroup(String name) {
		super(name, "div");
		addClass("btn-group");
	}
	
	
	public BTButtonGroup addButton(BTButton btn){
		addChild(btn);
		return this;
	}
	
	public BTButtonGroup setOrientation(OrientationType o){
		if(o == OrientationType.VERTICAL)
			setVertical();
		else if(o == OrientationType.HORIZONTAL)
			setHorizontal();
		return this;
	}
	
	protected BTButtonGroup setVertical(){
		addClass("btn-group-vertical");
		return this;
	}
	
	
	protected BTButtonGroup setHorizontal(){
		removeClass("btn-group-vertical");
		return this;
	}
	
	public BTButtonGroup setJustified(boolean justified){
		if(justified)
			addClass("btn-group-justified");
		else
			removeClass("btn-group-justified");
		return this;
			
	}

}
