package com.eliensons.ui.sales.fontend;

import org.castafiore.designable.EXCatalogue;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.product.EXProductItem;
import org.castafiore.designable.product.EXProductResultBar;
import org.castafiore.ui.Container;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.utils.ComponentVisitor;

import com.eliensons.ui.sales.EXElieNSonsSales;

public class EXElieNSonsFrontEnd extends EXElieNSonsSales{

	public EXElieNSonsFrontEnd(String name)throws Exception {
		super(name);
		this.getChildren().clear();
		
		addChild(new EXFrontEndMiniCart("cart"));
		getDescendentOfType(EXMiniCart.class).setTemplateLocation("templates/EXElieNSonsMiniCart.xhtml");
		addChild(new EXCatalogue(""));
		getDescendentOfType(EXCatalogue.class).search("fulltext", "", "elieandsons");
		getDescendentOfType(EXProductResultBar.class).setDisplay(false);
		
		
		ComponentUtil.iterateOverDescendentsOfType(getDescendentOfType(EXCatalogue.class), EXProductItem.class, new ComponentVisitor() {
			
			@Override
			public void doVisit(Container c) {
				c.getDescendentByName("productImageLink").setStyle("display", "none");
				c.setStyle("background", "white");
				c.getDescendentByName("addToCart").setText("Select Plan");
				c.getDescendentByName("productName").setStyle("font-size", "20px");
				c.setStyle("padding", "0");
				c.setStyle("margin", "8px 0");
				
			}
		});
	}

}
