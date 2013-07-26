package org.castafiore.shoppingmall.user.ui.settings;

import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXContactSetting extends EXXHTMLFragment{

	public EXContactSetting() {
		super("EXContactSetting","templates/EXContactSetting.xhtml");
		
		addChild(new EXInput("firstName"))
		.addChild(new EXInput("lastName"))
		.addChild(new EXInput("phone"))
		.addChild(new EXInput("mobile"))
		.addChild(new EXInput("email"));
		
		addChild(new EXDeliveryOptions("delivery").addClass("EXDeliveryOptions"));
	}

}
