package org.castafiore.appstore;

import org.castafiore.appstore.ui.EXAppStore;
import org.castafiore.appstore.ui.EXRegisterApplication;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXApplication;

public class AppStoreApplication extends EXApplication {

	public AppStoreApplication() {
		super("appstore");
		try{
			SpringUtil.getSecurityService().login("kureem", "marijuana");
			addChild(new EXAppStore(""));
			
			addChild(new EXRegisterApplication(""));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
