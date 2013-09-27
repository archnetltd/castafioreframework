package org.castafiore.bootstrap.buttons;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.toolbar.ToolBarItem;

public class BTButton extends EXContainer implements ToolBarItem{

	private final static String[] STYLES = new String[] { "btn-normal",
			"btn-primary", "btn-danger", "btn-warning", "btn-success",
			"btn-info", "btn-inverse" };

	public BTButton(String name, String label) {
		super(name, "button");
		addClass("btn");
		setText(label);
	}

	public BTButton setButtonSize(ButtonSize size){
		if(size == ButtonSize.SMALL){
			setSmall();
		}else if(size == ButtonSize.MINI){
			setMini();
		}else{
			setLarge();
		}
		return this;
	}
	
	protected BTButton setLarge() {
		removeClass("btn-small").removeClass("btn-mini").addClass("btn-large");
		return this;
	}

	protected BTButton setSmall() {
		removeClass("btn-large").removeClass("btn-mini").addClass("btn-small");
		return this;
	}

	protected BTButton setMini() {
		removeClass("btn-mini").removeClass("btn-large").addClass("btn-small");
		return this;
	}

	private BTButton applyStyle(String style) {
		for (String s : STYLES) {
			super.removeClass(s);
		}
		addClass(style);
		return this;
	}
	
	public BTButton setButtonType(ButtonType type){
		String val = type.getValue();
		return applyStyle("btn-" + val);
	}

}
