package org.castafiore.shoppingmall.ng.v2;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.ng.v2.pages.EXHome;
import org.castafiore.ui.ex.EXContainer;

public class EXBody extends EXContainer{

	public EXBody(String name) {
		super(name, "div");
		addClass("body");
		addChild(new EXContainer("left", "div").addClass("left").addChild(new EXMenu("menu")).addChild(new EXFacebookBox("fb")));
		addChild(new EXContainer("center", "div").addClass("center").addChild(new EXCatalogue()));
		addChild(new EXContainer("right", "div").addClass("right").addChild(new EXProductItem("mv", "Latest Product")).addChild(new EXProductItem("fav", "Featured Product").addClass("yellow")));
		
		
		Product np= MallUtil.getCurrentMall().getRecentProducts(1).get(0);
		EXProductItem nn = (EXProductItem)getChild("right").getChild("mv");
		nn.setProduct(np);
		
		List<Product> featured = MallUtil.getCurrentMall().getFeatureProducts(1);
		 nn = (EXProductItem)getChild("right").getChild("fav");
		 if(featured.size() > 0){
			 nn.setProduct(featured.get(0));
		 }else{
			 nn.setProduct(np);
		 }
		 
		 
		getDescendentOfType(EXCatalogue.class).search("recent", null);
	}
	
	

}
