/**
 * 
 */
package org.castafiore.designable.checkout;

import java.util.HashMap;
import java.util.Map;

import org.castafiore.designable.AbstractXHTML;
import org.castafiore.designable.EXCartDetail;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.EXProductRotator;
import org.castafiore.designable.EXRecentProducts;
import org.castafiore.designable.EXSearchProductBar;
import org.castafiore.designable.EXShopDepartments;
import org.castafiore.designable.product.EXProduct;
import org.castafiore.designable.product.EXProductItem;
import org.castafiore.designable.product.EXProductResultBar;
import org.castafiore.designer.designable.EXTemplateDesignableFactory;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.scripting.EXTemplateComponent;

/**
 * @author acer
 *
 */
public class EXCatalogueComponentsDesignableFactory extends EXTemplateDesignableFactory{
	
	public static Map<String, Class<? extends AbstractXHTML>> map = new HashMap<String, Class<? extends AbstractXHTML>>();
	
	static{
		
	
	
	map.put("EXCartDetail", EXCartDetail.class);
	map.put("EXMiniCart", EXMiniCart.class);
	map.put("EXProductRotator", EXProductRotator.class);
	map.put("EXRecentProducts", EXRecentProducts.class);
	map.put("EXSearchProductBar", EXSearchProductBar.class);
	map.put("EXShopDepartments", EXShopDepartments.class);
	
	map.put("EXBillingInformation", EXBillingInformation.class);
	map.put("EXPaymentInformation", EXPaymentInformation.class);
	map.put("EXShippingInformation", EXShippingInformation.class);
	map.put("EXShippingMethod", EXShippingMethod.class);
	
	map.put("EXProduct", EXProduct.class);
	map.put("EXProductItem", EXProductItem.class);
	map.put("EXProductResultBar", EXProductResultBar.class);
	
	}
	
	@Override
	public Container getInstance() {
		
		try{
		EXTemplateComponent tc = map.get(getType()).getConstructor(String.class).newInstance(getType());

		return tc;
		}catch(Exception e ){
			throw new UIException(e);
		}
	}
	
	@Override
	public String getCategory() {
		return "ECommerce";
	}
	
	public String getUniqueId() {
		return "ecommerce:"+getType();
	}

}
