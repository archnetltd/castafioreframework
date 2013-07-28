package org.castafiore.finance.ui;

import org.castafiore.ui.ex.EXContainer;

public class EXOSApplication extends EXContainer{

	public EXOSApplication(String name) {
		super(name, "div");
		addClass("application").addClass("span-24");
		addChild(new EXOSApplicationHead());
		addChild(new EXOSApplicationBody());
	}
	
	public EXOSApplicationHead getHeader(){
		return getDescendentOfType(EXOSApplicationHead.class);
	}
	
	public EXOSApplicationBody getBody(){
		return getDescendentOfType(EXOSApplicationBody.class);
	}

}
