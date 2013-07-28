package com.eliensons.ui.sales.fontend;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import org.castafiore.designable.CartItem;
import org.castafiore.designable.checkout.EXCheckout;
import org.castafiore.designable.portal.PortalContainer;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.PopupContainer;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.ex.panel.EXPanel;
import org.castafiore.utils.StringUtil;

import com.eliensons.ui.sales.EXElieNSonsMiniCart;

public class EXFrontEndMiniCart extends EXElieNSonsMiniCart{

	public EXFrontEndMiniCart(String name) {
		super(name);
	}
	
	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("clear")){
			setItems(new ArrayList<CartItem>());
			return true;
		}
		
		if(container.getName().equals("saveoptions")){
			request.put("torem", container.getAncestorOfType(EXPanel.class).getId());
			saveOptions(container);
			return true;
		}else if(container.getName().equals("canceloptions")){
			request.put("torem", container.getAncestorOfType(EXPanel.class).getId());
			Container root = container.getAncestorOfType(PortalContainer.class);
			Container parent = root.getParent();
			root.remove();
			parent.setRendered(false);
			return true;
		}
		if(getItems().size() == 0){
			request.put("noitem", "true");
			return true;
		}
			
		EXCheckout cat = new EXFrontEndCheckOut("", this);
		cat.setStyle("z-index", "4000");
		cat.setAttribute("merchant", getAttribute("merchant"));
		cat.setAttribute("cartid", getAttribute("cartid"));
		container.getAncestorOfType(PopupContainer.class).addPopup(cat);
		return true;
			
		
			
	}
	
	public void update(){
		super.update();
		
		if(getItems().size() > 0){
			getChild("numItem").setText(getItems().size() + " item(s)");
			getChild("msubTotal").setText(getItems().get(0).getTitle());
			getChild("mtotal").setText(StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),getTotal()));
		}else{
			getChild("numItem").setText("Your cart is empty");
			getChild("msubTotal").setText("");
			getChild("mtotal").setText("");
		}
	}
	
	public Date getInvoiceDate(){
		return new Date();
	}
	
	
	public String getPos(){
		return "Online";
	}
	
	
	public String getAgent(){
		return "Online";
	}
	
	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		super.Success(container, request);
		if(request.containsKey("noinv")){
			container.alert("Please enter an invoice number");
		}else if(request.containsKey("noitem")){
			container.alert("Please select a plan");
		}else if(request.containsKey("noinvdate")){
			container.alert("Please select an invoice date");
		}
		
	}

}
