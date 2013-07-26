package org.castafiore.shoppingmall.product.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.user.ui.EXMerchantCardLink;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.iterator.FileIterator;
import org.castafiore.wfs.types.Link;

public class EXAdvProductListBarItem extends EXXHTMLFragment{

	public EXAdvProductListBarItem() {
		super("", "templates/EXAdvProductListBarItem.xhtml");
		//addClass("span-6");
		addClass("adv-product-list");
		addChild(new EXContainer("img", "img").addClass("span-2").addClass("first").addClass("last").setAttribute("src", "http://www.dinodictionary.com/images/d/unknown.gif"));
		addChild(new EXProductCardLink("title"));
		addChild(new EXMerchantCardLink("owner").addClass("loud"));
		addChild(new EXContainer("price", "label"));
		
	}
	
	public void setProduct(Product product){
		getDescendentOfType(EXProductCardLink.class).setProduct(product);
		getDescendentOfType(EXMerchantCardLink.class).setMerchantUsername(product.getProvidedBy());
		FileIterator<Link> iter = product.getImages();
		if(iter != null && iter.hasNext()){
			Link f = iter.next();
			String url = f.getUrl();
			getChild("img").setAttribute("src", url);
		}
		getChild("price").setText("Rs " + product.getTotalPrice().toPlainString());
		
	}

}
