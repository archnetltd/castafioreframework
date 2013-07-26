package org.castafiore.shoppingmall.ng.v2;

import org.castafiore.shoppingmall.ng.EXProductItemNG;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.wfs.Util;

public class EXProductItem extends EXProductItemNG implements Event{

	public EXProductItem(String name, String title) {
		super(name);
		setTemplateLocation("templates/v2/EXProductItem.xhtml");
		addClass("pitem");
		super.getChildren().clear();
		addChild(new EXContainer("widgetTitle", "span").addClass("title").setText(title));
		
		addChild(new EXContainer("price","span").setAttribute("style", "color: red; font-size: 13px;font-weight:bold"));
		
		addChild(new EXContainer("title","label").addClass("ptitle").addEvent(this, CLICK));
		addChild(new EXContainer("summary","div").addClass("pdescription"));
		addChild(new EXContainer("weight","label").addClass("pweight"));
		addChild(new EXInput("qty"));
		addChild(new EXContainer("image", "img").setStyle("width", "128px").setStyle("height", "128px").addEvent(this, CLICK));
		getDescendentOfType(EXInput.class).setValue("1");
		addChild(new EXContainer("addQty","a").setAttribute("href", "#aq").addEvent(this, CLICK).setText( "<img src=\"emimg/item/images/plus.png\"></img>"));
		addChild(new EXContainer("delQty","a").setAttribute("href", "#dq").addEvent(this, CLICK).setText( "<img src=\"emimg/item/images/minus.png\"></img>"));
		addChild(new EXContainer("addToCart","img").setAttribute("title", "Add to cart").addEvent(this, CLICK).setAttribute("src", "http://lavenezamassas.com.br/massas/imgs/cesta.png").setStyle("width", "24px").setStyle("height", "24px"));
		addChild(new EXContainer("thumbUp","img").setStyle("display", "none").addEvent(this, CLICK).setAttribute("src", "icons-2/fugue/icons/thumb-up.png").setStyle("width", "24px").setStyle("height", "24px"));
		addChild(new EXContainer("thumbDown","img").setStyle("display", "none").addEvent(this, CLICK).setAttribute("src", "icons-2/fugue/icons/thumb.png").setStyle("width", "24px").setStyle("height", "24px"));
		addChild(new EXContainer("addToWishList","label").setText("Add to my shopping list").setStyle("display", "none").addEvent(this, CLICK));
		addChild(new EXContainer("merchant", "a").setAttribute("href", "#G").addEvent(this, CLICK));
		//setTemplateLocation("templates/ng/EXProductItemNG"+name+".xhtml");
		if(Util.getRemoteUser() != null){
			getChild("addToWishList").setDisplay(true);
			getChild("thumbUp").setDisplay(true);
			getChild("thumbDown").setDisplay(true);
			
		}
		
	}
	

}
