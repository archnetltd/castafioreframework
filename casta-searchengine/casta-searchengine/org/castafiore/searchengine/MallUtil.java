package org.castafiore.searchengine;

import java.util.List;

import org.castafiore.designer.Studio;
import org.castafiore.security.Address;
import org.castafiore.security.User;
import org.castafiore.shoppingmall.ShoppingMall;
import org.castafiore.shoppingmall.ShoppingMallManager;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.user.ShoppingMallUser;
import org.castafiore.shoppingmall.user.ShoppingMallUserManager;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.engine.context.CastafioreApplicationContextHolder;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.service.QueryParameters;
import org.hibernate.criterion.Restrictions;

public  class MallUtil extends Studio{
	
	public static ShoppingMallUser getCurrentUser(){
		return SpringUtil.getBeanOfType(ShoppingMallUserManager.class).getCurrentUser();
	}
	
	
	public static ShoppingMall getCurrentMall(){
		return SpringUtil.getBeanOfType(ShoppingMallManager.class).getDefaultMall();
	}
	
	
	public static Merchant getCurrentMerchant(){
		return getCurrentMall().getMerchant(Util.getLoggedOrganization());
	}
	
	public static Merchant getMerchant(String username){
		return getCurrentMall().getMerchant(username);
	}
	
	public static String getEcommerceMerchant(){
		String path = (String)CastafioreApplicationContextHolder.getCurrentApplication().getConfigContext().get("portalPath");
		String username = null;
		if(path != null){
			username = path.replace("/root/users/", "").replace("/site.ptl", "");
		}
		else
			username = Util.getLoggedOrganization();
		
		return username;
	}
	

	public static  org.castafiore.shoppingmall.checkout.Order createOrder(String invoiceCode,User customer, String supplier){
		org.castafiore.shoppingmall.checkout.Order o =getMerchant(supplier).getManager().createOrder(customer.getUsername());
		o.setCode(invoiceCode);
		
		BillingInformation si = o.createFile("billing", BillingInformation.class);
		
		
		si.setEmail(customer.getEmail());
		si.setFirstName(customer.getFirstName());
		si.setLastName(customer.getLastName());
		
		si.setPhone(customer.getPhone());
		si.setMobile(customer.getMobile());
		si.setUsername(customer.getUsername());
		si.setFax(customer.getFax());
		if(customer.getAddress().size() > 0){
			Address add = customer.getDefaultAddress();
			si.setAddressLine1(add.getLine1());
			si.setAddressLine2(add.getLine2());
			si.setCity(add.getCity());
			si.setZipPostalCode(add.getPostalCode());
		}
		
		si.save();
		return o;
	}
	
	public static Order getOrderByCode(String code){
		QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("code", code));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(result.size() > 0){
			return (Order)result.get(0);
		}else{
			return null;
		}
	}
	
	
	public static Order getOrderByCheque(String cheque){
		QueryParameters params = new QueryParameters().setEntity(Order.class).addRestriction(Restrictions.eq("chequeNo", cheque));
		
		List result = SpringUtil.getRepositoryService().executeQuery(params, Util.getRemoteUser());
		if(result.size() > 0){
			return (Order)result.get(0);
		}else{
			return null;
		}
	}
	
	
//	public static void doImport(Properties properties, String merchant, String category, String subcategory){
//		Iterator<Object> keys = properties.keySet().iterator();
//		//String merchant = properties.getProperty("merchant");
//		while(keys.hasNext()){
//			
//			String key = keys.next().toString();
//			
//				
//			String values = properties.getProperty(key);
//			
//			String[] parts = StringUtils.split(values, "||");
//			
//			
//			String code = key;
//			
//			String title = parts[0];
//			String summary = "Ants are social insects of the family Formicidae  " +
//					"and, along with the related wasps and bees, belong to the order Hymenoptera. " +
//					"Ants evolved from wasp-like ancestors in the mid-Cretaceous period between 110 and 130 million " +
//					"years ago and diversified after the rise of flowering plants. More than 12,500 " +
//					"out of an estimated total of 22,000 species have been classified.[3][4] " +
//					"They are easily identified by their elbowed antennae and a distinctive node-like structure that forms a slender waist.";
//			String price = parts[2];
//			String image = parts[1];
//			//String category = parts[3];
//			//String subCategory = parts[4];
//			
//			ShoppingMallUserManager manager = SpringUtil.getBeanOfType(ShoppingMallUserManager.class);
//			ShoppingMallMerchant omerchant = manager.getUser(merchant).getMerchant();
//			Product p = omerchant.createProduct();
//			p.setCode(code);
//			p.setCategory(category);
//			p.setSubCategory(subcategory);
//			p.setTitle(title);
//			p.setSummary(summary);
//			p.setTotalPrice(new BigDecimal(price));
//			p.createImage("Image 1" , image);
//			p.setStatus(Product.STATE_PUBLISHED);
//			p.save();
//			
//			 
//		}
//	}

}
