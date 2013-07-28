package org.racingtips.ui;

import org.castafiore.ui.ex.EXApplication;
import org.racingtips.ui.admin.EXAdmin;

public class EXRacingTipsAdmin extends EXApplication{

	public EXRacingTipsAdmin() {
		super("racingtipsadmin");
		try{
			addChild(new EXAdmin("admin"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
