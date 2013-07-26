package org.castafiore.shoppingmall.user.ui.settings;

import java.math.BigDecimal;

import org.castafiore.shoppingmall.checkout.DeliveryOptions;
import org.castafiore.shoppingmall.merchant.FinanceUtil;
import org.castafiore.shoppingmall.merchant.Merchant;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;

public class EXDeliveryOptions extends EXXHTMLFragment {

	public EXDeliveryOptions(String name) {
		super(name, "templates/EXDeliveryOptions.xhtml");
		addChild(new EXSelect("type", new DefaultDataModel<Object>().addItem("Own Delivery", "UPS Delivery")));
		addChild(new EXSelect("payer", new DefaultDataModel<Object>().addItem("Shipper", "Receiver")));
		addChild(new EXInput("threshold", "0"));
	}
	
	public void fill(DeliveryOptions options){
		if(options.isUps()){
			setValue("type", "UPS Delivery");
			
		}else{
			setValue("type", "Own Delivery");
		}
		setValue("payer",options.getTransportPayer());
		setValue("threshold", StringUtil.toCurrency(FinanceUtil.getCurrentCurrency(),options.getFreeDeliveryThreshold()));
	}
	
	public void createOrUpdate(Merchant merchant){
		DeliveryOptions options =	merchant.getDeliveryOptions();
		
		String type = getValue("type");
		if(type.equals("Own Delivery")){
			options.setUps(false);
		}else{
			options.setUps(true);
		}
		
		options.setTransportPayer(getValue("payer"));
		
		try{
			options.setFreeDeliveryThreshold(new BigDecimal(Double.parseDouble(getValue("threshold"))));
		}catch(Exception e){
			options.setFreeDeliveryThreshold(BigDecimal.ZERO);
		}
	}
	
	public void setValue(String fieldName, String value){
		 ((StatefullComponent)getChild(fieldName)).setValue(value);
	}
	
	public String getValue(String fieldName){
		return ((StatefullComponent)getChild(fieldName)).getValue().toString();
	}

}
