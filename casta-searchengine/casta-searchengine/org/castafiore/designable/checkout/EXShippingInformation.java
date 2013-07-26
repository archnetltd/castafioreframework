package org.castafiore.designable.checkout;

import java.util.List;
import java.util.Map;

import org.castafiore.SimpleKeyValuePair;
import org.castafiore.designable.AbstractXHTML;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.shoppingmall.checkout.BillingInformation;
import org.castafiore.shoppingmall.checkout.Order;
import org.castafiore.shoppingmall.checkout.ShippingInformation;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXAutoComplete;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.button.EXButton;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.utils.StringUtil;
import org.castafiore.wfs.Util;

public class EXShippingInformation extends AbstractXHTML implements Event{

	
	
	public EXShippingInformation(String name) {
		super(name);
		setStyle("clear", "both");
		addChild(new EXButton("sameAsBillingInfo", "Same as billing info").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "left").setStyle("margin-top", "10px").setStyle("width", "120px"));
		addClass("ui-widget-content");
		String[] fields = new String[]{"firstname", "lastname", "company", "email", "addressline1", "addressline2", "city", "postcode", "telephone", "fax", "mobile", "country"};
		
		for(String s : fields){
			
			if(s.equals("country")){
				try{
					//addChild(new  EXSelect("shipping." +s, new DefaultDataModel<Object>((List)FinanceUtil.getCountries())));
					//getDescendentOfType(EXSelect.class).setValue(FinanceUtil.getClientCountry());
					addChild(new EXAutoComplete("shipping." +s, "Mauritius", FinanceUtil.getCountryList()));
				}catch(Exception e){
					e.printStackTrace();
				}
			}else{
				EXInput input = new EXInput("shipping." + s);
				addChild(input.addClass("input-text"));
			}
			
		}
		
		addChild(new EXButton("continue", "Continue").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "right").setStyle("margin-top", "10px").setStyle("width", "120px"));
		addChild(new EXButton("back", "Back").addEvent(this, Event.CLICK).setStyleClass("ui-state-default ui-corner-all fg-button-small").setStyle("float", "left").setStyle("margin-top", "10px"));
		setStyle("width", "674px");
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mask(container.getAncestorOfType(EXShippingInformation.class)).makeServerRequest(this);
	}
	
	public void fill(EXBillingInformation bi){
		String[] fields = new String[]{"firstname", "lastname", "company", "email", "addressline1", "addressline2", "city", "postcode", "telephone", "fax", "country", "mobile"};
		for(String s : fields){
			Object val = ((StatefullComponent) bi.getChild("billing." +s)).getValue();
			((StatefullComponent)getChild("shipping." +s)).setValue(val);
		}
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		EXCheckout co = getAncestorOfType(EXCheckout.class);
		if(container.getName().equals("back")){
			Container c =co.getChild("billing");
			c.getChildByIndex(1).setDisplay(true);
			setDisplay(false);
		}else if(container.getName().equals("sameAsBillingInfo")){
		
			EXBillingInformation bi = co.getDescendentOfType(EXBillingInformation.class);
			fill(bi);
			
		}else{
			
			if(validate()){
				Container c =co.getChild("shippingMethod");
				if(c.getChildren().size() ==1){
					String path = (String)getRoot().getConfigContext().get("portalPath");
					String username = null;
					if(path != null){
						username = MallUtil.getEcommerceMerchant();
					}
					else
						username = getAncestorOfType(EXCheckout.class).getAttribute("merchant");
						//username = Util.getRemoteUser();
					EXShippingMethod method = new EXShippingMethod("");
					
					
					c.addChild(method.setStyle("width", "674px"));
					method.setMerchant(MallUtil.getMerchant(username));
				}
				else
					c.getChildByIndex(1).setDisplay(true);
				setDisplay(false);
			}
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
	public void createInfo(Order order){
		ShippingInformation si = order.createFile("shipping", ShippingInformation.class);
		
		si.setAddressLine1(getValue("addressline1"));
		si.setAddressLine2(getValue("addressline2"));
		si.setCity(getValue("city"));
		si.setCompany(getValue("company"));
		si.setEmail(getValue("email"));
		si.setFirstName(getValue("firstname"));
		si.setLastName(getValue("lastname"));
		si.setZipPostalCode(getValue("postcode"));
		si.setPhone(getValue("telephone"));
		si.setFax(getValue("fax"));
		
		//SimpleKeyValuePair kb =  (SimpleKeyValuePair)getDescendentOfType(EXSelect.class).getValue();
		//si.setCountry(kb.getValue() + "," + kb.getKey());
		
		String country = getDescendentOfType(EXAutoComplete.class).getValue().toString();
		String code = FinanceUtil.getCode(country);
		si.setCountry(country + "," + code);
		si.setMobile(getValue("mobile"));
	}
	
	public String getValue(String field){
		
		return ((StatefullComponent)getChild("shipping." + field)).getValue().toString();
	}
	
	private boolean valField(String field){
		StatefullComponent stf = ((StatefullComponent)getChild("shipping." +field));
		if(!StringUtil.isNotEmpty(stf.getValue().toString())){
			stf.addClass("ui-state-error");
			return false;
		}else{
			return true;
		}
	}
	
	public boolean validate(){
		String[] fields = new String[]{"firstname", "lastname", "email", "addressline1", "addressline2", "city","telephone"};
		boolean result = true;
		for(String s : fields){
			if(valField(s)){
				
			}else{
				if(result){
					result = false;
				}
			}
		}
		
		return result;
	}

}
