package org.castafiore.shoppingmall.merchant;

import java.util.List;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;

public class EXMerchantRotatorPanel extends EXXHTMLFragment{

	public EXMerchantRotatorPanel(String name) {
		super(name, "templates/EXMerchantRotatorPanel.xhtml");
		addChild(new EXContainer("title", "h5"));
		addChild(new EXContainer("body", "div"));
		
		List merchants = SpringUtil.getRepositoryService().executeQuery(new QueryParameters().setEntity(Merchant.class), Util.getRemoteUser());
		for(Object o : merchants){
			Merchant m = (Merchant)o;
			EXMerchantRotator rotator = new EXMerchantRotator("");
			rotator.addMerchant(m);
			getChild("body").addChild(rotator);
		}
		
	}
	
	

}
