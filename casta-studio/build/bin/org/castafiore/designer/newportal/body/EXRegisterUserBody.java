package org.castafiore.designer.newportal.body;

import org.castafiore.ui.Container;
import org.castafiore.ui.ex.dynaform.EXDynaformPanel;
import org.castafiore.ui.ex.form.EXInput;

public class EXRegisterUserBody extends AbstractWizardBody{

	public EXRegisterUserBody(String name) {
		super(name);
		
		
		
		
		
		
		
		
	}

	@Override
	public AbstractWizardBody clickButton(Container button) {
		if(button.getName().equalsIgnoreCase("next")){
			EXDynaformPanel panel = getDescendentOfType(EXDynaformPanel.class);
		
			
		}
		
		return null;
	}

}
