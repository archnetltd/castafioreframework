package com.eliensons.ui;

import org.castafiore.searchengine.back.OSApplication;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.ex.EXApplication;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.ui.ex.panel.EXPanel;

public class EXElienSonsAdmin extends OSApplication  implements PopupContainer{

	public EXElienSonsAdmin() {
		super();
		setName("eliensonsadmin");
		
	}
	
	
	public EXPanel getNewPanel(String title, Container body){
		
		EXPanel panel = new EXPanel("title", title);
		panel.setBody(body);
		
		return panel;
	}


	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
		
	}

}
