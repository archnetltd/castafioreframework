package org.castafiore.finance.ui;

import org.castafiore.ui.scripting.EXXHTMLFragment;

public class EXOSHeader extends EXXHTMLFragment{

	public EXOSHeader() {
		super("EXOSManager", "EXOSHeader.xhtml");
		addClass("os-header").addClass("span-25").setStyle("margin-top", "15px");
	}

}
