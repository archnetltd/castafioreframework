package org.castafiore.shoppingmall;

import java.io.Serializable;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.wfs.types.Directory;

public interface ShoppingMall extends Serializable{
	
	
	public final static String ROOT = "/root/Shopping Mall";
	
	/**
	 * return featured products
	 * @return
	 */
	public List<Product> getFeatureProducts(int max);
	
	/**
	 * return recent products
	 * @return
	 */
	public List<Product> getRecentProducts(int max);
	
	
	
	public List<Product> getAlsoPurchased(String productCode);
	
	
	/**
	 * search products
	 * @param search
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<Product> searchProducts(String search, int page, int pageSize);
	
	public int countSearch(String search);
	
	/**
	 * returns a specific product
	 * @param productPath
	 * @return
	 */
	public Product getProduct(String productPath);

	public Directory getRootCategory();
	
	
	
	public Merchant createMerchant(String name);
	
	public Merchant getMerchant(String username);
	
	public List<Merchant> getMerchants();
	
	
	public List<Merchant> searchMerchants(String searchTerm, int page, int pageSize);
	
	
	public int countMerchants(String search);
	
	public List<Product> getProductsWithProperty(String name, String value, String merchant);

}
