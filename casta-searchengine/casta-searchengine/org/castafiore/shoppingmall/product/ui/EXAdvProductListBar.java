package org.castafiore.shoppingmall.product.ui;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.ex.EXContainer;

public class EXAdvProductListBar extends EXContainer{

	public EXAdvProductListBar(String name, String title, List<Product> products) {
		super(name, "div");
		addClass("malladvlist").addClass("span-6");
		addChild(new EXContainer("title", "h5").setText(title).addClass("title"));
		for(Product p : products){
			EXAdvProductListBarItem item = new EXAdvProductListBarItem();
			item.setProduct(p);
			addChild(item);
		}
	}
	
	
	public void setTitle(String title){
		getChild("title").setText(title);
	}
	
	
	public void setProducts(List<Product> products){
		String title= getChild("title").getText();
		this.getChildren().clear();
		setRendered(false);
		addChild(new EXContainer("title", "h5").setText(title).addClass("title"));
		for(Product p : products){
			EXAdvProductListBarItem item = new EXAdvProductListBarItem();
			item.setProduct(p);
			addChild(item);
		}
	}
	
	

}
