package org.castafiore.shoppingmall.crm;

import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXOverLayablePanel extends EXPanel implements PopupContainer{

	public EXOverLayablePanel(String name, String title) {
		super(name, title);
		addChild(new EXOverlayPopupPlaceHolder("popup"));
	}

	public EXOverLayablePanel(String name) {
		super(name);
		addChild(new EXOverlayPopupPlaceHolder("popup"));
	}

	@Override
	public void addPopup(Container popup) {
		getChild("popup").addChild(popup);
	}

}
