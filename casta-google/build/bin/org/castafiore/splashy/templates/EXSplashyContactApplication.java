package org.castafiore.splashy.templates;

import org.castafiore.ui.ex.EXApplication;

public class EXSplashyContactApplication extends EXApplication{

	public EXSplashyContactApplication() {
		super("splashymessage");
		addChild(new EXSendSplashyMessage("splashymessage"));
	}

}
