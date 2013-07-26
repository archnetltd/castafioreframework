package org.racingtips.ui;

import org.castafiore.ui.UIException;

public class EXSmallLogin extends EXLogin{
	
	
	public EXSmallLogin() {
		super();
		setTemplateLocation("templates/racingtips/EXSmallLogin.xhtml");
		getChild("username").setStyle("width", "200px");
		getChild("password").setStyle("width", "200px");
	}

	@Override
	public void onLogin(String username) {
		try{
			getParent().getChildByIndex(0).setDisplay(true);
			setDisplay(false);
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

}
