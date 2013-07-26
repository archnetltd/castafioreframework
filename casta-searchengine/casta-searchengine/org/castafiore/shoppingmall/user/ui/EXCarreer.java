package org.castafiore.shoppingmall.user.ui;

import org.castafiore.ui.ex.form.EXTextArea;

public class EXCarreer extends BaseEXUserProfile {
	public EXCarreer() {
		super("Carreer", "templates/EXCarreer.xhtml");
		addChild(new EXTextArea("occupations"))
		.addChild(new EXTextArea("companies"))
		.addChild(new EXTextArea("schools"));
	}
}
