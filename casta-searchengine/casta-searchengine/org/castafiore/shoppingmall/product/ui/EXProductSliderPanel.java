package org.castafiore.shoppingmall.product.ui;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.ex.EXContainer;

public class EXProductSliderPanel extends EXContainer{

	public EXProductSliderPanel(String name) {
		super(name, "div");
		showPublic();
	}
	
	
	//show recent products
	//show featured products
	public void showPublic(){
		this.getChildren().clear();
		setRendered(false);
		EXProductSlider recent = new EXProductSlider("recent", "Recent products");
		addChild(recent);
		recent.setProducts(MallUtil.getCurrentMall().getRecentProducts(10));
		
		
		EXProductSlider featured = new EXProductSlider("featured", "Featured products");
		addChild(featured);
		featured.setProducts(MallUtil.getCurrentMall().getFeatureProducts(10));
	}

}
