package org.castafiore.ui.mac.item;

import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.EXContainer;

public class EXMacFieldItem extends EXContainer implements MacColumnItem{

	public EXMacFieldItem(String name, String label,StatefullComponent input) {
		super(name, "li");
		addChild(new EXContainer("", "span").addClass("ui-input-label").setText(label));
		addChild(input.addClass("ui-input"));
	}
	
	public StatefullComponent getInput(){
		return (StatefullComponent)getChildByIndex(1);
	}

}
