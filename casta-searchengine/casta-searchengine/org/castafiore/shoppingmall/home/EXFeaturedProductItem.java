package org.castafiore.shoppingmall.home;

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXEcommerce;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.designable.product.EXProduct;
import org.castafiore.designable.product.EXProductGrid;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXFeaturedProductItem extends EXXHTMLFragment implements Event, CurrencySensitive{

	public EXFeaturedProductItem(String name) {
		super(name, "templates/EXHomeFeatured.xhtml");
		addChild(new EXContainer("productImageLink", "a").addClass("product_image").setAttribute("href", "#pi").addEvent(this, Event.CLICK).addChild(new EXContainer("img", "img")));
		addChild(new EXContainer("productName", "a").setAttribute("href", "#pn").addEvent(this, Event.CLICK));
		addChild(new EXContainer("price", "span").addClass("price"));
		addChild(new EXContainer("addToCart","a").addClass("exclusive").addEvent(this, Event.CLICK));
	}
	
	
	
	public void setProduct(Product p){
		setAttribute("path", p.getAbsolutePath());
		getChild("productName").setText(p.getTitle());
		
		Merchant m = MallUtil.getMerchant(MallUtil.getEcommerceMerchant());
		String currency = FinanceUtil.getCurrentCurrency();
		if(!currency.equals(m.getCurrency())){
				try{
					BigDecimal curr = FinanceUtil.convert(p.getTotalPrice(), m.getCurrency(), currency);
					getChild("price").setText(StringUtil.toCurrency(currency,curr));
				}catch(Exception e){
					BigDecimal curr = p.getTotalPrice();
					getChild("price").setText(StringUtil.toCurrency(m.getCurrency(),curr));
				}
		}else{
			BigDecimal curr = p.getTotalPrice();
			getChild("price").setText(StringUtil.toCurrency(currency,curr));
		}
		
		getChild("price").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),p.getTotalPrice()));
		getChild("productImageLink").setAttribute("title", p.getTitle()).getChildByIndex(0).setAttribute("src", p.getImageUrl("")).setAttribute("alt", p.getTitle());
		
		if(p.getCurrentQty()==null || p.getCurrentQty().doubleValue() <=0){
			getChild("addToCart").setText("Sold out !!").setStyle("font-weight", "bold").setStyle("font-size", "15px").setStyle("height", "24px").addClass("ui-state-error").removeClass("ui-state-default").getEvents().clear();
			
		}else{
			getChild("addToCart").getEvents().clear();
			getChild("addToCart").setText("Add to cart").setStyle("font-weight", "normal").setStyle("font-size", "12px").addClass("ui-state-default").removeClass("ui-state-error").addEvent(this, Event.CLICK);
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXProductGrid.class)).makeServerRequest(this);
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String path = getAttribute("path");
		Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		EXEcommerce ecom = container.getAncestorOfType(EXEcommerce.class);
		
		if(container.getName().equals("productName") || container.getName().equals("productImageLink") ){
			EXProduct uip = (EXProduct)ecom.getPageOfType(EXProduct.class);
			if(uip == null){
				uip = new EXProduct("productCard");
				ecom.getBody().addChild(uip);
			}
			uip.setProduct(p);
			ecom.showPage(uip.getClass());
		}else{
			ecom.getDescendentOfType(EXMiniCart.class).addToCart(p,1,container);	
		}
		return true;
		
		
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void changeCurrency()throws Exception {
		if(isVisible()){
			String path = getAttribute("path");
			Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
			Merchant m = MallUtil.getMerchant(MallUtil.getEcommerceMerchant());
			String currency = FinanceUtil.getCurrentCurrency();
			if(!currency.equals(m.getCurrency())){
				BigDecimal curr = FinanceUtil.convert(p.getTotalPrice(), m.getCurrency(), currency);
				getChild("price").setText(StringUtil.toCurrency(currency,curr));
			}else{
				BigDecimal curr = p.getTotalPrice();
				getChild("price").setText(StringUtil.toCurrency(currency,curr));
			}
		}
		
	}

}
