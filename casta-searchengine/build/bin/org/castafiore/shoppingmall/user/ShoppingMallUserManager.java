package org.castafiore.shoppingmall.user;

import java.util.Map;

import org.castafiore.shoppingmall.merchant.Merchant;

public interface ShoppingMallUserManager {
	
	public ShoppingMallUser registerUser(Map<String, String> info);
	
	
	public ShoppingMallUser login(String username, String password)throws Exception;
	
	public ShoppingMallUser getCurrentUser();
	
	public ShoppingMallUser getUser(String username);


}
