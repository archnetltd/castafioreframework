package org.castafiore.shoppingmall.product.ui.tab;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;

public interface ProductEditForm extends Container{
	
	public  Container setProduct(Product product);
	
	
	public  void fillProduct(Product product);

}
