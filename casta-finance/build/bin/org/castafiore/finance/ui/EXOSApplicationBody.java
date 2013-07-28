package org.castafiore.finance.ui;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public class EXOSApplicationBody extends EXContainer{

	public EXOSApplicationBody() {
		super("EXOSBody", "div");
		addClass("span-24").addClass("application-body").addClass("push-1");
		addChild(new EXContainer("left","div").addClass("span-5").addClass("left-col"));
		addChild(new EXContainer("workspace", "div").addClass("span-14").addClass("workspace"));
		addChild(new EXContainer("right","div").addClass("span-5").addClass("right-col"));
	}
	
	public Container getLeftColumn(){
		return getChild("left");
	}
	
	public Container getWorkspace(){
		return getChild("workspace");
	}
	
	public Container getRightColumn(){
		return getChild("right");
	}

}
