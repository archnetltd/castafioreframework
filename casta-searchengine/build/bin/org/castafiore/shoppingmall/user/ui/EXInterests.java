package org.castafiore.shoppingmall.user.ui;

import org.castafiore.ui.ex.form.EXTextArea;

public class EXInterests extends BaseEXUserProfile {

	public EXInterests() {
		super("Interests", "templates/EXInterests.xhtml");
		addChild(new EXTextArea("interests"))
		.addChild(new EXTextArea("movies"))
		.addChild(new EXTextArea("music"))
		.addChild(new EXTextArea("books"));
	}

	

}
