package org.racingtips.mtc.ui;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.RaceCardItem;
import org.racingtips.ui.EXSmallLogin;

public class EXPublicVote extends EXContainer {

	public final static String UNCHECKED = "<img src=\"icons-2/fugue/icons/ui-check-box-uncheck.png\">";
	public final static String CHECKED = "<img src=\"icons-2/fugue/icons/ui-check-box.png\">";
	public EXPublicVote(String name) {
		super(name, "div");
		addChild(new EXPublicVote_(""));
		
//		addChild(new EXSmallLogin().setDisplay(false));
//		if(Util.getRemoteUser() == null){
//			getChildByIndex(0).setDisplay(false);
//			getChildByIndex(1).setDisplay(true);
//		}
	}
	
	public void setItem(RaceCardItem item){
		getDescendentOfType(EXPublicVote_.class).setItem(item);
	}
	
	

}
