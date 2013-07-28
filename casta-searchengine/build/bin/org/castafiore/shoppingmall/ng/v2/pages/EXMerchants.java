package org.castafiore.shoppingmall.ng.v2.pages;

import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.ShoppingMall;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.ng.v2.EXMerchantItem;
import org.castafiore.ui.ex.EXContainer;

public class EXMerchants extends EXContainer {

	public EXMerchants() {
		super("EXMerchants","div");
		ShoppingMall mall = MallUtil.getCurrentMall();
		List<Merchant> merchants = mall.getMerchants();
		for(Merchant m : merchants){
			EXMerchantItem item = new EXMerchantItem("mi", m);
			addChild(item.setStyle("float", "left").setStyle("width", "180px"));
		}
	}

}
