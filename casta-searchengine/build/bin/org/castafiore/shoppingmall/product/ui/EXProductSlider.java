package org.castafiore.shoppingmall.product.ui;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.js.JMap;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.types.Link;

public class EXProductSlider extends EXXHTMLFragment {

	public EXProductSlider(String name, String title) {
		super(name, "templates/EXProductSlider.xhtml");
		addClass("carousel");
		addChild(new EXContainer("title", "h5").setText(title));
		addChild(new EXContainer("images", "ul"));
		addScript("se-js/jquery.jcarousellite.min.js");
	}
	
	
	public void setProducts(List<Product> products){
		Container images = getChild("images");
		images.getChildren().clear();
		setRendered(false);
		for(Product p : products){
			Container li = new EXContainer("", "li");
			EXProductCardLink image = new EXProductCardLink("", "img");
			image.setAttribute("width", "150").setAttribute("height", "118");
			
			List<Link> links = p.getImages().toList();
			if(links.size() > 0){
				image.setAttribute("src", links.get(0).getUrl());
				image.setProduct(p);
				li.addChild(image);
				images.addChild(li);
			}
			
		}
	}
	
	
	
	public void onReady(ClientProxy proxy){
		if(getChild("images").getChildren().size() > 3){
			proxy.addMethod("jCarouselLite", new JMap().put("auto", 800).put("speed", 1000));
		}
	}

}
