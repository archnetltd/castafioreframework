package org.racingtips.ui;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXHome extends EXContainer{

	public EXHome() {
		super("EXHome", "div");
		//addChild(new EXTabs("tabs", "/root/users/racingtips/slider"));
		addChild(new EXXHTMLFragment("latesttopics", ResourceUtil.getDownloadURL("ecm", "/root/users/racingtips/articles/latesttopics.xhtml")));
	}

}
