package org.castafiore.designable;

import java.util.ArrayList;
import java.util.List;

import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.RefreshSentive;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.panel.EXOverlayPopupPlaceHolder;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXEcommerce extends EXContainer implements RefreshSentive, PopupContainer{

	public EXEcommerce(String name) {
		super(name, "div");
		setStyle("width", "970px").setStyle("margin", "30px auto");
		setStyle("float", "left");
		addChild(new EXOverlayPopupPlaceHolder("overlay"));
		addChild(new EXSearchProductBar("searchBar"));
		addChild(new EXContainer("leftcommerce", "div").setAttribute("style", "width: 200px;min-height: 500px;float: left;margin-left: 20px;margin-right: 20px"));
		
		String username= MallUtil.getEcommerceMerchant();
		
		
		getChild("leftcommerce").addChild(new EXShopDepartments("departments"));
			
		getChild("leftcommerce").addChild( new EXMiniCart(username));
		
		getChild("leftcommerce").addChild(new EXRecentProducts("recentProducts"));
		
		getChild("leftcommerce").addChild(new EXProductRotator("productRotator"));
		
		EXCatalogue cat = new EXCatalogue("");
		List l = cat.search("recent", "Demo", username);
		addChild(new EXContainer("bodybody", "div").setAttribute("style", "00px;float: left;min-height: 500px;width:710px;margin-right: 10px").addChild(cat));
		
		//QueryParameters params = new QueryParameters().setEntity(Product.class).addRestriction(Restrictions.eq("providedBy", username)).addOrder(Order.asc("dateCreated")).setFirstResult(0).setMaxResults(5);
		
		 
		
		((EXProductRotator)getChild("leftcommerce").getChild("productRotator")).setProducts(l);
		
		((EXRecentProducts)getChild("leftcommerce").getChild("recentProducts")).setProducts(l);
		
		setDepartment(username);
		
		
		
	}
	
	
	public String getCurrency(){
		return getDescendentOfType(EXSearchProductBar.class).getCurrency();
	}
	
	
	public void setDepartment(String username){
		Merchant m = MallUtil.getMerchant(username);
		List<String> ss = new ArrayList<String>();
		if(StringUtil.isNotEmpty(m.getCategory()))
			ss.add(m.getCategory());
		if(StringUtil.isNotEmpty(m.getCategory_1()))
			ss.add(m.getCategory_1());
		if(StringUtil.isNotEmpty(m.getCategory_2()))
			ss.add(m.getCategory_2());
		if(StringUtil.isNotEmpty(m.getCategory_3()))
			ss.add(m.getCategory_3());
		if(StringUtil.isNotEmpty(m.getCategory_4()))
			ss.add(m.getCategory_4());
		
		((EXShopDepartments)getChild("leftcommerce").getChild("departments")).setCategories(ss);
		
	}
	
	public Container getBody(){
		return getChild("bodybody");
	}
	
	
	public Container getPageOfType(Class<?> page){
		Container b = getBody();
		for(Container c : b.getChildren()){
			if(page.isAssignableFrom(c.getClass())){
				return c;
			}
		}
		return null;
	}
	
	public void showPage(Class<?> page){
		Container b = getBody();
		for(Container c : b.getChildren()){
			if(page.isAssignableFrom(c.getClass())){
				if(!c.isVisible()){
					c.setDisplay(true);
				}
				 
			}else{
				if(c.isVisible()){
					c.setDisplay(false);
				}
			}
		}
	}
	public boolean hasPage(Class<?> page){
		Container b = getBody();
		for(Container c : b.getChildren()){
			if(page.isAssignableFrom(c.getClass())){
				return true;
			}
		}
		return false;
	}


	@Override
	public void onRefresh() {
//		HttpSession session = (HttpSession)getRoot().getConfigContext().get("session");
//		EXSearchEngineApplication app = (EXSearchEngineApplication)session.getAttribute("searchengine");
//		if(app != null){
//		 	EXMiniCarts carts = app.getDescendentOfType(EXMiniCarts.class);
//		 	String path = (String)getRoot().getConfigContext().get("portalPath");
//			String username = null;
//		 	if(carts != null){
//		 		if(path != null){
//					username = path.replace("/root/users/", "").replace("/ecommerce.ptl", "");
//				}
//				else
//					username = Util.getRemoteUser();
//		 		
//		 		
//		 		EXMiniCart cart = carts.getMiniCart(username);
//		 		 List<CartItem> items = cart.getItems();
//		 		 if(items.size() > 0){
//		 			 getDescendentOfType(EXMiniCart.class).setItems(items);
//		 		 }
//		 		
//		 	}
//		}
		
	}


	@Override
	public void addPopup(Container popup) {
		getDescendentOfType(EXOverlayPopupPlaceHolder.class).addChild(popup);
		
	}

}
