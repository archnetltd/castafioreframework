package org.castafiore.designer.newportal.body;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;

public abstract class AbstractWizardBody extends EXContainer{

	public AbstractWizardBody(String name) {
		super(name, "div");
		addClass("n-body");
	}
	
	
	
	public abstract AbstractWizardBody clickButton(Container button);

}
