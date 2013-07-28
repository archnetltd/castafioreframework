package org.castafiore.site;

import org.castafiore.ui.ex.EXApplication;

public class Youdo extends EXApplication{

	
	private CollectedUserDate userDate = new CollectedUserDate();
	
	public Youdo() {
		super("youdo");
		addChild(new YoudoPortal());
		
	}

	public CollectedUserDate getUserDate() {
		return userDate;
	}

	public void setUserDate(CollectedUserDate userDate) {
		this.userDate = userDate;
	}
	
	

}
