package org.castafiore.searchengine;

import org.castafiore.inventory.BInventory;
import org.castafiore.security.ui.EXLoginForm;
import org.castafiore.security.ui.OnLoginHandler;
import org.castafiore.ui.Application;
import org.castafiore.ui.ex.EXApplication;

public class MallAdmin extends EXApplication implements OnLoginHandler{

	public MallAdmin() {
		
		
		super("malladmin");
//		try
//		{
//			SpringUtil.getSecurityService().login("previewmerchant", "previewmerchant");
//		}catch(Exception e){
//			try{
//				SpringUtil.getSecurityService().login("previewmerchant", "previewmerchant");
//			}catch(Exception ee){
//				
//			}
//			e.printStackTrace();
//		}
		
		addChild(new EXLoginForm("loginForm").addOnLoginHandler(this));
		
		//addChild(new BInventory());
	}

	@Override
	public void onLogin(Application app, String username) {
		this.getChildren().clear();
		setRendered(false);
		addChild(new BInventory());
		
		
	}

}
