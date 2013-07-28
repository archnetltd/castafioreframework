package org.castafiore.shoppingmall;

import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.ui.Container;

public interface Catalogue extends Container{
	public void setProducts(List<Product> products, String skin);
	
	public void back();
	
	public void changePage();
	public void sort(String by);
	
	public void search(String notation, String skin);

}
