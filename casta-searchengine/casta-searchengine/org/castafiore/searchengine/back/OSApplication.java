package org.castafiore.searchengine.back;

import org.castafiore.ui.ex.EXApplication;

public class OSApplication extends EXApplication{

	public OSApplication() {
		super("os");
		//addChild(new OS("OS"));
		
		addChild(new OSLoginForm("login"));
	}

}
