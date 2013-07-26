package org.castafiore.shoppingmall.merchant;

import java.util.Date;
import java.util.List;

import org.castafiore.catalogue.Product;
import org.castafiore.inventory.customers.Customer;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.user.ShoppingMallUser;

public interface ShoppingMallMerchant extends ShoppingMallUser {
	
	public final static String MY_PRODUCTS_DIR = E_SHOP_DIR +"/Products";
	
	public final static String PRODUCT_DRAFT_DIR = MY_PRODUCTS_DIR + "/draft";
	
	//public final static String PRODUCT_PUBLISHED_DIR = MY_PRODUCTS_DIR + "/published";
	
	//public final static String PRODUCT_DELETED_DIR = MY_PRODUCTS_DIR + "/deleted";
	
	public final static String CUSTOMER_DIR = E_SHOP_DIR + "/Customers";

	/**
	 * return my products
	 * @return
	 */
	public List<Product> getMyProducts(int state);
	
	public List<Product> getMyProducts(int state, int start, int max);
	
	public int countMyProduct(int state);
	public Product createProduct();
	
	
	
	/**
	 * Saves a product to draft
	 * @param product
	 */
	public void saveProduct(Product product);
	
	public Order createOrder(String supplier);
	
	/**
	 * Publish a product
	 * @param product
	 */
	public void publishProduct(Product product);
	
	
	/**
	 * deletes a product
	 * @param product
	 */
	public void deleteProduct(Product product);
	
	
	public void updateProduct(Product product);
	
	
	
	public List<Order> getOrders();
	
	public Customer createCustomer();
	
	public  List<Order> getSalesReport(Date startDate, Date endDate);
	
	public List<Order> getProductReport(Date startDate, Date endDate, String productCode);

	
}
