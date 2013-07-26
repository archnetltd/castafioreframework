package org.castafiore.shoppingmall.ng.v2.registrations;

import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXTermsAndConditions extends EXXHTMLFragment{

	public EXTermsAndConditions(String name) {
		super(name, "templates/v2/registrations/EXTermsAndConditions.xhtml");
		addChild(new EXCheckBox("agree"));
		
	}

}
