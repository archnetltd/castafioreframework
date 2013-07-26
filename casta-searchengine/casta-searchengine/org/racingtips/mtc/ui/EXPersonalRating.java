package org.racingtips.mtc.ui;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.RaceCardItem;
import org.racingtips.ui.EXSmallLogin;

public class EXPersonalRating extends EXContainer{

	public EXPersonalRating(String name) {
		super(name, "div");
		addChild(new EXPersonalRating_(""));
		
		addChild(new EXSmallLogin().setDisplay(false));
		if(Util.getRemoteUser() == null){
			getChildByIndex(0).setDisplay(false);
			getChildByIndex(1).setDisplay(true);
		}
	}
	
	
	public void setItem(RaceCardItem item){
		getDescendentOfType(EXPersonalRating_.class).setItem(item);
	}

}
