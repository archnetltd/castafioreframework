package org.castafiore.shoppingmall.ng.v2.registrations;

import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXCompanyRelevantInformation extends EXXHTMLFragment{

	public EXCompanyRelevantInformation(String name) {
		super(name, "templates/v2/registrations/EXCompanyRelevantInformation.xhtml");
		
		
		
		
		addChild(new EXRichTextArea("company.description"));
	}

	
}
