package org.castafiore.searchengine;

import org.castafiore.shoppingmall.ng.v2.EXMall;
import org.castafiore.shoppingmall.ng.v2.EXTopStripe;
import org.castafiore.ui.ex.EXApplication;

public class EXSearchEngineApplication extends EXApplication{
	
	
	public EXSearchEngineApplication() {
		super("searchengine");
		
		try{
			//addChild(new EXMiniCarts("miniCarts"));
			//addChild(new EXMallHeaderNG("header").setStyle("position", "fixed").setStyle("width", "100%").setStyle("z-index", "10"));
			//addClass("container").setStyle("width", "970px");
			//addChild(new EXMallNG("mall").setStyle("position", "relative").setStyle("top", "188px").setStyle("z-index", "0"));
			addChild(new EXTopStripe("stripe"));
			addChild(new EXMall("mall"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	

//	@Override
//	public void refresh() {
//		
//		super.refresh();
//		if(getConfigContext().containsKey("m")){
//			getDescendentOfType(EXMall.class).showMerchantCard(getConfigContext().get("m").toString());
//		}
//	}
//
//
//
//	@Override
//	public void onRefresh() {
//		if(getChildren().size() == 0){
//			try{
//				addClass("container").setStyle("width", "970px");
//				addChild(new EXMall("mall"));
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		
//	}

	
}
