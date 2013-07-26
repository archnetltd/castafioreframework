package org.castafiore.designable.product;	

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.designable.AbstractXHTML;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXEcommerce;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.searchengine.back.EXWindow;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.shoppingmall.ng.EXProductDetailNG;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.Dimension;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXProductItem extends AbstractXHTML implements Event, CurrencySensitive{

	public EXProductItem(String name) {
		super(name);
		addClass("itema");
		addClass("EXProductItem");
		addChild(new EXContainer("productImageLink", "a").addClass("product-image").setAttribute("href", "#pi").addEvent(this, Event.CLICK).addChild(new EXContainer("img", "img").setAttribute("width", "124").setAttribute("height", "124")));
		addChild(new EXContainer("productName", "a").setAttribute("href", "#pn").addEvent(this, Event.CLICK));
		addChild(new EXContainer("price", "span").addClass("price"));
		addChild(new EXButton("addToCart","Add to cart").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all").setStyle("float", "none").setStyle("margin", "10px auto").setStyle("width", "110px"));
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
			getChild("addToCart").setText("Sold out !!").setStyle("font-weight", "bold").setStyle("font-size", "15px").addClass("ui-state-error").removeClass("ui-state-default").getEvents().clear();
			
		}else{
			getChild("addToCart").getEvents().clear();
			getChild("addToCart").setText("Add to cart").setStyle("font-weight", "normal").setStyle("font-size", "12px").addClass("ui-state-default").removeClass("ui-state-error").addEvent(this, Event.CLICK);
		}
	}


	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXProductGrid.class)).makeServerRequest(this);
		
	}


public void showDetail(Container source){
		
		Product p =  null;
		
		p= (Product)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		
		EXPanel panel = new EXPanel("pd", "Product detail");
		panel.setShowFooter(true);
		EXProductDetailNG pd = new EXProductDetailNG("detail");
		//pd.setStyle("width", "586px");
		//pd.setStyle("height", "420px");
		pd.setStyle("overflow-x", "hidden");
		pd.setProduct(p);
		panel.setBody(pd);
		panel.setTitle(p.getTitle());
		//pd.getParent().setStyle("margin", "0");
		PopupContainer pc =getAncestorOfType(PopupContainer.class);
		if(pc == null){
			pc = getRoot().getDescendentOfType(PopupContainer.class);
		}
		panel.setWidth(Dimension.parse("652px"));
		pc.addPopup(panel.setStyle("z-index", "3000"));
		panel.getDescendentByName("content").setAttribute("style", "height: auto; overflow: auto; width: auto; margin: 0px !important; font-weight: normal; padding: 0pt; min-height: 61px;");
		panel.setStyle("border", "none");
		//panel.setHeight(Dimension.parse("470px"));
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String path = getAttribute("path");
		Product p = (Product)SpringUtil.getRepositoryService().getFile(path, Util.getRemoteUser());
		EXEcommerce ecom = container.getAncestorOfType(EXEcommerce.class);
		if(ecom != null){
			if(container.getName().equals("productName") || container.getName().equals("productImageLink") ){
//				EXProduct uip = (EXProduct)ecom.getPageOfType(EXProduct.class);
//				if(uip == null){
//					uip = new EXProduct("productCard");
//					ecom.getBody().addChild(uip);
//				}
//				uip.setProduct(p);
//				ecom.showPage(uip.getClass());
				showDetail(container);
			}else{
				CartItem item = ecom.getDescendentOfType(EXMiniCart.class).addToCart(p, 1,container);
				if(item != null){
					request.putAll(item.request);
				}
			}
		}else{
			try{
			CartItem item = container.getAncestorOfType(EXWindow.class).getDescendentOfType(EXMiniCart.class).addToCart(p, 1,container);
			if(item != null){
				request.putAll(item.request);
			}
			}catch(Exception e){
				CartItem item = container.getRoot().getDescendentOfType(EXMiniCart.class).addToCart(p, 1, container);
				if(item != null){
					request.putAll(item.request);
				}
			}
		}
		return true;
		
		
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		if(request.containsKey("container") && request.containsKey("method") && request.containsKey("param")){
			ClientProxy proxy = new ClientProxy("#" + request.get("container"));
			ClientProxy p = proxy.appendTo(new ClientProxy(container.getRoot().getIdRef())).fadeIn(100).setStyle("top", "10%").setStyle("left", "200px").setStyle("position", "absolute");
			container.mergeCommand(p);
		}
		
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
