package org.castafiore.shoppingmall.product.ui.simple;

import java.math.BigDecimal;
import java.util.Map;

import org.castafiore.catalogue.Product;
import org.castafiore.shoppingmall.product.ui.tab.EXAbstractProductTabContent;
import org.castafiore.ui.Container;
import org.castafiore.ui.StatefullComponent;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.dynaform.EXFieldSet;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXRichTextArea;
import org.castafiore.utils.StringUtil;

public class EXSimpleMain extends EXAbstractProductTabContent implements Event{

	public EXSimpleMain() {
		super();
		EXFieldSet fs = new EXFieldSet("", "Main product Info", true);
		fs.addField("Unique Code :", new EXInput("code"));
		fs.addField("Title :", new EXInput("title"));
		fs.addField("Basic Price :", new EXInput("totalPrice"));
		fs.addField("Tax Rate :", new EXInput("taxRate"));
		fs.addField("Total Price :", new EXInput("price"));
		
		Container c = new EXContainer("cc", "div");
		c.addChild(fs);
		
		
		//addChild(new EXInput("title").addClass("span-11"));
		//addChild(new EXInput("code").addClass("span-11"));

		c.addChild(new EXRichTextArea("description").setStyle("width", "490px").setStyle("height", "200px"));
		c.getDescendentByName("totalPrice").addEvent(this, BLUR);
		c.getDescendentByName("taxRate").addEvent(this, BLUR);
		//addChild(new EXInput("totalPrice").addEvent(this, BLUR));
		//addChild(new EXInput("taxRate"));
		//addChild(new EXInput("price").setAttribute("readonly", "true"));
		addChild(c);
	}

	@Override
	public Container setProduct(Product product){

			if(product != null){
				((StatefullComponent)getDescendentByName("totalPrice")).setValue(product.getTotalPrice()==null?"0":product.getTotalPrice());
				((StatefullComponent)getDescendentByName("taxRate")).setValue(product.getTaxRate()==null?"15":product.getTaxRate());
				((StatefullComponent)getDescendentByName("price")).setValue(product.getPriceExcludingTax());
				
				((StatefullComponent)getDescendentByName("title")).setValue(product.getTitle());
				((StatefullComponent)getDescendentByName("code")).setValue(product.getCode());
				((StatefullComponent)getDescendentByName("description")).setValue(product.getSummary());
			}else{
				((StatefullComponent)getDescendentByName("totalPrice")).setValue("0");
				((StatefullComponent)getDescendentByName("taxRate")).setValue("15");
				((StatefullComponent)getDescendentByName("price")).setValue("0");
				
				((StatefullComponent)getDescendentByName("title")).setValue("");
				((StatefullComponent)getDescendentByName("code")).setValue("");
				((StatefullComponent)getDescendentByName("description")).setValue("");
			}
			return this;
	}

	@Override
	public void fillProduct(Product product) {

		BigDecimal totalPrice = new BigDecimal(((StatefullComponent)getDescendentByName("totalPrice")).getValue().toString());
		product.setTotalPrice(totalPrice);
		
		BigDecimal taxRate = new BigDecimal(((StatefullComponent)getDescendentByName("taxRate")).getValue().toString());
		product.setTaxRate(taxRate);
		
		product.setTitle(((StatefullComponent)getDescendentByName("title")).getValue().toString());
		product.setCode(((StatefullComponent)getDescendentByName("code")).getValue().toString());
		product.setSummary(((StatefullComponent)getDescendentByName("description")).getValue().toString());
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
		
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		
		String totalPrice = ((StatefullComponent)getDescendentByName("totalPrice")).getValue().toString();
		String vat = ((StatefullComponent)getDescendentByName("taxRate")).getValue().toString();
		
		try{
		if(StringUtil.isNotEmpty(totalPrice) && StringUtil.isNotEmpty(vat)){
			BigDecimal exl = new BigDecimal(100).subtract(new BigDecimal(vat));
			exl = exl.divide(new BigDecimal(100));
		
			String value = new BigDecimal(totalPrice).multiply(exl).toPlainString();
			((StatefullComponent)getDescendentByName("price")).setValue(value);
			
		}
		}catch(Exception e){
			try{
				Double.parseDouble(vat);
			}catch(Exception v){
				getDescendentByName("taxRate").addClass("ui-state-error");
			}
			
			try{
				Double.parseDouble(totalPrice);
			}catch(Exception v){
				getDescendentByName("totalPrice").addClass("ui-state-error");
			}
			throw new UIException("Numeric values are expected in the fields Vat and Basic price");
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}

}
