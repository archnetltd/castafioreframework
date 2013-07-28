package org.castafiore.designable.checkout;

import java.util.Map;

import org.castafiore.designable.AbstractXHTML;
import org.castafiore.designable.EXMiniCart;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;

public class EXPaymentInformation extends AbstractXHTML implements Event{

	public EXPaymentInformation(String name) {
		super(name);
		addClass("ui-widget-content");
		
		addChild(new EXSelect("paymentMethod", new DefaultDataModel<Object>().addItem("Cash", "Cheque", "Standing Order","Bank Transfer")));
		
		addChild(new EXInput("chequeNo"));
		
		addChild(new EXButton("continue", "Continue").setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px"));
		
		addChild(new EXButton("back", "Back").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "left").setStyle("margin-top", "10px"));
		addChild(new EXButton("continue", "Continue").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "120px"));
	}
	
	@Override
	public void ClientAction(ClientProxy container) {
		container.mask().makeServerRequest(this);
	}
	
	public boolean validate(){
		String pt = getDescendentOfType(EXSelect.class).getValue().toString();
		if(pt.equals("Cheque")){
			String cn = getDescendentOfType(EXInput.class).getValue().toString();
			if(!StringUtil.isNotEmpty(cn)){
				getDescendentOfType(EXInput.class).addClass("ui-state-error");
				return false;
			}
		}return true;
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		if(container.getName().equals("back")){
			Container c =co.getChild("shippingMethod");
			c.getChildByIndex(1).setDisplay(true);
			setDisplay(false);
		}else{
			if(validate()){
				Container c =co.getChild("review");
				EXMiniCart cart = (EXMiniCart)getRoot().getDescendentById(getAncestorOfType(EXCheckout.class).getAttribute("cartid"));
				if(c.getChildren().size() ==1){
					double delivery = container.getAncestorOfType(EXCheckout.class).getDescendentOfType(EXShippingMethod.class).getPrice().doubleValue();
					c.addChild(new EXOrderReview("", cart,delivery).setStyle("width", "674px"));
				}
				else
					c.getChildByIndex(1).setDisplay(true);
				setDisplay(false);
			}
		}
		
		return true;
	}
	
	
	public void fillOrder(Order order){
		String pt = getDescendentOfType(EXSelect.class).getValue().toString();
		order.setPaymentMethod(pt);
		
		if(pt.equals("Cheque")){
			String cn =  getDescendentOfType(EXInput.class).getValue().toString();
			order.setChequeNo(cn);
		}else{
			order.setChequeNo("");
		}
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
