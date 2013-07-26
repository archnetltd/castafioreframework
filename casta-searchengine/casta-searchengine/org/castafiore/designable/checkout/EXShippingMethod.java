package org.castafiore.designable.checkout;

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.designable.AbstractXHTML;
import org.castafiore.designable.CartItem;
import org.castafiore.designable.CurrencySensitive;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.shoppingmall.checkout.DeliveryOptions;
import org.castafiore.shoppingmall.merchant.DeliveryUtil;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.utils.StringUtil;

public class EXShippingMethod extends AbstractXHTML implements Event, CurrencySensitive{

	public EXShippingMethod(String name) {
		super(name);
		addClass("ui-widget-content");
		addChild(new EXContainer("logo", "img").setStyle("width", "100px").setStyle("margin", "0 20px 20px 0").setAttribute("src", "http://assets.gcstatic.com/u/apps/asset_manager/uploaded/2009/03/ups-logo-200x200-1232453753-lead-image-0.jpg"));
		addChild(new EXContainer("message", "h4").setText("Flat rate"));
		addChild(new EXContainer("price", "label").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),150)));
		
		
		addChild(new EXButton("back", "Back").addEvent(this, CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "left").setStyle("margin-top", "10px"));
		addChild(new EXButton("continue", "Continue").addEvent(this, CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "120px"));
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		if(container.getName().equals("back")){
			Container c =co.getChild("shipping");
			c.getChildByIndex(1).setDisplay(true);
		}else{
			Container c =co.getChild("payment");
			if(c.getChildren().size() ==1)
				c.addChild(new EXPaymentInformation("").setStyle("width","674px"));
			else
				c.getChildByIndex(1).setDisplay(true);
		}
		setDisplay(false);
		return true;
	}
	
	public BigDecimal getPrice(){
		return new BigDecimal(getChild("price").getAttribute("val"));
	}
	
	
	
	
	public void setMerchant(Merchant merchant){
		DeliveryOptions options = merchant.getDeliveryOptions();
		EXMiniCart cart = (EXMiniCart)getRoot().getDescendentById(getAncestorOfType(EXCheckout.class).getAttribute("cartid"));
		BigDecimal total = cart.getTotal();
		String currentCurrency = FinanceUtil.getCurrentCurrency();
		String cc  = getAncestorOfType(EXCheckout.class).getDescendentOfType(EXShippingInformation.class).getDescendentOfType(EXAutoComplete.class).getValue().toString();
		String code = FinanceUtil.getCode(cc);
		String country = cc + "," +code;
		
		getChild("price").setText(StringUtil.toCurrency(currentCurrency,0)).setAttribute("currency", currentCurrency).setAttribute("val", "0");
		if(!country.equals(merchant.getCountry())){
			BigDecimal totalWeight = getTotalWeight();
			BigDecimal price = DeliveryUtil.lookup(totalWeight, code);
			if(price != null){
				getChild("logo").setAttribute("src", "http://assets.gcstatic.com/u/apps/asset_manager/uploaded/2009/03/ups-logo-200x200-1232453753-lead-image-0.jpg").setDisplay(true);
				getChild("message").setText("Delivered by UPS");
				
				try{
					BigDecimal val = FinanceUtil.convert(price, "MUR", currentCurrency);
					getChild("price").setText(StringUtil.toCurrency(currentCurrency,val)).setAttribute("currency", currentCurrency).setAttribute("val", val.toPlainString());
				}catch(Exception e){
					e.printStackTrace();
					getChild("price").setText(StringUtil.toCurrency("MUR",price)).setAttribute("currency", "MUR").setAttribute("val", price.toPlainString());
				}
			}else{
				getChild("message").setDisplay(true).setText("Our default delivery system does not work with your country for the moment.<br>However we will register your order and and contact you about an alternate way and price to deliver your order");
				getChild("logo").setDisplay(false);
				getChild("price").setText(StringUtil.toCurrency("MUR",2000)).setAttribute("currency", "MUR").setAttribute("val", "2000");
			}
			
			return;
		}
		
		
		if(options.getTransportPayer().equals("Shipper")){
			getChild("logo").setAttribute("src", "http://www.memorycross.com/images/FreeShipping2.jpg").setDisplay(true);
			getChild("message").setDisplay(true).setText("Free shipping");
			getChild("price").setDisplay(false).setAttribute("currency", currentCurrency).setAttribute("val", "0");
			
			
			if(options.getFreeDeliveryThreshold().doubleValue() > total.doubleValue()){
				getChild("logo").setDisplay(false);
				getChild("message").setText("We are sorry that shipping is not offerred for orders below " + StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),options.getFreeDeliveryThreshold()));
			}
		}else{
			getChild("message").setDisplay(true);
			getChild("price").setDisplay(true);
			
			
			BigDecimal price  = new BigDecimal(150);
			try{
				BigDecimal val = FinanceUtil.convert(price, "MUR", currentCurrency);
				getChild("price").setText(StringUtil.toCurrency(currentCurrency,val)).setAttribute("currency", currentCurrency).setAttribute("val", val.toPlainString());
			}catch(Exception e){
				e.printStackTrace();
				getChild("price").setText(StringUtil.toCurrency("MUR",price)).setAttribute("currency", "MUR").setAttribute("val", price.toPlainString());
			}
			//getChild("price").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),150)).setAttribute("currency", FinanceUtil.getCurrentCurrency()).setAttribute("val", "150");
			if(options.isUps()){
				getChild("logo").setAttribute("src", "http://assets.gcstatic.com/u/apps/asset_manager/uploaded/2009/03/ups-logo-200x200-1232453753-lead-image-0.jpg").setDisplay(true);
				getChild("message").setText("Delivered by UPS");
				
				for(CartItem item: getRoot().getDescendentOfType(EXMiniCart.class).getItems()){
					if(!canDeliver(item)){
						getChild("message").setText("<b>Delivered by UPS</b><br> <i>(Please note that this is only an approximate value. Exact value will communicated to you as soon as possible)</i>");
						break;
					}
				}
			}else{
				getChild("logo").setDisplay(false);
				getChild("message").setText("Delivered by " + merchant.getTitle());
			}
			
			if(options.getFreeDeliveryThreshold().doubleValue() <= total.doubleValue()){
				//getChild("logo").setDisplay(false);
				getChild("message").setText("Free shipping offerred for orders above " + StringUtil.toCurrency(currentCurrency,options.getFreeDeliveryThreshold()));
				getChild("price").setText(StringUtil.toCurrency(currentCurrency,0)).setAttribute("val", "0").setAttribute("currency", currentCurrency);
			}
		}
	}
	
	
	public BigDecimal getTotalWeight(){
		BigDecimal total = BigDecimal.ZERO;
		EXMiniCart cart = (EXMiniCart)getRoot().getDescendentById(getAncestorOfType(EXCheckout.class).getAttribute("cartid"));
		for(CartItem item: cart.getItems()){
			total .add(item.getWeight().multiply(item.getQty()));
		}
		return total;
	}
	
	private boolean canDeliver(CartItem p){
		
		//Product p = citem.getProduct();
		if(p.getLength() == null || p.getHeight() == null || p.getWidth() == null){
			return false;
		}
		
		BigDecimal volume = p.getLength().multiply(p.getWidth().multiply(p.getHeight()));
		volume = volume.multiply(p.getQty());
		if(volume.doubleValue() > 30000){
			return false;
		}
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeCurrency() throws Exception {
		try{
		String currentC = FinanceUtil.getCurrentCurrency();
		Container uPrice = getChild("price");
		String pCur = uPrice.getAttribute("currency");
		BigDecimal val = new BigDecimal(uPrice.getAttribute("val"));
		BigDecimal nVal = FinanceUtil.convert(val, pCur, currentC);
		uPrice.setText(StringUtil.toCurrency(currentC, nVal)).setAttribute("currency", currentC).setAttribute("val", nVal.toPlainString());
		}catch(Exception e){
			
		}
		
	}

}
