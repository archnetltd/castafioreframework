package org.castafiore.designer;

import org.castafiore.designer.config.ui.EXMiniConfig;

public class EXMiniDesigner extends EXDesigner{

	public EXMiniDesigner() {
		super();
		setTemplateLocation("designer/sliced/minidesigner.xhtml");
		setName("EXProductOption");
		addChild(new EXMiniConfig("miniConfigs"));
	}

}
