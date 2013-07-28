package org.castafiore.designer.newportal.icons;

import org.castafiore.ui.ex.EXContainer;

public class EXBlankSite extends EXPortalIcon{

	public EXBlankSite() {
		super("BlankSite");
		getChild("img").setStyle("text-align", "center").addChild(new EXContainer("", "label").setText("Blank"));
		getChild("info").setText("Blank site");
	}

}
