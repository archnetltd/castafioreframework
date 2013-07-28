package org.castafiore.splashy.templates;

import org.castafiore.ui.ex.EXApplication;

public class EXPreviewApplication extends EXApplication{

	public EXPreviewApplication() {
		super("splashypreview");
		addChild(new EXPreview("sd", null));
	}

}
