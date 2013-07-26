package org.castafiore.shoppingmall.payment;

import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXBillingInformation extends EXXHTMLFragment {
	
	static String[] labels = new String[]{"firstName",
			"lastName",
			"addressLine1",
			"addressLine2",
			"city",
			"zipCode",
			"country",
			"zipCode",
			"email",
			"organization",
			"primaryPhone",
			"ext"
		};

	public EXBillingInformation(String name) {
		super(name, "templates/EXBillingInformation.xhtml");
		
		for(String s : labels){
			addChild(new EXInput(s));
		}
		
	}

}
