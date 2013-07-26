package org.castafiore.shoppingmall.product.ui.tab;

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.searchengine.MallUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.utils.StringUtil;

public class EXPrice extends EXAbstractProductTabContent  implements Event{

	public EXPrice() {
		addChild(new EXInput("totalPrice").addEvent(this, BLUR));
		addChild(new EXInput("taxRate"));
		addChild(new EXInput("price").setAttribute("readonly", "true"));
		addChild(new EXTextArea("script"));
		
		if(MallUtil.getCurrentMerchant().getPlan().equalsIgnoreCase("free")){
			setTemplateLocation("templates/product/PriceFree.xhtml");
		}
	}
	
	@Override
	public Container setProduct(Product product){

			if(product != null){
				((StatefullComponent)getChild("totalPrice")).setValue(product.getTotalPrice()==null?"0":product.getTotalPrice());
				((StatefullComponent)getChild("taxRate")).setValue(product.getTaxRate()==null?"15":product.getTaxRate());
				((StatefullComponent)getChild("price")).setValue(product.getPriceExcludingTax());
				((StatefullComponent)getChild("script")).setValue(product.getPriceScript());
			}else{
				((StatefullComponent)getChild("totalPrice")).setValue("0");
				((StatefullComponent)getChild("taxRate")).setValue("15");
				((StatefullComponent)getChild("price")).setValue("0");
				((StatefullComponent)getChild("script")).setValue("");
			}
			return this;
	}

	@Override
	public void fillProduct(Product product) {

		BigDecimal totalPrice = new BigDecimal(((StatefullComponent)getChild("totalPrice")).getValue().toString());
		product.setTotalPrice(totalPrice);
		
		BigDecimal taxRate = new BigDecimal(((StatefullComponent)getChild("taxRate")).getValue().toString());
		product.setTaxRate(taxRate);
		
		String script = ((StatefullComponent)getChild("script")).getValue().toString();
		product.setPriceScript(script);
		


		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		String totalPrice = ((StatefullComponent)getChild("totalPrice")).getValue().toString();
		String vat = ((StatefullComponent)getChild("taxRate")).getValue().toString();
		
		if(StringUtil.isNotEmpty(totalPrice) && StringUtil.isNotEmpty(vat)){
			BigDecimal exl = new BigDecimal(100).subtract(new BigDecimal(vat));
			exl = exl.divide(new BigDecimal(100));
		
			String value = new BigDecimal(totalPrice).multiply(exl).toPlainString();
			((StatefullComponent)getChild("price")).setValue(value);
			
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	

}
