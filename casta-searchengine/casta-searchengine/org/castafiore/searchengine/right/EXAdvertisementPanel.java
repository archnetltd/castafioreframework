package org.castafiore.searchengine.right;

import org.castafiore.shoppingmall.product.ui.EXMiniCarts;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.shoppingmall.user.ui.EXLoginForm;
import org.castafiore.ui.ex.EXContainer;

public class EXAdvertisementPanel extends EXContainer implements MallLoginSensitive{

	public EXAdvertisementPanel(String name) {
		super(name, "div");
		addClass("malladvertisementpanel").addClass("span-6");
		addChild(new EXLoginForm("EXLoginForm"));
		addChild(new EXMiniCarts("minicarts"));
		//addChild(new EXCartInfo("EXCartInfo"));
		//getDescendentOfType(EXCartInfo.class));
	}

	@Override
	public void onLogin(String username) {
		getDescendentOfType(EXLoginForm.class).remove();
		
		//EXCreditsInfo info = new EXCreditsInfo("EXCreditsInfo");
		//addChild(info);
		//info.update();
		
		
	}

	
	
}
