package org.castafiore.shoppingmall.product.ui;

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXEcommerce;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.utils.StringUtil;

public class EXMiniCarts extends EXContainer implements Event, CurrencySensitive{

	public EXMiniCarts(String name) {
		super(name, "div");
	}
	
	
	public EXMiniCart getMiniCart(String merchant){
		for(Container c : getChildren()){
			if(c.getName().equals(merchant)){
				return (EXMiniCart)c;
			}
		}
		EXMiniCart cart = new EXMiniCart(merchant);
		cart.addClass("span-6").setStyle("margin", "0");
		cart.addChild(new EXContainer("title", "span").setText("Cart : " + MallUtil.getMerchant(merchant).getTitle()));
		addChild(cart);
		cart.getChild("checkout").remove();
		cart.addChild(new EXContainer("checkout", "a").setStyle("float", "none").setStyle("font-weight", "bold").setStyle("text-align", "center").setAttribute("href", "ecommerce.jsp?m=" + merchant).setAttribute("target", "_blank").setAttribute("class", "button ui-state-default ui-corner-all fg-button-small").setText("Proceed in shop"));
		return cart;
	}
	
	
	public void redirect(Container source){
		EXMiniCart cart = source.getAncestorOfType(EXMiniCart.class);
		String merchant = cart.getName();
		EXEcommerce ecommerce = new EXEcommerce("");
		//ecommerce.
	}


	@Override
	public void ClientAction(ClientProxy container) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void changeCurrency() throws Exception {
		BigDecimal total = new BigDecimal(0);
		for(Container c : getChildren()){
			if(c instanceof EXMiniCart){
				((EXMiniCart)c).changeCurrency();
				total = ((EXMiniCart)c).getTotal().add(total);
			}	
		}
		getDescendentByName("totalll").setText("Cart total :" + StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(), total));
		
	}

}
