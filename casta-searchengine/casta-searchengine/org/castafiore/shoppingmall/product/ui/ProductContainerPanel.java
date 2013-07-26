package org.castafiore.shoppingmall.product.ui;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;

public interface ProductContainerPanel extends Container {
	
	public void showProductList(int  state);
	
	public void showProductList();
	
	public void showProductEditCard(Product product);

}
